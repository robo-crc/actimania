%@page import="java.io.IOException"%>
<%@page import="java.io.IOException"%>
<%@page import="com.backend.models.enums.SideEnum"%>
<%@page import="com.backend.models.enums.GameEventEnum"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="com.backend.models.enums.TargetEnum"%>
<%@page import="com.backend.models.enums.ActuatorEnum"%>
<%@page import="com.backend.models.GameState"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.Game"%>
<%@page import="com.backend.models.School"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% 
@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>) request.getAttribute("errorList"); 

Game game = (Game) request.getAttribute("game");

Locale currentLocale = request.getLocale();

LocalizedString strGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game", 
		Locale.FRENCH, 	"Partie"
		), currentLocale);

LocalizedString strBlueTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue Team", 
		Locale.FRENCH, 	"Équipe bleue"
		), currentLocale);

LocalizedString strYellowTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow Team", 
		Locale.FRENCH, 	"Équipe jaune"
		), currentLocale);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strGame %></title>
</head>
<body>

<h2><%= strBlueTeam %></h2>
<div class="teamName">
<%
for(School school : game.blueTeam)
{
	out.print(school.name + "<br/>");
}
%>
</div>


<h2><%= strYellowTeam %></h2>
<div class="teamName">
<%
for(School school : game.yellowTeam)
{
	out.print(school.name + "<br/>");
}
%>
</div>

<div class="gameStates">
<%
DateTime gameLength = Game.getGameLength();

for(GameState state : game.getGameStates())
{
	DateTime timeInGame = new DateTime(gameLength.getMillis() - (state.lastGameEvent.time.getMillis() - game.gameEvents.get(0).time.getMillis()));

	boolean blueScored = false;
	boolean yellowScored = false;
	if(state.lastGameEvent.gameEvent == GameEventEnum.TARGET_HIT)
	{
		ActuatorEnum targetHitColor = state.actuatorsStates[state.lastGameEvent.side.ordinal()][state.lastGameEvent.target.ordinal()];
		if( targetHitColor == ActuatorEnum.BLUE )
		{
			blueScored = true;
		}
		else if(targetHitColor == ActuatorEnum.YELLOW)
		{
			yellowScored = true;
		}
	}
%>
<div class="timer"><%= timeInGame.minuteOfHour().toString() + ":" + timeInGame.secondOfMinute().toString() %></div>
<div class="blueScore<% if(blueScored) out.write("teamScored"); %>"><%= state.blueScore %></div>
<div class="yellowScore<% if(blueScored) out.write("teamScored");%>"><%= state.yellowScore %></div>

<%! 
	public void outputTargetActuator(GameState state, SideEnum side, TargetEnum target, boolean isTarget, JspWriter out) throws IOException
	{
		ActuatorEnum actuatorColor = state.actuatorsStates[side.ordinal()][target.ordinal()];
		out.write("<div class=\"" + target.name() + "Target " + side.name() + "Side " +  actuatorColor.name() + "Actuator");
		
		if(isTarget)
		{
			out.write(" isTarget");
		}
		else
		{
			out.write(" isActuator");
		}
		
		if(state.lastGameEvent.gameEvent == GameEventEnum.ACTUATOR_CHANGED)
		{
			if(state.lastGameEvent.side == side && state.lastGameEvent.target == target)
			{
				out.write(" ActuatorChangedEvent");
			}
		}
		else if(isTarget && state.lastGameEvent.gameEvent == GameEventEnum.TARGET_HIT)
		{
			if(state.lastGameEvent.side == side && state.lastGameEvent.target == target)
			{
				out.write(" TargetHitEvent");
			}
		}
		out.write("\"></div>");
	}
%>
<%
// Targets
outputTargetActuator(state, SideEnum.BLUE, TargetEnum.LOW, true, out);
outputTargetActuator(state, SideEnum.BLUE, TargetEnum.MID, true, out);
outputTargetActuator(state, SideEnum.BLUE, TargetEnum.HIGH, true, out);

outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.LOW, true, out);
outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.MID, true, out);
outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.HIGH, true, out);

// Actuators

outputTargetActuator(state, SideEnum.BLUE, TargetEnum.LOW, false, out);
outputTargetActuator(state, SideEnum.BLUE, TargetEnum.MID, false, out);
outputTargetActuator(state, SideEnum.BLUE, TargetEnum.HIGH, false, out);

outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.LOW, false, out);
outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.MID, false, out);
outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.HIGH, false, out);
%>

<%
}
%>
</div>


</body>
</html>