<%@page import="com.backend.models.Game"%>
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

Game game = (Game) request.getAttribute("game");

Locale currentLocale = request.getLocale();

LocalizedString strAdd = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add", 
		Locale.FRENCH, 	"Ajouter"
		), currentLocale);

LocalizedString strEdit = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Edit", 
		Locale.FRENCH, 	"Éditer"
		), currentLocale);

LocalizedString strDelete = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Delete", 
		Locale.FRENCH, 	"Supprimer"
		), currentLocale);

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);

LocalizedString strGameAdmin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game administration", 
		Locale.FRENCH, 	"Administration des parties"
		), currentLocale);

LocalizedString strStartGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Start Game", 
		Locale.FRENCH, 	"Démarrer la partie"
		), currentLocale);

LocalizedString strTargetHit = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Target hit", 
		Locale.FRENCH, 	"Démarrer la partie"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strGameAdmin %></title>
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
	
	
	
	
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>
</html>