package com.to.security.auth.processors;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.processors.GridProcessorAdapter;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.internal.processors.security.SecurityContext;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.security.AuthenticationContext;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityException;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecuritySubject;
import org.apache.ignite.plugin.security.SecuritySubjectType;
import org.apache.ignite.spi.discovery.DiscoverySpiNodeAuthenticator;
import org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode;
import org.jetbrains.annotations.Nullable;

import com.to.security.auth.processors.security.DefaultSecurityContext;
import com.to.security.auth.processors.security.DefaultSecurityPermissionSet;
import com.to.security.auth.processors.security.DefaultSecuritySubject;
import com.to.security.auth.util.AuthUtil;

/**
 * 
 * @author Sai
 *
 */
public class CustomGridSecurityProcessor extends GridProcessorAdapter implements DiscoverySpiNodeAuthenticator, GridSecurityProcessor, IgnitePlugin {

	
	private Collection<SecuritySubject> subjects=new ArrayList<>();
	
    public CustomGridSecurityProcessor(GridKernalContext ctx) {
		super(ctx);
	}
    
    
    public SecurityContext authenticateNode(ClusterNode node, SecurityCredentials cred)  {
    	
    	if(log.isDebugEnabled())
    		log.debug("authenticateNode(..) invoked for the node:"+node.id()+" joined.");
		 
    	SecurityContext context=null;    	
    	if(Objects.nonNull(node) && Objects.nonNull(cred)) {
    		
    		if(AuthUtil.isAuthenticable(cred)) {
	    		UUID id=node.id();
		    	SecuritySubjectType type= node.isClient()? SecuritySubjectType.REMOTE_CLIENT:SecuritySubjectType.REMOTE_NODE;
		    	Object login=cred.getLogin();
		    	if (isSubjectExist(id)) {
		    		if(log.isDebugEnabled())
		    			log.debug("subject joined already found with id="+id.toString()); 
		    		SecuritySubject subject=getSubject(id);
					context=new DefaultSecurityContext(subject);
				}else {
					if(log.isDebugEnabled())
						log.debug("new subject with id="+id.toString());
					String address = node.addresses().iterator().next();
					int port = 0;
					if (node instanceof TcpDiscoveryNode) {
						port = ((TcpDiscoveryNode)node).discoveryPort();
						if(log.isDebugEnabled())
							log.error("new Subject["+node.id()+"] has found with IP:Port=["+address+":"+port+"]"); 
					}
					context=new DefaultSecurityContext(new DefaultSecuritySubject(id, type, login,new InetSocketAddress(address, port) , new DefaultSecurityPermissionSet()));
				}
    		}else {
    			throw new IgniteException("Authentication failed due to invalid username/password for the node:"+node.id());
    		}
    	}
    	return context;
    }

    public boolean isGlobalNodeAuthentication() {
    	return true;
    }

    
    
    public SecurityContext authenticate(AuthenticationContext ctx) throws IgniteCheckedException {
    	return null;
    }

    private boolean isSubjectExist(UUID id) {
    	boolean isExist=false;
    	for (SecuritySubject subject : subjects) {
			if (subject.id().equals(id)) {
				isExist=true;
				break;
			}
		}
    	return isExist;
    }
    
    private SecuritySubject getSubject(UUID id) {
    	SecuritySubject sub=null;
    	for (SecuritySubject subject : subjects) {
			if (subject.id().equals(id)) {
				sub=subject;
			}
		}
    	return sub;
    }
    
    private boolean removeSubject(UUID id) {
    	boolean isRemoved=false;
		if (Objects.nonNull(id)) {
			for (SecuritySubject sub : subjects) {
				if (sub.id().equals(id)) {
					subjects.remove(sub);
					isRemoved = true;
				}
			}
		}
    	return isRemoved;
    }
    
    public Collection<SecuritySubject> authenticatedSubjects() throws IgniteCheckedException {
        return subjects;
    }

    public SecuritySubject authenticatedSubject(UUID subjId) throws IgniteCheckedException {
    	SecuritySubject subject=null;
    		if (Objects.nonNull(subjId) && isSubjectExist(subjId)) {
				subject=getSubject(subjId);
			}
        return subject;
    }

    public void authorize(String name, SecurityPermission perm, @Nullable SecurityContext securityCtx) throws SecurityException {
    
    }

    public void onSessionExpired(UUID subjId) {
    	if (Objects.nonNull(subjId))
    		removeSubject(subjId);
    }

    public boolean enabled() {
        return true;
    }
}
