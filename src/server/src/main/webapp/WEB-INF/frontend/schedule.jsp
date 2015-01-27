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
		Locale.ENGLISH, "SCORE", 
		Locale.FRENCH, 	"POINTAGE"
		), currentLocale);

LocalizedString strPoints = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points", 
		Locale.FRENCH, 	"Points"
		), currentLocale);

LocalizedString strScheduleTitle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strGameNumberGroupTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "GAME #,<br/>GAME TIME,<br/>GROUP", 
		Locale.FRENCH, 	"# PARTIE,<br/>DATE ET HEURE,<br/>GROUPE"
		), currentLocale);

LocalizedString strGameNumberTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "GAME #,<br/>GAME TIME", 
		Locale.FRENCH, 	"# PARTIE,<br/>DATE ET HEURE"
		), currentLocale);

LocalizedString strGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game", 
		Locale.FRENCH, 	"Partie"
		), currentLocale);

LocalizedString strBlueTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "BLUE TEAM", 
		Locale.FRENCH, 	"ÉQUIPE BLEUE"
		), currentLocale);

LocalizedString strEmpty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "", 
		Locale.FRENCH, 	""
		), currentLocale);

LocalizedString strYellowTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "YELLOW TEAM", 
		Locale.FRENCH, 	"ÉQUIPE JAUNE"
		), currentLocale);

LocalizedString strPreliminaryGames = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary", 
		Locale.FRENCH, 	"Parties préliminaires"
		), currentLocale);

LocalizedString strGroup = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Group", 
		Locale.FRENCH, 	"Groupe"
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
<title><%= strScheduleTitle %></title>
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
<%
for(int i = GameTypeEnum.values().length - 1; i >= 0; i--)
{
	GameTypeEnum gameType = GameTypeEnum.values()[i];
	ArrayList<Game> heatGames = tournament.getHeatGames(gameType);
	if(heatGames.size() == 0)
	{
		continue;
	}
	
	LocalizedString roundType = null;
	switch(gameType)
	{
	case PRELIMINARY:
		roundType = strPreliminaryGames;
		break;
	case PLAYOFF_REPECHAGE:
		roundType = strPlayoffRepechage;
		break;
	case PLAYOFF_QUARTER:
		roundType = strPlayoffQuarter;
		break;
	case PLAYOFF_DEMI:
		roundType = strPlayoffDemi;
		break;
	case PLAYOFF_FINAL:
		roundType = strPlayoffFinal;
		break;
	default:
		roundType = null;
		break;
	}
%>
<table>
<tr>
<th class="scheduleNone"></th>
<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<th>" + strGameNumberGroupTime + "</th>" ); } %>
<% if(gameType == GameTypeEnum.PRELIMINARY) { out.print( "<th>" + strGameNumberTime + "</th>" ); } %>
<th><%= strBlueTeam %></th>
<th><%= strScore %></th>
<th><%= strYellowTeam %></th>
<th><%= strScore %></th>
</tr>
<%
int gameCount = 0;
int block = 1;
for( Game game : heatGames )
{
	boolean isStartOfBlock = gameType == GameTypeEnum.PRELIMINARY && TournamentScoreCalculator.isStartOfBlock(gameCount, heatGames.size());
	if(gameCount != 0 && isStartOfBlock)
	{
		%>
		</table>
		<br/>
		<br/>
		<table>
		<tr>
			<th class="scheduleNone"></th>
			<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<th>" + strGameNumberGroupTime + "</th>" ); } %>
			<% if(gameType == GameTypeEnum.PRELIMINARY) { out.print( "<th>" + strGameNumberTime + "</th>" ); } %>
			<th><%= strBlueTeam %></th>
			<th><%= strScore %></th>
			<th><%= strYellowTeam %></th>
			<th><%= strScore %></th>
		</tr>
		<%
	}
	
	ArrayList<GameState> gameStates = game.getGameStates();
	String blueScore = "";
	String yellowScore = "";
	LocalizedString pointsStr = strEmpty;
	
	if(gameStates.size() > 0)
	{
		GameState gameState = gameStates.get(gameStates.size() - 1);
		blueScore = String.valueOf(gameState.blueScore);
		yellowScore = String.valueOf(gameState.yellowScore);
		pointsStr = strPoints;
	}
%>
	<tr class="<%= game.isLive ? "isLive" : "" %>">
	<%
	if(isStartOfBlock)
	{
		out.print("<td class=\"scheduleRound\" rowspan=\"" + 17 + "\">" + roundType + "</td>");
	}
	 %>
	<td class="center"><a class="scheduleGame" href="<%= gamePrefix %>game?gameId=<%= game._id %>"><%= strGame + " " + game.gameNumber %></a><br/>
	<%= Helpers.dateTimeFormatter.print(game.scheduledTime) %>
	<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<br/>" + strGroup + " " + game.playoffGroup ); } %>
	</td>
	
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
	<td class="center"><div class="schedulePoints"><%= blueScore %></div><div class="schedulePointsStr"><%= pointsStr %></div></td>
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
	<td class="center"><div class="schedulePoints"><%= yellowScore %></div><div class="schedulePointsStr"><%= pointsStr %></div></td>
</tr>
<%
gameCount++;
}
%>
</table>
<div class="clear"></div>

<%
} // End of for GameTypeEnum
%>
</body>
</html>