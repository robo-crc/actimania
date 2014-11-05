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
School school	= (School) request.getAttribute("school");
int position 	= (int) request.getAttribute("position");
int score		= (int) request.getAttribute("score");

@SuppressWarnings("unchecked")
ArrayList<Game> games = (ArrayList<Game>)request.getAttribute("games");

Locale currentLocale = request.getLocale();

LocalizedString strPosition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Position", 
		Locale.FRENCH, 	"Position"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Score", 
		Locale.FRENCH, 	"Pointage"
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

<table>
<tr>
<td><%= strGameNumber %></td><td><%= strGameTime %></td><td><%= strSchoolScore %></td><td><%= strBlueScore %></td><td><%= strYellowScore %></td>
<%
for( Game game : games )
{
	ArrayList<GameState> gameStates = game.getGameStates();
	GameState gameState = gameStates.get(gameStates.size() - 1);
%>
	<td><a href="game/gameId?=<%= game._id %>">Game number goes here</a></td>
	<td>Game time goes here</td>
	<td><%= game.getScore(school) %></td>
	<td><%= gameState.blueScore %></td>
	<td><%= gameState.yellowScore %></td>
<%
}
%>
</tr>
</table>

</body>
</html>