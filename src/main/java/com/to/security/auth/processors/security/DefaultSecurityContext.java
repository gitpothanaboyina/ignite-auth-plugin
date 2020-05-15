package com.to.security.auth.processors.security;

import java.io.Serializable;

import org.apache.ignite.internal.processors.security.SecurityContext;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecuritySubject;

/**
 * 
 * @author Sai
 *
 */
public class DefaultSecurityContext implements SecurityContext, Serializable {

	private static final long serialVersionUID = 1L;
	private SecuritySubject subject;
	
	
	public DefaultSecurityContext(SecuritySubject subject) {
		this.subject=subject;
	}
	
	@Override
	public SecuritySubject subject() {
		return subject;
	}

	@Override
	public boolean taskOperationAllowed(String var1, SecurityPermission var2) {
		return true;
	}

	@Override
	public boolean cacheOperationAllowed(String var1, SecurityPermission var2) {
		return true;
	}

	@Override
	public boolean serviceOperationAllowed(String var1, SecurityPermission var2) {
		return true;
	}

	@Override
	public boolean systemOperationAllowed(SecurityPermission var1) {
		return true;
	}
	
}
