<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 

@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>)request.getAttribute("errorList");

Locale currentLocale = request.getLocale();

LocalizedString strLogin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Login", 
		Locale.FRENCH, 	"Ouverture de session"
		), currentLocale);

LocalizedString strEmail = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "email : ", 
		Locale.FRENCH, 	"courriel : "
		), currentLocale);

LocalizedString strPassword = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Password : ", 
		Locale.FRENCH, 	"Mot de passe : "
		), currentLocale);


%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strLogin %></title>
<link rel="icon" type="image/png" href="favicon.png">

</head>
<body>
	<h1><%= strLogin %></h1>
	
	<% for(LocalizedString error : errorList) 
	{
	%>
		<%= error %><br/>
	<%
	}
	%>
	
	<form method="post">
		<input type="hidden" name="action" value="login" />
		<span><%= strEmail %><input type="text" name="email" />				</span><br/>
		<span><%= strPassword %><input type="password" name="password" />	</span><br/>
		<input type="submit" value="<%= strLogin %>">
	</form>
</body>
</html>