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
Tournament tournament = (Tournament) request.getAttribute("tournament");
School school	= (School) request.getAttribute("school");
Integer rank 	= (Integer) request.getAttribute("rank");
Integer score	= (Integer) request.getAttribute("score");

@SuppressWarnings("unchecked")
ArrayList<Game> games = (ArrayList<Game>)request.getAttribute("games");

Locale currentLocale = request.getLocale();

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Tournament rank : ", 
		Locale.FRENCH, 	"Position au classement : "
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Tournament score : ", 
		Locale.FRENCH, 	"Pointage cumulatif du tournoi : "
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
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
		Locale.ENGLISH, "Game date/time", 
		Locale.FRENCH, 	"Date/Heure de la partie"
		), currentLocale);

LocalizedString strSchoolScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School score", 
		Locale.FRENCH, 	"Pointage de l'école"
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
<title><%= strSchool %></title>
</head>
<body>

<h1><%= school.name %></h1>
<div class="rank"><%= strRank + rank.toString() + " / " + tournament.schools.size() %></div>
<div class="clear"></div>
<div class="score"><%= strScore + score.toString() %></div>
<div class="clear"></div>
<br/>
<table>
<tr>
<td><%= strGameNumber %></td><td><%= strGameTime %></td><td><%= strSchoolScore %></td><td><%= strBlueScore %></td><td><%= strYellowScore %></td>
</tr>
<%
for( Game game : games )
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
<tr>
	<td><a href="game?gameId=<%= game._id %>"><%= game.gameNumber %></a></td>
	<td><%= Helpers.dateTimeFormatter.print(game.scheduledTime) %></td>
	<td><%= game.getScore(school) %></td>
	<td><%= blueScore %></td>
	<td><%= yellowScore %></td>
</tr>
<%
}
%>
</table>
<br/>
<a href="schedule"><%= strSchedule %></a><br/>
<a href="ranking"><%= strRanking %></a>
</body>
</html>