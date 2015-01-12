<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.Tournament"%>

<% 
Tournament tournament = (Tournament) request.getAttribute("tournament");

Locale currentLocale = request.getLocale();

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Overall", 
		Locale.FRENCH, 	"Classement final"
		), currentLocale);

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);

LocalizedString strSave = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Save", 
		Locale.FRENCH, 	"Sauvegarder"
		), currentLocale);

LocalizedString strUsers = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Users", 
		Locale.FRENCH, 	"Utilisateurs"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strPlayoff %></title>
<link rel="shortcut icon" href="images/favicon.ico" />
</head>
<body>
	<br/>
	<a href="../overall"><%= strOverall %></a>
	<br/>
	<a href="../schedule"><%= strSchedule %></a>
	<br/>
	<a href="schools"><%= strSchools %></a>
	<br/>
	<a href="users"><%= strUsers %></a>
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>