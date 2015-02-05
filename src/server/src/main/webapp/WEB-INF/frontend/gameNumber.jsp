<!DOCTYPE html>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.Game"%>
<%@page import="com.framework.helpers.Helpers"%>

<html>
<head>
<%@include file="head.jsp" %>
	<script>
    $(function() 
    {
	    function refreshPage() 
	    {
	        var source = new EventSource('gameRefresh');
	
	        source.onmessage = function(event) 
	        {
	            location.reload();
	        };
	    }
	    window.addEventListener("load", refreshPage);
    });
    </script>
<%
Locale currentLocale = request.getLocale();

LocalizedString strGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Game", 
		Locale.FRENCH, 	"Partie"
		), currentLocale);

LocalizedString strLive = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "LIVE", 
		Locale.FRENCH, 	"EN DIRECT"
		), currentLocale);

Game game = (Game) request.getAttribute("game");
%>
</head>
<body>
<div class="gameNumberPage">
<h1 class="grayColor gameNumberH1"><%= strGame.get(currentLocale) + " " + String.valueOf(game.gameNumber) %></h1>
<h2 class="grayColor gameH2"><%= Helpers.dateTimeFormatter.print(game.scheduledTime) %></h2>
<div class="bar grayBackgroundColor gameNumberBar"></div>
<h2 class="grayColor gameNumberLiveStr"><%= strLive %></h2>
</div>
</body>
</html>