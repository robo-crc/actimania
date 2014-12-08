<%@page import="com.backend.models.GameEvent.GameEvent"%>
<%@page import="com.backend.models.enums.TeamEnum"%>
<%@page import="com.backend.models.enums.ActuatorStateEnum"%>
<%@page import="java.io.IOException"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="com.backend.models.GameState"%>
<%@page import="com.backend.models.enums.TargetEnum"%>
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);

LocalizedString strGameAdmin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game administration", 
		Locale.FRENCH, 	"Administration des parties"
		), currentLocale);

LocalizedString strStartGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Start Game", 
		Locale.FRENCH, 	"Démarrer la partie"
		), currentLocale);

LocalizedString strRestartGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Re-Start Game", 
		Locale.FRENCH, 	"Redémarrer la partie"
		), currentLocale);

LocalizedString strTargetHit = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Target hit", 
		Locale.FRENCH, 	"Démarrer la partie"
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

LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
		), currentLocale);

LocalizedString strUsers = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Users", 
		Locale.FRENCH, 	"Utilisateurs"
		), currentLocale);

LocalizedString strGameEvents = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game events", 
		Locale.FRENCH, 	"Événements de la partie"
		), currentLocale);

LocalizedString strActuatorState = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Actuator state", 
		Locale.FRENCH, 	"État de l'actuateur"
		), currentLocale);

LocalizedString strActuatorStateChanged = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Actuator state change", 
		Locale.FRENCH, 	"Changement de l'état de l'actuateur"
		), currentLocale);

LocalizedString strAddAfter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add this new game event after game event #", 
		Locale.FRENCH, 	"Ajouter ce nouvel événement après l'événement #"
		), currentLocale);

LocalizedString strSchoolPenalty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School Penalty", 
		Locale.FRENCH, 	"Pénalité à une école"
		), currentLocale);

LocalizedString strTeamPenalty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Team Penalty", 
		Locale.FRENCH, 	"Pénalité à une équipe"
		), currentLocale);

LocalizedString strMisconductPenalty = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Misconduct Penalty", 
		Locale.FRENCH, 	"Pénalité de mauvaise conduite"
		), currentLocale);

LocalizedString strEndGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "End game", 
		Locale.FRENCH, 	"Fin de partie"
		), currentLocale);

LocalizedString strGamePage = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Frontend game page", 
		Locale.FRENCH, 	"Page de la partie publique"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strPointModifier = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Point Modifier", 
		Locale.FRENCH, 	"Modifcation de points"
		), currentLocale);

LocalizedString strPoints = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points", 
		Locale.FRENCH, 	"Points"
		), currentLocale);

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
		), currentLocale);

LocalizedString strCommentEnglish = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Comment in english", 
		Locale.FRENCH, 	"Commentaire en anglais"
		), currentLocale);

LocalizedString strCommentFrench = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Comment in french", 
		Locale.FRENCH, 	"Commentaire en français"
		), currentLocale);

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Overall", 
		Locale.FRENCH, 	"Classement final"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="images/favicon.ico" />
<title><%= strGameAdmin %></title>
<link rel="icon" type="image/png" href="favicon.png">

<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="../jquery/jquery.js"></script>
<script src="../jquery/jquery.numeric.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<script>
$(document).ready(function(){
	$( ".spinner" ).spinner();
	$( ".spinner" ).numeric();
});

</script>

</head>
<body>
<%!
public void outputAddAfter(Game game, LocalizedString strAddAfter, JspWriter out) throws IOException
{
	out.write(strAddAfter.toString());
	out.write("<select name=\"insertAfter\">");

	ArrayList<GameEvent> gameEvents = game.getGameEvents();
	int iterationEnd = gameEvents.size();
	if(gameEvents.size() > 0 && gameEvents.get(gameEvents.size() - 1).getGameEventEnum() == GameEventEnum.END_GAME)
	{
		iterationEnd--;
	}
	for(int i = 0; i < iterationEnd; i++)
	{
		String selected = "";
		if(i == iterationEnd - 1)
		{
			selected = "selected=\"selected\"";
		}
		
		out.write("<option " + selected + " value=\"" + String.valueOf(i + 1) + "\">" + String.valueOf(i + 1) + "</option>");
	}
	out.write("</select>");
}

public void outputSideTarget(Locale currentLocale, JspWriter out) throws IOException
{
	LocalizedString strSide = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Side ", 
			Locale.FRENCH, 	"Côté du terrain "
			), currentLocale);
	
	LocalizedString strTarget = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, " Target ", 
			Locale.FRENCH, 	" Cible "
			), currentLocale);
	
	out.write(strSide.toString());
	out.write("<select name=\"side\">");
	
 	for(SideEnum side : SideEnum.values())
	{
		out.write("<option value=\"" + side.toString() + "\">" + side.toString().toLowerCase() + "</option>");
 	}
 	out.write("</select>");
 	out.write("<br/>");
 	out.write(strTarget.toString());
 	
	out.write("<select name=\"target\">");
 	for(TargetEnum target : TargetEnum.values())
	{
		out.write("<option value=\"" + target.toString() + "\">" + target.toString().toLowerCase() + "</option>");
 	}
	out.write("</select>");
}
%>

	<%
	for(LocalizedString error : errorList)
	{
		%>
		<span class="error"><%= error %></span><br/>
		<%
	}
	%>
	
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
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.TARGET_HIT.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strTargetHit %></h2>

		<% outputSideTarget(currentLocale, out); %>
		<br/>
		<% outputAddAfter(game, strAddAfter, out); %>
		<br/>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<br/>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.ACTUATOR_STATE_CHANGED.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strActuatorStateChanged %></h2>
		
		<% outputSideTarget(currentLocale, out); %>
		<br/>
		<%= strActuatorState %>
		<select name="actuatorState">
	<% 	for(ActuatorStateEnum actuatorState : ActuatorStateEnum.values())
		{ %>
			<option value="<%= actuatorState.toString() %>"> <%= actuatorState.toString().toLowerCase() %></option>
	 <% } %>
		</select>
		<br/>
		<% outputAddAfter(game, strAddAfter, out); %>
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
		<%= strPointDeduction %><input class="spinner" name="points" value="0" />
		<br/>
		<% outputAddAfter(game, strAddAfter, out); %>
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
		<%= strPointDeduction %><input class="spinner" name="points" value="0" />
		<br/>
		<% outputAddAfter(game, strAddAfter, out); %>
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
		<%= strPoints %><input class="spinner" name="points" value="0" /><br/>
		<%= strCommentEnglish %><input type="text" name="commentEn" /><br/>
		<%= strCommentFrench %><input type="text" name="commentFr" />
		<br/>
		<% outputAddAfter(game, strAddAfter, out); %>
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
		<% outputAddAfter(game, strAddAfter, out); %>
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
		<tr><td>#</td><td><%= strTimeInGame %></td><td><%= strBlueScore %></td><td><%= strYellowScore %></td><td><%= strEvent %></td><td><%= strAction %></td></tr>
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
	<br/>
	<a href="../game?gameId=<%= game._id %>"><%= strGamePage %></a>
	<br/>
	<a href="../overall"><%= strOverall %></a>
	<br/>
	<a href="../schedule"><%= strSchedule %></a>
	<br/>
	<a href="schools"><%= strSchools %></a>
	<br/>
	<a href="users"><%= strUsers %></a>
	<br/>
	<a href="competition"><%= strCompetition %></a>
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>
</html>