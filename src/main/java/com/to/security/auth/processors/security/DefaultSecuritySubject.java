package com.to.security.auth.processors.security;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.UUID;

import org.apache.ignite.plugin.security.SecurityPermissionSet;
import org.apache.ignite.plugin.security.SecuritySubject;
import org.apache.ignite.plugin.security.SecuritySubjectType;

/**
 * 
 * @author Sai
 *
 */
public class DefaultSecuritySubject implements SecuritySubject {

	private static final long serialVersionUID = 1L;
	
	public DefaultSecuritySubject(UUID id,SecuritySubjectType type, Object login, InetSocketAddress address, SecurityPermissionSet permissionSet) {
		super();
		this.id=id;
		this.type = type;
		this.login = login;
		this.address = address;
		this.permissionSet = permissionSet;
	}


	private UUID id;
	private SecuritySubjectType type;
	private Object login;
	private InetSocketAddress address;
	private SecurityPermissionSet permissionSet;
	
	@Override
	public UUID id() {
		return Objects.nonNull(this.id)?this.id:UUID.randomUUID();
	}

	@Override
	public SecuritySubjectType type() {
		return type;
	}

	@Override
	public Object login() {
		return login;
	}

	@Override
	public InetSocketAddress address() {
		return address;
	}

	@Override
	public SecurityPermissionSet permissions() {
		return permissionSet;
	}

}
