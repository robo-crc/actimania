<%@page import="com.backend.models.optaplanner.TournamentScoreCalculator"%>
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

LocalizedString strLiveGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live game", 
		Locale.FRENCH, 	"Partie en cours"
		), currentLocale);

LocalizedString strPreliminaryGames = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary", 
		Locale.FRENCH, 	"Parties préliminaires"
		), currentLocale);

LocalizedString strPlayoffDraft = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Draft", 
		Locale.FRENCH, 	"Repêchage"
		), currentLocale);

LocalizedString strPlayoffSemi = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Semi-final", 
		Locale.FRENCH, 	"Semi finale"
		), currentLocale);

LocalizedString strPlayoffDemi = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Demi-final", 
		Locale.FRENCH, 	"Demi finale"
		), currentLocale);

LocalizedString strPlayoffFinal = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Final", 
		Locale.FRENCH, 	"Finale"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="images/favicon.ico" />
<title><%= strSchedule %></title>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-58398665-1', 'auto');
  ga('send', 'pageview');
</script>
</head>
<body>

<% 
for(int i = GameTypeEnum.values().length - 1; i >= 0; i--)
{
	GameTypeEnum gameType = GameTypeEnum.values()[i];
	ArrayList<Game> heatGames = tournament.getHeatGames(gameType);
	if(heatGames.size() == 0)
	{
		continue;
	}
	
	LocalizedString h2Str = null;
	switch(gameType)
	{
	case PRELIMINARY:
		h2Str = strPreliminaryGames;
		break;
	case PLAYOFF_DRAFT:
		h2Str = strPlayoffDraft;
		break;
	case PLAYOFF_DEMI:
		h2Str = strPlayoffDemi;
		break;
	case PLAYOFF_SEMI:
		h2Str = strPlayoffSemi;
		break;
	case PLAYOFF_FINAL:
		h2Str = strPlayoffFinal;
		break;
	}
%>
<h2><%= h2Str %></h2>
<table>
<tr>
<td><%= strGameNumber %></td><td><%= strGameTime %></td><td><%= strBlueTeam %></td><td><%= strBlueScore %></td><td><%= strYellowTeam %></td><td><%= strYellowScore %></td>
</tr>
<%
int gameCount = 0;
int block = 1;
for( Game game : heatGames )
{
	if(gameCount != 0 && gameType == GameTypeEnum.PRELIMINARY && TournamentScoreCalculator.isStartOfBlock(gameCount, heatGames.size()))
	{
		%>
		</table>
		<br/>
		<br/>
		<table>
		<tr>
		<td><%= strGameNumber %></td><td><%= strGameTime %></td><td><%= strBlueTeam %></td><td><%= strBlueScore %></td><td><%= strYellowTeam %></td><td><%= strYellowScore %></td>
		</tr>
		<%
	}
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
gameCount++;
}
%>
</table>

<%
} // End of for GameTypeEnum
%>
<a href="ranking"><%= strRanking %></a><br/>
<a href="live"><%= strLiveGame %></a>
</body>
</html>