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

@SuppressWarnings("unchecked")
ArrayList<Game> games = (ArrayList<Game>)request.getAttribute("games");

Locale currentLocale = request.getLocale();

LocalizedString strTournament = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Tournament", 
		Locale.FRENCH, 	"Tournoi"
		), currentLocale);

LocalizedString strCumulative = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary cumulative", 
		Locale.FRENCH, 	"Cumulatif préliminaire"
		), currentLocale);

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Rank", 
		Locale.FRENCH, 	"Position"
		), currentLocale);

LocalizedString strRankScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Cumulative value", 
		Locale.FRENCH, 	"Valeure globale"
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

LocalizedString strLiveGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live game", 
		Locale.FRENCH, 	"Partie en cours"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="images/favicon.ico" />
<title><%= strSchool %></title>
</head>
<body>

<h1><%= school.name %></h1>
<table>
<tr><td></td><td><%= strRank + " ( / " + schoolCount + " )" %></td><td><%= strRankScore %></td><td><%= strScore %></td></tr>
<tr><td><%= strTournament %></td>			<td><%= rank %></td>																			<td><%= String.format("%.1f", tournament.getPreliminaryHeatScore(school)) %></td>				<td><%= score %></td></tr>
<tr><td><%= strPickupRace %></td>			<td><%= skillsCompetition.getPickballsPosition(school) + 1 %></td>								<td><%= String.format("%.1f", skillsCompetition.getPickballsPoints(school)) %></td>				<td><%= skillsCompetition.getPickballs(school).integer %></td></tr>
<tr><td><%= strTwoTargetHits %></td>		<td><%= skillsCompetition.getTwoTargetHitsPosition(school) + 1 %></td>							<td><%= String.format("%.1f", skillsCompetition.getTwoTargetHitsPoints(school)) %></td>			<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %></td></tr>
<tr><td><%= strTwoActuatorChanged %></td>	<td><%= skillsCompetition.getTwoActuatorChangedPosition(school) + 1 %></td>						<td><%= String.format("%.1f", skillsCompetition.getTwoActuatorChangedPoints(school)) %></td>	<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %></td></tr>
<tr><td><b><%= strCumulative %></b></td>	<td><b><%= tournament.getCumulativeRanking(skillsCompetition).indexOf(school) + 1 %></b></td>	<td><b><%= String.format("%.1f", tournament.getCumulativeScore(school, skillsCompetition)) %></b></td>	<td>-</td></tr>
</table>
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
<a href="ranking"><%= strRanking %></a><br/>
<a href="live"><%= strLiveGame %></a>
</body>
</html>