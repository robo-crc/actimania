<%@page import="org.joda.time.format.PeriodFormatterBuilder"%>
<%@page import="org.joda.time.format.PeriodFormatter"%>
<%@page import="com.backend.models.SkillsCompetition"%>
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
SkillsCompetition skillsCompetition = (SkillsCompetition) request.getAttribute("skillsCompetition");
int schoolCount = tournament.schools.size();

Locale currentLocale = request.getLocale();

LocalizedString strTournament = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Tournament", 
		Locale.FRENCH, 	"Tournoi"
		), currentLocale);

LocalizedString strCumulative = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary round position : ", 
		Locale.FRENCH, 	"Position ronde préliminaire : "
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Score", 
		Locale.FRENCH, 	"Pointage"
		), currentLocale);

LocalizedString strPickupRace = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Pick-up race", 
		Locale.FRENCH, 	"Ramassage de vitesse"
		), currentLocale);

LocalizedString strTwoTargetHits = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Two target hits", 
		Locale.FRENCH, 	"Toucher deux cibles"
		), currentLocale);

LocalizedString strTwoActuatorChanged = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Two actuator changed", 
		Locale.FRENCH, 	"Changer deux actuateurs"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
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

LocalizedString strGroup = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Group", 
		Locale.FRENCH, 	"Groupe"
		), currentLocale);

LocalizedString strBlueScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue score", 
		Locale.FRENCH, 	"Pointage équipe bleue"
		), currentLocale);

LocalizedString strYellowScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow score", 
		Locale.FRENCH, 	"Pointage équipe jaune"
		), currentLocale);

LocalizedString strPreliminaryGames = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary", 
		Locale.FRENCH, 	"Parties préliminaires"
		), currentLocale);

LocalizedString strPlayoffRepechage = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Repechage", 
		Locale.FRENCH, 	"Repêchage"
		), currentLocale);

LocalizedString strPlayoffQuarter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Quarter final", 
		Locale.FRENCH, 	"Quart de finale"
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
<title><%= strSchool %></title>
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
<%@include file="header.jsp" %>

<h1><%= school.name %></h1>

<table>
<tr><td></td>								<td><%= strScore %></td></tr>
<tr><td><%= strTournament %></td>			<td><%= score %></td></tr>
<tr><td><%= strPickupRace %></td>			<td><%= skillsCompetition.getPickballs(school).integer %></td></tr>
<tr><td><%= strTwoTargetHits %></td>		<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %></td></tr>
<tr><td><%= strTwoActuatorChanged %></td>	<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %></td></tr>
</table>
<br/>
<b><%= strCumulative %> <%=tournament.getPreliminaryRanking(skillsCompetition).indexOf(school) + 1%> / <%= schoolCount %></b>
<br/>
<br/>

<%
for(int i = GameTypeEnum.values().length - 1; i >= 0; i--)
{
	GameTypeEnum gameType = GameTypeEnum.values()[i];
	ArrayList<Game> games = Tournament.getGamesPlayed(tournament.games, school, gameType);
	if(games.size() == 0)
	{
		continue;
	}
	
	LocalizedString h2Str = null;
	switch(gameType)
	{
	case PRELIMINARY:
		h2Str = strPreliminaryGames;
		break;
	case PLAYOFF_REPECHAGE:
		h2Str = strPlayoffRepechage;
		break;
	case PLAYOFF_QUARTER:
		h2Str = strPlayoffQuarter;
		break;
	case PLAYOFF_DEMI:
		h2Str = strPlayoffDemi;
		break;
	case PLAYOFF_FINAL:
		h2Str = strPlayoffFinal;
		break;
	default:
		h2Str = null;
		break;
	}
%>
<h2><%= h2Str %></h2>
<table>
<tr>
<td><%= strGameNumber %></td>
<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<td>" + strGroup + "</td>" ); } %>
<td><%= strGameTime %></td>
<td><%= strSchoolScore %></td>
<td><%= strBlueScore %></td>
<td><%= strYellowScore %></td>
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
	<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<td>" + game.playoffGroup + "</td>" ); } %>
	<td><%= Helpers.dateTimeFormatter.print(game.scheduledTime) %></td>
	<td><%= game.getScore(school) %></td>
	<td><%= blueScore %></td>
	<td><%= yellowScore %></td>
</tr>
<%
}
%>
</table>
<%
} // End of for GameTypeEnum
%>
</body>
</html>