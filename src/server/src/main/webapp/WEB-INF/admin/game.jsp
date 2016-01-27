<%@page import="com.backend.models.enums.TriangleStateEnum"%>
<%@page import="com.backend.models.Hole"%>
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

LocalizedString strScoreboardUpdate = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Update score board", 
		Locale.FRENCH, 	"Mettre à jour le tableau de pointage"
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


LocalizedString strSide1Triangle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Triangle side 1", 
		Locale.FRENCH, 	"Triangle côté 1"
		), currentLocale);

LocalizedString strSide2Triangle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Triangle side 2", 
		Locale.FRENCH, 	"Triangle côté 2"
		), currentLocale);


String strH1 = strGameAdmin.get(currentLocale) + " " + String.valueOf(game.gameNumber);
%>

<!DOCTYPE html>
<html>
<head>
<title><%= strH1 %></title>
<%@include file="head.jsp" %>

<script>

$('select').on('change', function(){
	alert("12345");
	if(($this).val() == "EMPTY")
	{
		$(this).css("background-color", "white");	
	}
	else if(($this).val() == "BLUE")
	{
		$(this).css("background-color", "lightblue");
	}
	else if(($this).val() == "YELLOW")
	{
		$(this).css("background-color", "yellow");
	}
	else	
	{
		alert($(this).val());
	}
});
/*
$(document).ready(function(){
	$( ".spinner" ).numeric();
	$( ".spinner" ).spinner();
});
*/
</script>

</head>
<body>
<%@include file="header.jsp" %>

<h1 class="grayColor"><%= strH1 %></h1>
<div class="bar grayBackgroundColor"></div>

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

%>

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
	
	<form method="post">
		<input type="hidden" name="gameEvent" value="<%= GameEventEnum.SCOREBOARD_UPDATED.toString() %>" />
		<input type="hidden" name="id" value="<%= game._id %>" />
		<h2><%= strScoreboardUpdate %></h2>
		
		
		<table>
		<tr>
		<%= strSide1Triangle %>
		<%
		Hole[] triangleLeft = game.getGameStates().get(game.getGameStates().size() - 1).triangleLeft;
		for(int holeNb = 0; holeNb < triangleLeft.length; holeNb++)
		{
			/*
		       0
			  1 2
			 3 4 5
			6 7 8 9
		 */
			if(holeNb == 1 || holeNb == 3 || holeNb == 6)
			{
			%>
				</tr>
				<tr>
			<%
			}
			
			String firstTD = "<td>";
			if(holeNb == 0 || holeNb == 1 || holeNb == 3)
			{
				firstTD = "<td colspan=\"8\" align=\"center\"><table><tr><td>";
			}
			%>
			<%= firstTD %><%= holeNb %></td>
			<td width="90px">
			<%
			Hole hole = triangleLeft[holeNb];
			for(int triangleStateNb = 0; triangleStateNb < hole.triangleStates.length; triangleStateNb++)
			{
				 TriangleStateEnum triangleState = hole.triangleStates[triangleStateNb];
				%>
				
				<select class="selectColor" name="hole_<%=SideEnum.SIDE1.toString()%>_<%=holeNb%>_<%=triangleStateNb%>">
				<%
				for(TriangleStateEnum triangleStateEnum : TriangleStateEnum.values())
				{
					String isSelected = "";
					if(triangleStateEnum == triangleState)
					{
						isSelected = " selected";
					}
				%>
					<option value="<%= triangleStateEnum.name() %>" class="triangleStateEnum_<%= triangleStateEnum.name() %>"<%=isSelected%>><%= triangleStateEnum.name() %></option>
		 		<%
				}
				%>
				</select>
			<%
			}
			String lastTd = "</td>";
			if(holeNb == 0 || holeNb == 2 || holeNb == 5)
			{
				lastTd = "</td></tr></table></td>";
			}
			%>
			<%= lastTd %>
			<%
	 	} 
	 	%>
	 	<tr>
	 	</table>
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
</body>
</html>