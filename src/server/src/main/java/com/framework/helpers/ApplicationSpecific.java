package com.framework.helpers;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;

import com.backend.models.enums.GameTypeEnum;
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
	
	public static String getDatabaseName()
	{
		return "Converto";
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
	
	@SuppressWarnings("unchecked")
	public static <T> T getParameter(String safeParam, Class<T> entityType)
	{
		if(entityType == GameTypeEnum.class)
		{
			return (T) GameTypeEnum.valueOf(safeParam);
		}
		
		return null;
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
		if(login.roles.contains(AuthorizationRole.admin.name()))
		{
			essentials.response.sendRedirect("schedule");
		}
	}
}
