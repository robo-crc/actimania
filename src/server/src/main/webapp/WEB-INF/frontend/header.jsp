<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>

<%
LocalizedString strLiveGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "LIVE GAME", 
		Locale.FRENCH, 	"PARTIE EN COURS"
		), request.getLocale());

LocalizedString strPreliminary = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PRELIMINARY", 
		Locale.FRENCH, 	"PRÉLIMINAIRE"
		), request.getLocale());

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PLAYOFF", 
		Locale.FRENCH, 	"ÉLIMINATOIRES"
		), request.getLocale());

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHEDULE", 
		Locale.FRENCH, 	"HORAIRE"
		), request.getLocale());

%>

<div class="header">
	<img class="headerLogo" src="images/crc.jpg" />
	
	<a class="headerInstagram" href="http://www.instagram.com/robocrc" target="_blank">
		<img src="images/instagram.svg" />
	</a>
	<a class="headerFacebook" href="https://www.facebook.com/roboCRC" target="_blank">
		<img src="images/facebook.svg" />
	</a>
	
	<div class="headerRubban grayBackgroundColor">
		<a class="headerLive" href="live"><%= strLiveGame %></a>
		<a class="headerSchedule" href="schedule"><%= strSchedule %></a>
		<a class="headerRanking" href="ranking"><%= strPreliminary %></a>
		<a class="headerPlayoff" href="playoff"><%= strPlayoff %></a>
	</div>
</div>