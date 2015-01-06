<%@page import="com.backend.models.enums.GameTypeEnum"%>
<%@page import="com.backend.models.Tournament"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
Locale currentLocale = request.getLocale();

LocalizedString strLive = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live", 
		Locale.FRENCH, 	"En cours"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/live.css"/>
<title><%= strLive %></title>
</head>
<body>

<iframe src="http://www.twitch.tv/liquidtlo/embed" class="twitchStream" frameborder="0" scrolling="no" height="378" width="620"></iframe>
<iframe src="game" class="gameFrame" scrolling="no" frameborder="0" height="1500px" width="100%"></iframe>

</body>
</html>