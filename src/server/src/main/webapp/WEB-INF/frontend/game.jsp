<%@page import="com.backend.models.SchoolPenalty"%>
<%@page import="com.backend.models.GameEvent.TargetHitEvent"%>
<%@page import="com.backend.models.GameEvent.ActuatorStateChangedEvent"%>
<%@page import="com.framework.helpers.Helpers"%>
<%@page import="com.backend.models.GameEvent.SchoolPenaltyEvent"%>
<%@page import="org.joda.time.Duration"%>
<%@page import="java.io.IOException"%>
<%@page import="com.backend.models.enums.SideEnum"%>
<%@page import="com.backend.models.enums.GameEventEnum"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="com.backend.models.enums.TargetEnum"%>
<%@page import="com.backend.models.enums.ActuatorStateEnum"%>
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
	Game game = (Game) request.getAttribute("game");

Locale currentLocale = request.getLocale();

LocalizedString strGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game", 
		Locale.FRENCH, 	"Partie"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Ranking", 
		Locale.FRENCH, 	"Classement"
		), currentLocale);

LocalizedString strPenalties = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Penalties", 
		Locale.FRENCH, 	"Pénalitées"
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

<%!public void outputTargetActuator(GameState state, SideEnum side, TargetEnum target, JspWriter out) throws IOException
{
	ActuatorStateEnum actuatorColor = state.actuatorsStates[side.ordinal()][target.ordinal()];
	out.write("\t<img src=\"images/" + "side" + side.name() + "_target" + target.name() + "_actuator" + actuatorColor.name() + ".png\"" );
	
	out.write(" class=\"fieldImage");

	if(state.lastGameEvent.getGameEventEnum() == GameEventEnum.ACTUATOR_STATE_CHANGED)
	{
		ActuatorStateChangedEvent actuatorStateChanged = (ActuatorStateChangedEvent) state.lastGameEvent;
		if(actuatorStateChanged.side == side && actuatorStateChanged.target == target)
		{
			out.write(" ActuatorChangedEvent");
		}
	}
	else if(state.lastGameEvent.getGameEventEnum() == GameEventEnum.TARGET_HIT)
	{
		TargetHitEvent targetHitEvent = (TargetHitEvent) state.lastGameEvent;
		if(targetHitEvent.side == side && targetHitEvent.target == target)
		{
			out.write(" TargetHitEvent");
		}
	}
	out.write("\"/>\n");
	//out.write("background: url('../images/templates/background_image.jpg') no-repeat center center fixed;")
}%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=strGame%></title>
<link rel="stylesheet" type="text/css" href="css/game.css"/>
<link rel="stylesheet" type="text/css" href="css/bjqs.css"/>
    <script type="text/javascript" src="jquery/jquery.js"></script>
    <script type="text/javascript" src="jquery/bjqs-1.3.js"></script>
	
    <script>
    $(function() {
    	$('#my-slideshow').bjqs({
    		width : 2825,
    		height : 1475,
            responsive : true,
            showcontrols : false,
            automatic : false,
            randomstart : false
        });
    });
    </script>
</head>
<body>

<h1><%=strGame + " " + String.valueOf(game.gameNumber)%></h1>
<div class="scheduledTime"><%=Helpers.dateTimeFormatter.print(game.scheduledTime)%></div>

<div class="team">
	<h2><%=strBlueTeam%></h2>
	<div class="teamName">
	<%
		for(School school : game.blueTeam)
		{
	%>
		<a href="school?schoolId=<%=school._id%>"><%=school.name%></a><br/>
		<%
			}
		%>
	</div>
</div>

<div class="team">
	<h2><%=strYellowTeam%></h2>
	<div class="teamName">
	<%
		for(School school : game.yellowTeam)
		{
	%>
		<a href="school?schoolId=<%=school._id%>"><%=school.name%></a><br/>
		<%
			}
		%>
	</div>
</div>

<div class="clear"></div>


<div id="my-slideshow">
	<ul class="bjqs">
	<!-- Slides Container -->
		<%
		for(GameState state : game.getGameStates())
		{
			DateTime timeInGame = game.getTimeInGame(state);

			boolean blueScored = false;
			boolean yellowScored = false;
			if(state.lastGameEvent.getGameEventEnum() == GameEventEnum.TARGET_HIT)
			{
				TargetHitEvent targetHitEvent = (TargetHitEvent) state.lastGameEvent;
				ActuatorStateEnum targetHitColor = state.actuatorsStates[targetHitEvent.side.ordinal()][targetHitEvent.target.ordinal()];
				if( targetHitColor == ActuatorStateEnum.BLUE )
				{
					blueScored = true;
				}
				else if(targetHitColor == ActuatorStateEnum.YELLOW)
				{
					yellowScored = true;
				}
			}
		%>
		<li>
			<div class="timer"><%= timeInGame.getMinuteOfHour() + ":" + (timeInGame.getSecondOfMinute() < 10 ? "0" : "") + timeInGame.getSecondOfMinute() %></div>
			<div class="blueScore<% if(blueScored) out.write("teamScored"); %>"><%= state.blueScore %></div>
			<div class="yellowScore<% if(blueScored) out.write("teamScored");%>"><%= state.yellowScore %></div>
			<div class="gameEvent"><%= state.lastGameEvent.getLocalizedString(currentLocale) %></div>
			<%
if(state.penalties.size() > 0)
{
%>
<h2><%=strPenalties%></h2>
<%
	for(SchoolPenalty penalty : state.penalties)
	{
		out.print(penalty.school + " : " + penalty.pointsDeduction);
	}
}
%>
			<br/>

			<div class="playfield">
				<img src="images/playfield.png" class="playfieldBackground fieldImage" />
				<%
				// Targets
				outputTargetActuator(state, SideEnum.BLUE, TargetEnum.LOW, out);
				outputTargetActuator(state, SideEnum.BLUE, TargetEnum.MID, out);
				outputTargetActuator(state, SideEnum.BLUE, TargetEnum.HIGH, out);
				
				outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.LOW, out);
				outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.MID, out);
				outputTargetActuator(state, SideEnum.YELLOW, TargetEnum.HIGH, out);
				%>
			</div>
		</li>
	<%
	}
	%>
	</ul>
</div>

<a href="schedule"><%= strSchedule %></a><br/>
<a href="ranking"><%= strRanking %></a>
</body>
</html>