<%@page import="com.frontend.controllers.yearly.GameYearlyController"%>
<%@page import="com.backend.models.SchoolInteger"%>
<%@page import="com.framework.helpers.Helpers"%>
<%@page import="com.backend.models.GameEvent.SchoolPenaltyEvent"%>
<%@page import="org.joda.time.Duration"%>
<%@page import="java.io.IOException"%>
<%@page import="com.backend.models.enums.SideEnum"%>
<%@page import="com.backend.models.enums.GameEventEnum"%>
<%@page import="org.joda.time.DateTime"%>
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

LocalizedString strBlueTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Blue Team", 
		Locale.FRENCH, 	"Équipe bleue"
		), currentLocale);

LocalizedString strYellowTeam = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Yellow Team", 
		Locale.FRENCH, 	"Équipe jaune"
		), currentLocale);

LocalizedString strGameNotStarted = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game has not been played yet", 
		Locale.FRENCH, 	"Cette parte n'a pas encore été jouée"
		), currentLocale);

LocalizedString strGameAdministration = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game administration", 
		Locale.FRENCH, 	"Administration de la partie"
		), currentLocale);

LocalizedString strLiveHeader = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, " - Live!", 
		Locale.FRENCH, 	" - En cours!"
		), currentLocale);


LocalizedString strPoints = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Points", 
		Locale.FRENCH, 	"Points"
		), currentLocale);

String strH1 = strGame.get(currentLocale) + " " + String.valueOf(game.gameNumber);
if(isLive)
{
	strH1 += strLiveHeader.get(currentLocale);
}
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<title><%= strH1 %></title>
<link rel="stylesheet" type="text/css" href="css/game.css"/>
<link rel="stylesheet" type="text/css" href="css/bjqs.css"/>
<link rel="stylesheet" type="text/css" href="css/jquery.countdown.css"/>
<script type="text/javascript" src="jquery/bjqs-1.3.js"></script>
<script type="text/javascript" src="jquery/jquery.plugin.min.js"></script> 
<script type="text/javascript" src="jquery/jquery.countdown.min.js"></script>
	
    <script>
    $(function() {
    <%   
	    boolean hasCountdown = false;
	    ArrayList<GameState> gameStates = game.getGameStates();
	    
		if(isLive && gameStates.size() > 0 && gameStates.get(gameStates.size() - 1).lastGameEvent.getGameEventEnum() != GameEventEnum.END_GAME)
		{
			hasCountdown = true; 
			
			out.write("var liftoffTime = new Date(" + (game.getGameEvents().get(0).getTime().plus(Game.getGameLength())).getMillis() + ");\n" );
			out.write("$('.countdown').countdown({until: liftoffTime, compact: true, layout: '{mn}{sep}{snn}', description: ''})");
		}
		
	    if( gameStates.size() > 0 )
	    {
	    %>
	    	$('#game-slideshow').bjqs({
	    		width : 1190,
	    		height : <%= GameYearlyController.GAME_PORTION_HEIGHT %>,
	            responsive : false,
	            showcontrols : false,
	            automatic : false,
	            randomstart : false,
	            centermarkers : true,
	            startAt : <%= game.getGameEvents().size() %>
	        });
	    <%
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
<h1 class="grayColor gameH1"><%= strH1 %></h1>
<h2 class="grayColor gameH2"><%= Helpers.dateTimeFormatter.print(game.scheduledTime) %></h2>
<%
}
%>

<div class="bar grayBackgroundColor"></div>
<div class="clear"></div>

<%
if( gameStates.size() == 0)
{
	%>
	<div class="team blueTeam grayBackgroundColor">
		<div class="blueBackgroundColor gameBarNotStarted"></div>
		<table class="wrapperNotStarted">
			<tr>
			<td class="teamName">
			<%
				for(School school : game.blueTeam)
				{
			%>
				<div class="gameSchoolDiv clear">
					<div class="gameSchoolInner">
						<img src="images/schools/32x32/<%= school.getCompactName() %>.png" />
					</div>
					<a target="_blank" class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
				</div>
				<%
					}
				%>
			</td>
			<td class="whiteDiv"></td>
			<td class="gameScore whiteColor">
			</td>
			</tr>
		</table>
	</div>
			
	<div class="team grayBackgroundColor">
		<div class="yellowBackgroundColor gameBarNotStarted"></div>
		<table class="wrapperNotStarted">
			<tr>
				<td class="teamName">
				<%
				for(School school : game.yellowTeam)
				{
				%>
					<div class="gameSchoolDiv clear">
						<div class="gameSchoolInner">
							<img src="images/schools/32x32/<%= school.getCompactName() %>.png" />
						</div>
						<a target="_blank" class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
					</div>
				<%
				}
				%>
				</td>
				<td class="whiteDiv"></td>
				<td class="gameScore whiteColor">
				</td>
			</tr>
		</table>
	</div>
	
	<div class="clear"></div>

	<h2 class="grayColor gameH2NotStarted"><%= strGameNotStarted %></h2>
	<%
}
%>

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
			<div class="grayBackgroundColor center gameTimerOuter">
				<div class="timer whiteColor gameTimer
				<% 
				if(hasCountdown && i == gameStates.size() - 1) 
				{ 
					out.write("countdown"); 
				}%>">
					<%= timeInGame.getMinuteOfHour() + ":" + (timeInGame.getSecondOfMinute() < 10 ? "0" : "") + timeInGame.getSecondOfMinute() %>
				</div>
				<div class="gameEvent whiteColor"><%= state.lastGameEvent.getLocalizedString(currentLocale) %></div>
			</div>
			
			<div class="team blueTeam grayBackgroundColor">
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
							hasPenalty |= state.misconductPenalties.contains(school) || state.penalties.contains(school) || state.didNotScore.contains(school);
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
								hasPenalty |= state.misconductPenalties.contains(school) || state.penalties.contains(school) || state.didNotScore.contains(school);
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
			<%= GameYearlyController.getHtmlForView(state) %>
			<br/>
			<div class="clear"></div>
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
		</li>
	<%
	}
	%>
	</ul>
</div>
<div class="clear"></div>




 
<%
if( isLoggedIn )
{
%>
<br/>
<br/>
<a target="_blank" href="admin/game?gameId=<%= game._id %>"><%= strGameAdministration %></a><br/>
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