package com.framework.helpers;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;

import com.framework.models.Essentials;
import com.framework.models.User;
import com.google.common.collect.ImmutableSet;

public class ApplicationSpecific 
{
	public enum AuthorizationRole 
	{
		admin
		// , school // I don't see a use case for school to have a login yet.
	}
	
	public static Set<String> getAllRoles()
	{
		Set<String> roles = new TreeSet<String>();
		for( AuthorizationRole role : AuthorizationRole.values() )
		{
			roles.add(role.name());
		}
		return roles;
	}
	
	public static String getDefaultRole()
	{
		return AuthorizationRole.admin.name();
	}
	
	public static Set<String> getPermissions(Set<String> roles)
	{
		Set<String> permissions = new TreeSet<String>();
		
		for( String role : roles )
		{
			if( role.equals(AuthorizationRole.admin.toString()) )
			{
				permissions.add("*");
			}
			/*
			if( role.equals(AuthorizationRole.school.toString()) )
			{
				permissions.add("school:*");
			}
			*/
		}
		
		return permissions;
	}
	
	public static Set<String> getAuthorizationRole()
	{
		return ImmutableSet.of(AuthorizationRole.admin.name());
	}
	
	// Domain specific account creation
	public static void onAccountCreated(Essentials essentials, User login)
	{
	}
	
	public static void onLoggedIn(Essentials essentials, User login) throws ServletException, IOException
	{
		//Subject currentUser = SecurityUtils.getSubject();
		
		if(login.roles.contains(AuthorizationRole.admin.name()))
		{
			essentials.response.sendRedirect("admin/users");
		}
	}
}
