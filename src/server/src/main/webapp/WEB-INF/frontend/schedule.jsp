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
		Locale.ENGLISH, "PRELIMINARY GAMES", 
		Locale.FRENCH, 	"PARTIES PRÉLIMINAIRES"
		), currentLocale);

LocalizedString strGroup = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Group", 
		Locale.FRENCH, 	"Groupe"
		), currentLocale);

LocalizedString strPlayoffRepechage = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "REPECHAGE", 
		Locale.FRENCH, 	"REPÊCHAGE"
		), currentLocale);

LocalizedString strPlayoffQuarter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "QUARTER FINAL", 
		Locale.FRENCH, 	"QUART DE FINALE"
		), currentLocale);

LocalizedString strPlayoffDemi = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "DEMI FINAL", 
		Locale.FRENCH, 	"DEMI FINALE"
		), currentLocale);

LocalizedString strPlayoffFinal = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "FINAL", 
		Locale.FRENCH, 	"FINALE"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<title><%= strScheduleTitle %></title>
<style>
.headerSchedule
{
	font-weight: bold !important;
}
</style>
</head>
<body>
<%@include file="header.jsp" %>

<h1 class="grayColor"><%= strSchedule %></h1>
<div class="bar grayBackgroundColor"></div>

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
<th class="whiteBackgroundColor"></th>
<th class="scheduleGameNumber">
<% if(gameType == GameTypeEnum.PRELIMINARY) 
{ 
	out.print( strGameNumberTime );	 
}
else
{
	out.print( strGameNumberGroupTime );
}
%>
</th>
<th class="scheduleTeam"><%= strBlueTeam %></th>
<th class="scheduleScore"><%= strScore %></th>
<th class="scheduleTeam"><%= strYellowTeam %></th>
<th class="scheduleScore"><%= strScore %></th>
</tr>
<%
ArrayList<Integer> gamesPerBlockCount = TournamentScoreCalculator.getGamesPerBlockCount(heatGames.size());
int gameCount = 0;
int block = 0;
for( Game game : heatGames )
{
	boolean isStartOfBlock = gameType == GameTypeEnum.PRELIMINARY && TournamentScoreCalculator.isStartOfBlock(gameCount, heatGames.size());
	if(gameCount != 0 && isStartOfBlock)
	{
		block++;
		%>
		</table>
		<table>
		<tr>
			<th class="backgroundWhite"></th>
			<th class="scheduleGameNumber">
			<% if(gameType == GameTypeEnum.PRELIMINARY) 
			{ 
				out.print( strGameNumberTime );	 
			}
			else
			{
				out.print( strGameNumberGroupTime );
			}
			%>
			</th>
			<th class="scheduleTeam"><%= strBlueTeam %></th>
			<th class="scheduleScore"><%= strScore %></th>
			<th class="scheduleTeam"><%= strYellowTeam %></th>
			<th class="scheduleScore"><%= strScore %></th>
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
	if((gameType != GameTypeEnum.PRELIMINARY && gameCount == 0) || isStartOfBlock)
	{
		String roundTypeDisplay = roundType.get(currentLocale);
		int nbGames = heatGames.size();
		if(gameType == GameTypeEnum.PRELIMINARY)
		{
			nbGames = gamesPerBlockCount.get(block);
			roundTypeDisplay += " " + String.valueOf(block + 1);
		}
		out.print("<td class=\"scheduleRoundTd roundDiv\" rowspan=\"" + nbGames + "\"><div class=\"scheduleRound\">" + roundTypeDisplay + "</div></td>");
	}
	 %>
	<td class="center scheduleGameNumber"><a class="scheduleGame" href="<%= gamePrefix %>game?gameId=<%= game._id %>"><%= strGame + " " + game.gameNumber %></a><br/>
	<%= Helpers.dateTimeFormatter.print(game.scheduledTime) %>
	<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<br/>" + strGroup + " " + game.playoffGroup ); } %>
	</td>
	
	<td class="scheduleTeam">
	<div class="blueBackgroundColor scheduleColor"></div><div class="scheduleSchool">
<%
for( School school : game.blueTeam )
{
	%>
	<div class="scheduleSchoolDiv clear">
		<div class="scheduleSchoolInner">
			<img class="scheduleSchoolLogo" src="images/schools/32x32/<%= school.getCompactName() %>.png" />
		</div>
		<a class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
	</div>
	<%
}
%>
	</div>
	</td>
	<td class="center scheduleScore"><div class="schedulePoints"><%= blueScore %></div><div class="schedulePointsStr"><%= pointsStr %></div></td>
	<td class="scheduleTeam">
	<div class="yellowBackgroundColor scheduleColor"></div><div class="scheduleSchool">
<%
for( School school : game.yellowTeam )
{
	%>
	<div class="scheduleSchoolDiv clear">
		<div class="scheduleSchoolInner">
			<img class="scheduleSchoolLogo" src="images/schools/32x32/<%= school.getCompactName() %>.png" />
		</div>
		<a class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
	</div>
	<%
}
%>
	</div>
	</td>
	<td class="center scheduleScore"><div class="schedulePoints"><%= yellowScore %></div><div class="schedulePointsStr"><%= pointsStr %></div></td>
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
<%@include file="footer.jsp" %>
</body>
</html>