<%@page import="com.backend.models.SchoolInteger"%>
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

<%
Game game = (Game) request.getAttribute("game");
boolean isLoggedIn = ((Boolean) request.getAttribute("isLoggedIn")).booleanValue();
boolean isLive = ((Boolean) request.getAttribute("isLive")).booleanValue();
boolean liveRefresh = ((Boolean) request.getAttribute("liveRefresh")).booleanValue();
boolean showHeader = ((Boolean) request.getAttribute("showHeader")).booleanValue();

Locale currentLocale = request.getLocale();

LocalizedString strGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game", 
		Locale.FRENCH, 	"Partie"
		), currentLocale);

LocalizedString strSchoolPenalties = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School penalties", 
		Locale.FRENCH, 	"Pénalitées d'école"
		), currentLocale);

LocalizedString strMisconductPenalties = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Misconduct penalties", 
		Locale.FRENCH, 	"Pénalité pour conduite anti-sportive"
		), currentLocale);

LocalizedString strBlueTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue Team", 
		Locale.FRENCH, 	"Équipe bleue"
		), currentLocale);

LocalizedString strYellowTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow Team", 
		Locale.FRENCH, 	"Équipe jaune"
		), currentLocale);

LocalizedString strGameAdministration = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game administration", 
		Locale.FRENCH, 	"Administration de la partie"
		), currentLocale);

LocalizedString strLiveHeader = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, " - Live!", 
		Locale.FRENCH, 	" - En cours!"
		), currentLocale);
%>

<%!
public void outputTargetActuator(GameState state, SideEnum side, TargetEnum target, JspWriter out) throws IOException
{
	ActuatorStateEnum actuatorColor = state.actuatorsStates[side.ordinal()][target.ordinal()];
	out.write("\t<img src=\"images/playfield/side" + side.name() + "_target" + target.name() + "_actuator" + actuatorColor.name() + ".svg\"" );
	
	out.write(" class=\"fieldImage");
	out.write("\"/>\n");

	if(state.lastGameEvent.getGameEventEnum() == GameEventEnum.TARGET_HIT)
	{
		TargetHitEvent targetHitEvent = (TargetHitEvent) state.lastGameEvent;
		if(targetHitEvent.side == side && targetHitEvent.target == target && actuatorColor != ActuatorStateEnum.CLOSED)
		{
			out.write("\t<img src=\"images/blip" + actuatorColor.name() + ".gif\" class=\"targetValue targetHit side" + side.name() + "target" + target.name() + "\">" );
		}
	}
	
	int targetValue = GameState.calculateTargetHitValue(state.actuatorsStates, side, target);
	out.write("\t<div class=\"targetValue side" + side.name() + "target" + target.name() + " actuator" + actuatorColor.name() + "\">" + targetValue + "</div>" );
}
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<title><%=strGame%></title>
<link rel="stylesheet" type="text/css" href="css/game.css"/>
<link rel="stylesheet" type="text/css" href="css/bjqs.css"/>
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css"/>
<script type="text/javascript" src="jquery/bjqs-1.3.js"></script>
<script type="text/javascript" src="jquery/jquery.plugin.min.js"></script> 
<script type="text/javascript" src="jquery/jquery.countdown.min.js"></script>
	
    <script>
    $(function() {
    	$('#game-slideshow').bjqs({
    		width : 1000,
    		height : 900,
            responsive : false,
            showcontrols : false,
            automatic : false,
            randomstart : false,
            centermarkers : false,
            startAt : <%= game.getGameEvents().size() %>
        });
    
	    <%
	    boolean hasCountdown = false;
	    ArrayList<GameState> gameStates = game.getGameStates();
	    
		if(isLive && gameStates.size() > 0 && gameStates.get(gameStates.size() - 1).lastGameEvent.getGameEventEnum() != GameEventEnum.END_GAME)
		{
			hasCountdown = true; 
			
			out.write("var liftoffTime = new Date(" + (game.getGameEvents().get(0).getTime().plus(Game.getGameLength())).getMillis() + ");\n" );
			out.write("$('.countdown').countdown({until: liftoffTime, compact: true, layout: '{mn}{sep}{snn}', description: ''})");
		}
	    %>
    });
    
    <%
    if(liveRefresh || isLive) 
    {
    %>
    function refreshPage() {
        var source = new EventSource('gameRefresh');

        source.onmessage = function(event) 
        {
            location.reload();
        };
    }
    window.addEventListener("load", refreshPage);
    <%
    }
    %>
    
    </script>
</head>
<body>
<% 
if(showHeader)
{
%>
<%@include file="header.jsp" %>
<%
}
%>

<h1><%=strGame + " " + String.valueOf(game.gameNumber)%><% if(isLive) out.write(strLiveHeader.get(currentLocale)); %></h1>
<div class="scheduledTime"><%=Helpers.dateTimeFormatter.print(game.scheduledTime)%></div>

<div class="team">
	<h2><%=strBlueTeam%></h2>
	<div class="teamName">
	<%
		for(School school : game.blueTeam)
		{
	%>
		<a target="_blank" href="school?schoolId=<%=school._id%>"><%=school.name%></a><br/>
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
		<a target="_blank" href="school?schoolId=<%=school._id%>"><%=school.name%></a><br/>
		<%
			}
		%>
	</div>
</div>

<div class="clear"></div>

<div id="game-slideshow">
	<ul class="bjqs">
	<!-- Slides Container -->
		<%
		for(int i = 0; i < gameStates.size(); i++)
		{
			GameState state = gameStates.get(i);
			DateTime timeInGame = game.getTimeInGame(state);
			
		%>
		<li>
			<div class="timer
			<% 
			if(hasCountdown && i == gameStates.size() - 1) 
			{ 
				out.write("countdown"); 
			}%>">
				<%= timeInGame.getMinuteOfHour() + ":" + (timeInGame.getSecondOfMinute() < 10 ? "0" : "") + timeInGame.getSecondOfMinute() %>
			</div>
			<div class="blueScore"><%= state.blueScore %></div>
			<div class="yellowScore"><%= state.yellowScore %></div>
			<br/>
<%
if(state.penalties.size() > 0)
{
%>
<h2><%= strSchoolPenalties %></h2>
<%
	for(SchoolInteger penalty : state.penalties)
	{
		out.print(penalty.name + " : " + penalty.integer + "<br/>");
	}
}

if(state.misconductPenalties.size() > 0)
{
%>
<h2><%= strMisconductPenalties %></h2>
<%
	for(School school : state.misconductPenalties)
	{
		out.print(school.name + "<br/>");
	}
}
%>
	
<%
if(isLive && state.lastGameEvent.getGameEventEnum() == GameEventEnum.END_GAME)
{
%>
	<audio autoplay>
		<source src="sounds/horn.mp3" type="audio/mpeg">
	</audio>
<%
}
%>
			<br/>
			<h3 class="gameEvent"><%= state.lastGameEvent.getLocalizedString(currentLocale) %></h3>
			<div class="playfield">
				<img src="images/playfield/playfield.svg" class="playfieldBackground fieldImage" />
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

 
<%
if( isLoggedIn )
{
%>
<a href="admin/game?gameId=<%= game._id %>"><%= strGameAdministration %></a><br/>
<%
}
%>
<% 
if(showHeader)
{
%>
<%@include file="footer.jsp" %>
<%
}
%>
<script type="text/javascript" src="jquery/iframeresizer/js/iframeResizer.contentWindow.min.js"></script>
</body>
</html>