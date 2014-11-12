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

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strGameAdmin %></title>
<link rel="icon" type="image/png" href="favicon.png">

</head>
<body>
	<%
	for(LocalizedString error : errorList)
	{
		%>
		<span class="error"><%= error %></span><br/>
		<%
	}
	%>
	
	<form method="post" 
	<% 	if(game.gameEvents.size() > 0)
		{ %>
		onsubmit="return confirm('<%= strSubmitConfirm %>');"
	<%	} %>
		>
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.START_GAME.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<input type="submit" value="<%= strStartGame %>" />
	</form>
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.TARGET_HIT.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<select name="side">
	<% 	for(SideEnum side : SideEnum.values())
		{ %>
			<option value="<%= side.toString() %>"><%= side.toString().toLowerCase() %></option>
	 <% } %>
		</select>
		<select name="target">
	<% 	for(TargetEnum target : TargetEnum.values())
		{ %>
			<option value="<%= target.toString() %>"><%= target.toString().toLowerCase() %></option>
	 <% } %>
		</select>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	
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
			<td><form method="post" >
				<input type="hidden" name="action" value="delete" />
				<input type="hidden" name="id" value="<%= game._id %>" />
				<input type="hidden" name="index" value="<%= i %>" />
				<input type="submit" value="<%= strDelete %>" />
			</form></td>
		</tr>
		<%
			i++;
		}
		%>
	</table>
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>
</html>