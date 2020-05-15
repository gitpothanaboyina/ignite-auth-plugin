package com.to.security.auth.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.ignite.plugin.security.SecurityCredentials;

/**
 * 
 * @author Sai
 *
 */
public class AuthUtil {
	
	private static Map<String, String> credentials;
	
	static {
		credentials=new HashMap<>();
		credentials.put("admin", "admin");
	}
	
	public static boolean isAuthenticable(SecurityCredentials cred) {
		String uid=(String)cred.getLogin();
		String passcode=(String)cred.getPassword();
		return (credentials.containsKey(uid) && credentials.get(uid).equals(passcode)) ? true : false;
	}
}
