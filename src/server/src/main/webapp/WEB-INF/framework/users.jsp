<%@page import="com.framework.models.User"%>
<%@page import="java.util.Set"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
LocalizedString strError = (LocalizedString) request.getAttribute("error"); 

@SuppressWarnings("unchecked")
Set<String> roles = (Set<String>) request.getAttribute("roles");

String defaultRole = (String) request.getAttribute("defaultRole");

@SuppressWarnings("unchecked")
ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");

Locale currentLocale = request.getLocale();

LocalizedString strCreateAccount = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Create account", 
		Locale.FRENCH, 	"Ajouter un usager"
		), currentLocale);

LocalizedString strEditAccount = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Edit account", 
		Locale.FRENCH, 	"Éditer un usager"
		), currentLocale);

LocalizedString strDeleteAccount = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Delete account", 
		Locale.FRENCH, 	"Supprimer cet usager"
		), currentLocale);

LocalizedString strEmail = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "email : ", 
		Locale.FRENCH, 	"courriel : "
		), currentLocale);

LocalizedString strPassword = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Password : ", 
		Locale.FRENCH, 	"Mot de passe : "
		), currentLocale);

LocalizedString strRole = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Role : ", 
		Locale.FRENCH, 	"Role : "
		), currentLocale);

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);

LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strCreateAccount %></title>
<link rel="icon" type="image/png" href="favicon.png">

</head>
<body>
	<h1><%= strCreateAccount %></h1>
	<%= strError %>
	<form method="post">
		<input type="hidden" name="action" value="create" />
		<span><%= strEmail %><input type="text" name="email" />				</span><br/>
		<span><%= strPassword %><input type="password" name="password" />	</span><br/>
		<span><%= strRole %>
			<select name="role">
			<% 
				for( String role : roles )
				{
					if(defaultRole.equals(role))
					{
				%>
					<option selected="selected" value="<%= role %>"><%= role %></option>
				<%
					}
					else
					{
				%>
						<option value="<%= role %>"><%= role %></option>
				<%
					}
				}
				%>			</select></span><br/>
		<input type="submit" value="<%= strCreateAccount %>">
	</form>
	
	<h1><%= strEditAccount %></h1>
	
	<% 
	for( User user : users)
	{
	%>
		<form method="post">
		<input type="hidden" name="action" value="edit" />
		<input type="hidden" name="id" value="<%= user._id %>" />
		<span><%= strEmail %><input type="text" name="email" value="<%= user.email %>" /> </span><br/>
		<span><%= strPassword %><input type="password" name="password" />	</span><br/>
		<span><%= strRole %>
			<select name="role">
				<% 
				for( String role : roles )
				{
					if(user.roles.contains(role))
					{
				%>
					<option selected="selected" value="<%= role %>"><%= role %></option>
				<%
					}
					else
					{
				%>
						<option value="<%= role %>"><%= role %></option>
				<%
					}
				}
				%>
			</select></span><br/>
		<input type="submit" value="<%= strEditAccount %>">
		</form>
		
		<form method="post">
			<input type="hidden" name="action" value="delete" />
			<input type="hidden" name="id" value="<%= user._id %>" />
			<input type="submit" value="<%= strDeleteAccount %>">
		</form>
	<% 
	} 
	%>
	<br/>
	<a href="../schedule"><%= strSchedule %></a>
	<br/>
	<a href="schools"><%= strSchools %></a>
	<br/>
	<a href="competition"><%= strCompetition %></a>
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>
</html>