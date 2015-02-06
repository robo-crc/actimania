<!DOCTYPE html>
<%@page import="com.backend.models.School"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="com.backend.models.enums.GameEventEnum"%>
<%@page import="com.backend.models.GameState"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.Game"%>
<%@page import="com.framework.helpers.Helpers"%>
<%
Game game = (Game) request.getAttribute("game");
%>
<html>
<head>
<%@include file="head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/game.css"/>
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css"/>
<script type="text/javascript" src="jquery/jquery.plugin.min.js"></script> 
<script type="text/javascript" src="jquery/jquery.countdown.min.js"></script>
    <script>
    $(function() {
    <%   
	    boolean hasCountdown = false;
	    ArrayList<GameState> gameStates = game.getGameStates();
	    GameState state = null;
	    
		if(gameStates.size() > 0)
		{
			state = gameStates.get(gameStates.size() - 1);
			
			if(state.lastGameEvent.getGameEventEnum() != GameEventEnum.END_GAME)
			{
				hasCountdown = true; 
			
				out.write("var liftoffTime = new Date(" + (game.getGameEvents().get(0).getTime().plus(Game.getGameLength())).getMillis() + ");\n" );
				out.write("$('.countdown').countdown({until: liftoffTime, compact: true, layout: '{mn}{sep}{snn}', description: ''})");
			}
		}
		%>
    });

    function refreshPage() {
        var source = new EventSource('gameRefresh');

        source.onmessage = function(event) 
        {
            location.reload();
        };
    }
    window.addEventListener("load", refreshPage);
    
    </script>
<%
Locale currentLocale = request.getLocale();

LocalizedString strPoints = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points", 
		Locale.FRENCH, 	"Points"
		), currentLocale);

LocalizedString strGameNotStarted = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game has not been played yet", 
		Locale.FRENCH, 	"Cette parte n'a pas encore été jouée"
		), currentLocale);
%>
</head>
<body>
<%
if(state == null)
{
%>
<h2 class="grayColor gameH2"><%= strGameNotStarted %></h2>
</body>
<%
}
else
{
	DateTime timeInGame = game.getTimeInGame(state);
%>
	<div class="grayBackgroundColor center gameTimerOuter">
		<div class="timer whiteColor gameTimer
		<% 
		if(hasCountdown)
		{ 
			out.write("countdown"); 
		}%>">
			<%= timeInGame.getMinuteOfHour() + ":" + (timeInGame.getSecondOfMinute() < 10 ? "0" : "") + timeInGame.getSecondOfMinute() %>
		</div>
		<div class="gameEvent whiteColor"><%= state.lastGameEvent.getLocalizedString(currentLocale) %></div>
	</div>
	
	<div class="gameScoreBlue team blueTeam grayBackgroundColor">
		<div class="blueBackgroundColor gameBar"></div>
		<table class="wrapper">
			<tr>
			<td class="teamName">
			<%
				boolean hasPenalty = false;
				for(School school : game.blueTeam)
				{
			%>
				<div class="gameSchoolDiv clear">
					<div class="gameSchoolInner">
						<img src="images/schools/32x32/<%= school.getCompactName() %>.png" />
					</div>
					<a target="_blank" class="scheduleSchoolText <% if(state.misconductPenalties.contains(school)) { out.write("gameMisconduct"); } %>" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
					<%
					hasPenalty |= state.misconductPenalties.contains(school) || state.penalties.contains(school);
					%>
				</div>
				<%
					}
				%>
			</td>
			<td class="whiteDiv"></td>
			<td class="gameScore whiteColor">
				<%
				if(!hasPenalty)
				{
				%>
				<div class="schedulePoints"><%= state.blueScore %></div>
				<div class="schedulePointsStr"><%= strPoints %></div>
				<%
				}
				else
				{
					for(School school : game.blueTeam)
					{
						%>
						<div class="schedulePoints"><%= game.getScore(school, state) %></div>
						<%
					}
				}
				%>
			</td>
			</tr>
		</table>
	</div>
	
	<div class="team grayBackgroundColor">
		<div class="yellowBackgroundColor gameBar"></div>
		<table class="wrapper">
			<tr>
				<td class="teamName">
				<%
					hasPenalty = false;
					for(School school : game.yellowTeam)
					{
				%>
					<div class="gameSchoolDiv clear">
						<div class="gameSchoolInner">
							<img src="images/schools/32x32/<%= school.getCompactName() %>.png" />
						</div>
						<a target="_blank" class="scheduleSchoolText <% if(state.misconductPenalties.contains(school)) { out.write("gameMisconduct"); } %>" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
					</div>
					<%
						hasPenalty |= state.misconductPenalties.contains(school) || state.penalties.contains(school);
					}
					%>
				</td>
				<td class="whiteDiv"></td>
				<td class="gameScore whiteColor">
					<%
						if(!hasPenalty)
						{
						%>
					<div class="schedulePoints"><%= state.yellowScore %></div>
					<div class="schedulePointsStr"><%= strPoints %></div>
						<%
						}
						else
						{
							for(School school : game.yellowTeam)
							{
								%>
								<div class="schedulePoints"><%= game.getScore(school, state) %></div>
								<%
							}
						}
						%>
				</td>
			</tr>
		</table>
	</div>
<%
}
%>
</body>
</html>