<%@page import="com.framework.helpers.Helpers"%>
<%@page import="com.backend.models.GameState"%>
<%@page import="com.backend.models.Game"%>
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
Tournament tournament = (Tournament)request.getAttribute("tournament");

String gamePrefix = (String)request.getAttribute("gamePrefix");

Locale currentLocale = request.getLocale();

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Score", 
		Locale.FRENCH, 	"Pointage"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Ranking", 
		Locale.FRENCH, 	"Classement"
		), currentLocale);

LocalizedString strGameNumber = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game #", 
		Locale.FRENCH, 	"# Partie"
		), currentLocale);

LocalizedString strGameTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game time", 
		Locale.FRENCH, 	"Heure de la partie"
		), currentLocale);

LocalizedString strBlueTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue team", 
		Locale.FRENCH, 	"Équipe bleue"
		), currentLocale);

LocalizedString strYellowTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow team", 
		Locale.FRENCH, 	"Équipe jaune"
		), currentLocale);

LocalizedString strBlueScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue score", 
		Locale.FRENCH, 	"Pointage équipe bleue"
		), currentLocale);

LocalizedString strYellowScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow score", 
		Locale.FRENCH, 	"Pointage équipe jaune"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strSchedule %></title>
</head>
<body>

<table>
<tr>
<td><%= strGameNumber %></td><td><%= strGameTime %></td><td><%= strBlueTeam %></td><td><%= strBlueScore %></td><td><%= strYellowTeam %></td><td><%= strYellowScore %></td>
</tr>
<%
for( Game game : tournament.games )
{
	ArrayList<GameState> gameStates = game.getGameStates();
	String blueScore = "";
	String yellowScore = "";
	
	if(gameStates.size() > 0)
	{
		GameState gameState = gameStates.get(gameStates.size() - 1);
		blueScore = String.valueOf(gameState.blueScore);
		yellowScore = String.valueOf(gameState.yellowScore);
	}
%>
<tr class="<%= game.isLive ? "isLive" : "" %>">
	<td><a href="<%= gamePrefix %>game?gameId=<%= game._id %>"><%= game.gameNumber %></a></td>
	<td><%= Helpers.dateTimeFormatter.print(game.scheduledTime) %></td>
	<td>
<%
for( School school : game.blueTeam )
{
	%>
	<a href="school?schoolId=<%= school._id %>"><%= school.name %></a><br/>
	<%
}
%>
	</td>
	<td><%= blueScore %></td>
		<td>
<%
for( School school : game.yellowTeam )
{
	%>
	<a href="school?schoolId=<%= school._id %>"><%= school.name %></a><br/>
	<%
}
%>
	</td>
	<td><%= yellowScore %></td>
</tr>
<%
}
%>
</table>

<a href="ranking"><%= strRanking %></a>
</body>
</html>