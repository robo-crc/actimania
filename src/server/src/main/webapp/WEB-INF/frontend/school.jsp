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
<%
Tournament tournament = (Tournament) request.getAttribute("tournament");
School school	= (School) request.getAttribute("school");
Integer rank 	= (Integer) request.getAttribute("rank");
Integer score	= (Integer) request.getAttribute("score");
SkillsCompetition skillsCompetition = (SkillsCompetition) request.getAttribute("skillsCompetition");
int schoolCount = tournament.schools.size();

Locale currentLocale = request.getLocale();

LocalizedString strTournament = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary games",
		Locale.FRENCH, 	"Parties préliminaires"
		), currentLocale);

LocalizedString strCumulative = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary round position", 
		Locale.FRENCH, 	"Position ronde préliminaire"
		), currentLocale);

LocalizedString strGameNumberGroupTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "GAME #,<br/>GAME TIME,<br/>GROUP", 
		Locale.FRENCH, 	"# PARTIE,<br/>DATE ET HEURE,<br/>GROUPE"
		), currentLocale);

LocalizedString strGameNumberTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "GAME #,<br/>GAME TIME", 
		Locale.FRENCH, 	"# PARTIE,<br/>DATE ET HEURE"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCORE", 
		Locale.FRENCH, 	"POINTAGE"
		), currentLocale);

LocalizedString strBlueScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "BLUE TEAM<br/>SCORE", 
		Locale.FRENCH, 	"POINTAGE DE<br/>L'ÉQUIPE BLEUE"
		), currentLocale);

LocalizedString strYellowScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "YELLOW TEAM<br/>SCORE", 
		Locale.FRENCH, 	"POINTAGE DE<br/>L'ÉQUIPE JAUNE"
		), currentLocale);

LocalizedString strGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game", 
		Locale.FRENCH, 	"Partie"
		), currentLocale);

LocalizedString strPoints = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points", 
		Locale.FRENCH, 	"Points"
		), currentLocale);

LocalizedString strGroup = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Group", 
		Locale.FRENCH, 	"Groupe"
		), currentLocale);

LocalizedString strSchoolScore = new LocalizedString(ImmutableMap.of(     
        Locale.ENGLISH, "SCHOOL SCORE", 
        Locale.FRENCH,  "POINTAGE DE L'ÉCOLE"
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

LocalizedString strEmpty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "", 
		Locale.FRENCH, 	""
		), currentLocale);

LocalizedString strPreliminaryGames = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PRELIMINARY GAMES", 
		Locale.FRENCH, 	"PARTIES PRÉLIMINAIRES"
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
<title><%= school.name %></title>
</head>
<body>
<%@include file="header.jsp" %>

<div class="schoolLogo">
	<ul class="schoolLogoChild">
		<li><img src="images/schools/64x64/<%= school.getCompactName() %>.png" /></li>
		<li><h1 class="grayColor schoolH1"><%= school.name %></h1></li>
	</ul>
</div>

<div class="schoolBar bar grayBackgroundColor"></div>

<table class="schoolTable schoolCompetition">
<tr><th class="whiteBackgroundColor"></th>	<th class="center"><%= strScore %></th></tr>
<tr><td><%= strTournament %></td>			<td class="center"><%= score %></td></tr>
<tr><td><%= strPickupRace %></td>			<td class="center"><%= skillsCompetition.getPickballs(school).integer %></td></tr>
<tr><td><%= strTwoTargetHits %></td>		<td class="center"><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %></td></tr>
<tr><td><%= strTwoActuatorChanged %></td>	<td class="center"><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %></td></tr>
<tr><td><b><%= strCumulative %></b></td> 	<td class="center"><b><%=tournament.getPreliminaryRanking(skillsCompetition).indexOf(school) + 1%> / <%= schoolCount %></b></td></tr>
</table>
<br/>

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
<table class="schoolTable">
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
<th class="scheduleScore"><%= strSchoolScore %></th>
<th class="scheduleScore"><%= strBlueScore %></th>
<th class="scheduleScore"><%= strYellowScore %></th>
</tr>
<%
boolean isFirstGame = true;
for( Game game : games )
{
	ArrayList<GameState> gameStates = game.getGameStates();
	String blueScore = "";
	String yellowScore = "";
	String schoolScore = "";
	LocalizedString pointsStr = strEmpty;
	
	if(gameStates.size() > 0)
	{
		GameState gameState = gameStates.get(gameStates.size() - 1);
		blueScore = String.valueOf(gameState.blueScore);
		yellowScore = String.valueOf(gameState.yellowScore);
		schoolScore = String.valueOf(game.getScore(school));
		pointsStr = strPoints;
	}
	
	%>
	<tr class="<%= game.isLive ? "isLive" : "" %>">
	
	<%
	if(isFirstGame)
	{
		isFirstGame = false;
		String roundTypeDisplay = roundType.get(currentLocale);
		int nbGames = games.size();
		out.print("<td class=\"scheduleRoundTd roundDiv\" rowspan=\"" + nbGames + "\"><div class=\"scheduleRound\">" + roundTypeDisplay + "</div></td>");
	}
	%>

	<td class="center scheduleGameNumber"><a class="scheduleGame" href="game?gameId=<%= game._id %>"><%= strGame + " " + game.gameNumber %></a><br/>
	<%= Helpers.dateTimeFormatter.print(game.scheduledTime) %>
	<% if(gameType != GameTypeEnum.PRELIMINARY) { out.print( "<br/>" + strGroup + " " + game.playoffGroup ); } %>
	</td>
	
	<td class="center schoolScore">
		<div class="<% if( game.blueTeam.contains(school) ) { out.write("blueBackgroundColor"); } else { out.write("yellowBackgroundColor"); }   %> scheduleColor"></div>
		<div class="schedulePoints"><%= schoolScore %></div><div class="schedulePointsStr"><%= pointsStr %></div>
	</td>
	<td class="center schoolScore"><div class="blueBackgroundColor scheduleColor"></div><div class="schedulePoints"><%= blueScore %></div><div class="schedulePointsStr"><%= pointsStr %></div></td>
	<td class="center schoolScore"><div class="yellowBackgroundColor scheduleColor"></div><div class="schedulePoints"><%= yellowScore %></div><div class="schedulePointsStr"><%= pointsStr %></div></td>
</tr>
<%
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