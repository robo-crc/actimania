<%@page import="com.backend.views.yearly.GameYearlyView"%>
<%@page import="com.backend.controllers.GameController"%>
<%@page import="com.backend.controllers.yearly.GameYearlyController"%>
<%@page import="com.backend.models.yearly.GameStateYearly"%>
<%@page import="com.backend.models.GameEvent.GameEvent"%>
<%@page import="com.backend.models.enums.TeamEnum"%>
<%@page import="java.io.IOException"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="com.backend.models.GameState"%>
<%@page import="com.backend.models.enums.SideEnum"%>
<%@page import="com.backend.models.enums.GameEventEnum"%>
<%@page import="com.backend.models.Game"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.Set"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>

<% 
@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>) request.getAttribute("errorList"); 

Game game = (Game) request.getAttribute("game");

Locale currentLocale = request.getLocale();

LocalizedString strAdd = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add", 
		Locale.FRENCH, 	"Ajouter"
		), currentLocale);

LocalizedString strEdit = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Edit", 
		Locale.FRENCH, 	"Éditer"
		), currentLocale);

LocalizedString strDelete = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Delete", 
		Locale.FRENCH, 	"Supprimer"
		), currentLocale);

LocalizedString strStartGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Start Game", 
		Locale.FRENCH, 	"Démarrer la partie"
		), currentLocale);

LocalizedString strRestartGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Re-Start Game", 
		Locale.FRENCH, 	"Redémarrer la partie"
		), currentLocale);

LocalizedString strSubmitConfirm = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Are you sure you want to start a new game? That will reset the current game.", 
		Locale.FRENCH, 	"Êtes-vous sûr de vouloir démarrer une nouvelle partie? Le contenu de la partie à date sera effacé."
		), currentLocale);

LocalizedString strBlueScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue", 
		Locale.FRENCH, 	"Bleu"
		), currentLocale);

LocalizedString strYellowScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow", 
		Locale.FRENCH, 	"Jaune"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of(
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strPointDeduction = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points deduction", 
		Locale.FRENCH, 	"Nombre de point de pénalité"
		), currentLocale);


LocalizedString strPercentDeduction = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "% deduction", 
		Locale.FRENCH, 	"% déduction"
		), currentLocale);

LocalizedString strTimeInGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Time", 
		Locale.FRENCH, 	"Temps"
		), currentLocale);

LocalizedString strEvent = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Event", 
		Locale.FRENCH, 	"Événement"
		), currentLocale);

LocalizedString strAction = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Action", 
		Locale.FRENCH, 	"Action"
		), currentLocale);

LocalizedString strTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Team", 
		Locale.FRENCH, 	"Équipe"
		), currentLocale);

LocalizedString strGameEvents = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game events", 
		Locale.FRENCH, 	"Événements de la partie"
		), currentLocale);

LocalizedString strActuatorState = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Switch state", 
		Locale.FRENCH, 	"État de l'actuateur"
		), currentLocale);

LocalizedString strSchoolPenalty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School Penalty", 
		Locale.FRENCH, 	"Pénalité à une école"
		), currentLocale);

LocalizedString strSchoolPenaltyPercentage = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School Penalty %", 
		Locale.FRENCH, 	"% Pénalité à une école"
		), currentLocale);

LocalizedString strTeamPenalty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Team Penalty", 
		Locale.FRENCH, 	"Pénalité à une équipe"
		), currentLocale);

LocalizedString strMisconductPenalty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Misconduct Penalty", 
		Locale.FRENCH, 	"Pénalité de mauvaise conduite"
		), currentLocale);

LocalizedString strDidNotScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School did not score", 
		Locale.FRENCH, 	"École qui n'a pas compter de points"
		), currentLocale);

LocalizedString strEndGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "End game", 
		Locale.FRENCH, 	"Fin de partie"
		), currentLocale);

LocalizedString strGamePage = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Frontend game page", 
		Locale.FRENCH, 	"Page de la partie publique"
		), currentLocale);

LocalizedString strPointModifier = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Point Modifier", 
		Locale.FRENCH, 	"Modifcation de points"
		), currentLocale);

LocalizedString strPoints = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points", 
		Locale.FRENCH, 	"Points"
		), currentLocale);

LocalizedString strCommentEnglish = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Comment in english", 
		Locale.FRENCH, 	"Commentaire en anglais"
		), currentLocale);

LocalizedString strCommentFrench = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Comment in french", 
		Locale.FRENCH, 	"Commentaire en français"
		), currentLocale);


LocalizedString strGameAdmin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Admin of game", 
		Locale.FRENCH, 	"Administration de la partie"
		), currentLocale);

String strH1 = strGameAdmin.get(currentLocale) + " " + String.valueOf(game.gameNumber);
%>

<!DOCTYPE html>
<html>
<head>
<title><%= strH1 %></title>
<%@include file="head.jsp" %>
<link href='../css/gameYearly_backend.css' rel='stylesheet' type='text/css'>
</head>
<body>
<%@include file="header.jsp" %>

<h1 class="grayColor"><%= strH1 %></h1>
<div class="bar grayBackgroundColor"></div>

	<%
	for(LocalizedString error : errorList)
	{
		%>
		<span class="error"><%= error %></span><br/>
		<%
	}
	%>
	<a href="../game?gameId=<%= game._id %>"><%= strGamePage %></a>
	
	<%
	if(game.containsStartGameEvent())
	{
	%>
	<form method="post" onsubmit="return confirm('<%= strSubmitConfirm %>');">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.START_GAME.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strRestartGame %></h2>
		<input type="submit" value="<%= strRestartGame %>" />
	</form>
	<%
	}
	else
	{
		%>
		<form method="post">
			<input type="hidden" name="gameEvent" value="<%= GameEventEnum.START_GAME.toString() %>" />
			<input type="hidden" name="id" value="<%= game._id %>" />
			<h2><%= strStartGame %></h2>
			<input type="submit" value="<%= strStartGame %>" />
		</form>
		<%
	}
	%>
	
	<%
	if(game.containsStartGameEvent())
	{
	%>
	<br/>
	
	<% out.write(GameYearlyView.getHtmlForGame(game, currentLocale)); %>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.DID_NOT_SCORE.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strDidNotScore %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	for(School school : game.getSchools())
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<br/>
		<% out.write(GameController.outputAddAfterForView(game, currentLocale)); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.SCHOOL_PENALTY.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strSchoolPenalty %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	for(School school : game.getSchools())
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<br/>
		<%= strPointDeduction %><input type="number" name="points" value="0" />
		<br/>
		<% out.write(GameController.outputAddAfterForView(game, currentLocale)); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.SCHOOL_PENALTY_PERCENTAGE.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strSchoolPenaltyPercentage %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	for(School school : game.getSchools())
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<br/>
		<%= strPercentDeduction %><input type="number" name="percentage" value="0" />
		<br/>
		<% out.write(GameController.outputAddAfterForView(game, currentLocale)); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.TEAM_PENALTY.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strTeamPenalty %></h2>
		<%= strTeam %>
		<select name="team">
	<% 	for(TeamEnum teamEnum : TeamEnum.values())
		{ %>
			<option value="<%= teamEnum.name() %>"> <%= teamEnum.name() %></option>
	 <% } %>
		</select>
		<br/>
		<%= strPointDeduction %><input type="number" name="points" value="0" />
		<br/>
		<% out.write(GameController.outputAddAfterForView(game, currentLocale)); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.POINT_MODIFIER.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strPointModifier %></h2>
		<%= strTeam %>
		<select name="team">
	<% 	for(TeamEnum teamEnum : TeamEnum.values())
		{ %>
			<option value="<%= teamEnum.name() %>"> <%= teamEnum.name() %></option>
	 <% } %>
		</select>
		<br/>
		<%= strPoints %><input type="number" name="points" value="0" /><br/>
		<%= strCommentEnglish %><input type="text" name="commentEn" /><br/>
		<%= strCommentFrench %><input type="text" name="commentFr" />
		<br/>
		<% out.write(GameController.outputAddAfterForView(game, currentLocale)); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.MISCONDUCT_PENALTY.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strMisconductPenalty %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	for(School school : game.getSchools())
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<br/>
		<% out.write(GameController.outputAddAfterForView(game, currentLocale)); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<% if( !game.containsEndGameEvent() )
		{
		%>
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.END_GAME.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strEndGame %></h2>
		<input type="submit" value="<%= strEndGame %>" />
	</form>
	<%
		}
	%>
	<%
	}
	%>
	
	<h1><%= strGameEvents %></h1>
	
	<table>
		<tr><th>#</th><th><%= strTimeInGame %></th><th><%= strBlueScore %></th><th><%= strYellowScore %></th><th><%= strEvent %></th><th><%= strAction %></th></tr>
	<%
		int i = 0;
		for(GameState state : game.getGameStates())
		{
			DateTime timeInGame = game.getTimeInGame(state);
		%>
		<tr>
			<td><%= String.valueOf(i + 1) %></td>
			<td><%= timeInGame.getMinuteOfHour() + ":" + (timeInGame.getSecondOfMinute() < 10 ? "0" : "") + timeInGame.getSecondOfMinute() %></td>
			<td><%= state.blueScore %></td>
			<td><%= state.yellowScore %></td>
			<td><%= state.lastGameEvent.getLocalizedString(currentLocale) %></td>
			<td>
			<% 	if( state.lastGameEvent.getGameEventEnum() != GameEventEnum.START_GAME &&
					state.lastGameEvent.getGameEventEnum() != GameEventEnum.END_GAME )
				{ %>
			<form method="post" >
				<input type="hidden" name="gameEvent" value="delete" />
				<input type="hidden" name="id" value="<%= game._id %>" />
				<input type="hidden" name="gameEventIndex" value="<%= i %>" />
				<input type="submit" value="<%= strDelete %>" />
			</form>
			<%
				}
			%>
			</td>
		</tr>
		<%
			i++;
		}
		%>
	</table>

<script>
<% out.write(GameYearlyView.getScripts()); %>
</script>
<%@include file="footer.jsp" %>
</body>
</html>