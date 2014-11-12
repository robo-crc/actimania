<%@page import="com.backend.models.School"%>
<%@page import="java.util.Set"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>) request.getAttribute("errorList"); 

@SuppressWarnings("unchecked")
ArrayList<School> schools = (ArrayList<School>) request.getAttribute("schools");

Locale currentLocale = request.getLocale();

LocalizedString strAddSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add", 
		Locale.FRENCH, 	"Ajouter"
		), currentLocale);

LocalizedString strEditSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Edit", 
		Locale.FRENCH, 	"Éditer"
		), currentLocale);

LocalizedString strDeleteSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Delete", 
		Locale.FRENCH, 	"Supprimer"
		), currentLocale);

LocalizedString strSchoolName = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strAddSchool %></title>
<link rel="icon" type="image/png" href="favicon.png">

</head>
<body>
	<%
	for(LocalizedString error : errorList)
	{
		%>
		<span class="error"><%= error %></span><br/>
		<%
	}
	%>
	<h1><%= strAddSchool %></h1>
	<form method="post">
		<input type="hidden" name="action" value="create" />
		<span><%= strSchoolName %><input type="text" name="schoolName" /> </span><br/>
		<br/>
		<input type="submit" value="<%= strAddSchool %>" />
	</form>
	
	<h1><%= strEditSchool %></h1>
	
	<% 
	for( School school : schools)
	{
	%>
		<form method="post">
			<input type="hidden" name="action" value="edit" />
			<input type="hidden" name="id" value="<%= school._id %>" />
			<span><%= strSchoolName %><input type="text" name="schoolName" value="<%= school.name %>" /> </span><br/>
			<input type="submit" value="<%= strEditSchool %>" />
		</form>
		
		<form method="post">
			<input type="hidden" name="action" value="delete" />
			<input type="hidden" name="id" value="<%= school._id %>" />
			<input type="submit" value="<%= strDeleteSchool %>" />
		</form>
		<br/>
	<% 
	}
	%>

	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>
</html>