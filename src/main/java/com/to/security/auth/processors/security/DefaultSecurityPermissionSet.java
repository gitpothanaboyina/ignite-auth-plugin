package com.to.security.auth.processors.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecurityPermissionSet;

/**
 * 
 * @author Sai
 *
 */
public class DefaultSecurityPermissionSet implements SecurityPermissionSet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public boolean defaultAllowAll() {
		return true;
	}

	@Override
	public Map<String, Collection<SecurityPermission>> taskPermissions() {
		
		Map<String, Collection<SecurityPermission>> permissions= new HashMap<>();
		
		Collection<SecurityPermission> taskExecute = new ArrayList<SecurityPermission>();
		taskExecute.add(SecurityPermission.TASK_EXECUTE);
		Collection<SecurityPermission> taskCancel = new ArrayList<SecurityPermission>();
		taskCancel.add(SecurityPermission.TASK_CANCEL);
		
		permissions.put("taskExecute", taskExecute);
		permissions.put("taskCancel", taskCancel);
		
		return permissions;
	}

	@Override
	public Map<String, Collection<SecurityPermission>> cachePermissions() {
		Map<String, Collection<SecurityPermission>> permissions= new HashMap<>();
		
		Collection<SecurityPermission> cacheCreate = new ArrayList<SecurityPermission>();
		cacheCreate.add(SecurityPermission.CACHE_CREATE);
		Collection<SecurityPermission> cachePut = new ArrayList<SecurityPermission>();
		cachePut.add(SecurityPermission.CACHE_PUT);
		Collection<SecurityPermission> cacheDestroy = new ArrayList<SecurityPermission>();
		cacheDestroy.add(SecurityPermission.CACHE_DESTROY);
		Collection<SecurityPermission> cacheRemove = new ArrayList<SecurityPermission>();
		cacheRemove.add(SecurityPermission.CACHE_REMOVE);
		Collection<SecurityPermission> cacheRead = new ArrayList<SecurityPermission>();
		cacheRead.add(SecurityPermission.CACHE_READ);
		
		permissions.put("cacheCreate", cacheCreate);
		permissions.put("cachePut", cachePut);
		permissions.put("cacheDestroy", cacheDestroy);
		permissions.put("cacheRemove", cacheRemove);
		permissions.put("cacheRead", cacheRead);
		
		return permissions;
	}

	@Override
	public Map<String, Collection<SecurityPermission>> servicePermissions() {
		Map<String, Collection<SecurityPermission>> permissions= new HashMap<>();
		
		Collection<SecurityPermission> serviceInvoke = new ArrayList<SecurityPermission>();
		serviceInvoke.add(SecurityPermission.SERVICE_INVOKE);
		Collection<SecurityPermission> serviceDeploy = new ArrayList<SecurityPermission>();
		serviceDeploy.add(SecurityPermission.SERVICE_DEPLOY);
		Collection<SecurityPermission> serviceCancel = new ArrayList<SecurityPermission>();
		serviceCancel.add(SecurityPermission.SERVICE_CANCEL);
		
		permissions.put("serviceInvoke", serviceInvoke);
		permissions.put("serviceDeploy", serviceDeploy);
		permissions.put("serviceCancel", serviceCancel);
		return permissions;
	}

	@Override
	public Collection<SecurityPermission> systemPermissions() {
		
		Collection<SecurityPermission> systemPermissions = new ArrayList<>();
		systemPermissions.add(SecurityPermission.ADMIN_CACHE);
		systemPermissions.add(SecurityPermission.ADMIN_OPS);
		systemPermissions.add(SecurityPermission.ADMIN_QUERY);
		systemPermissions.add(SecurityPermission.ADMIN_VIEW);
		systemPermissions.add(SecurityPermission.EVENTS_ENABLE);
		
		return systemPermissions;
	}

}
