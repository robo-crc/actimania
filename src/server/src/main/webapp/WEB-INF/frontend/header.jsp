<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>

<%
LocalizedString strLiveGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "LIVE GAME", 
		Locale.FRENCH, 	"PARTIE EN COURS"
		), request.getLocale());

LocalizedString strRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "RANKING", 
		Locale.FRENCH, 	"CLASSEMENT"
		), request.getLocale());

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHEDULE", 
		Locale.FRENCH, 	"HORAIRE"
		), request.getLocale());

LocalizedString strFacebook = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "FACEBOOK", 
		Locale.FRENCH, 	"FACEBOOK"
		), request.getLocale());

LocalizedString strInstagram = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "INSTAGRAM", 
		Locale.FRENCH, 	"INSTAGRAM"
		), request.getLocale());

LocalizedString strCRC = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "CRC", 
		Locale.FRENCH, 	"CRC"
		), request.getLocale());

%>

<div class="header">
	<img class="headerLogo" src="images/crc.jpg" />
	<img class="headerInstagram" src="images/instagram.svg" />
	<img class="headerFacebook" src="images/facebook.svg" />

	<div class="headerRubban grayBackgroundColor">
		<a class="headerLink headerLive" href="live"><%= strLiveGame %></a>
		<a class="headerLink headerSchedule" href="schedule"><%= strSchedule %></a>
		<a class="headerLink headerRanking" href="ranking"><%= strRanking %></a>
		<a class="headerLink" href="https://www.facebook.com/roboCRC"><%= strFacebook %></a>
		<a class="headerLink" href="http://www.instagram.com/robocrc"><%= strInstagram %></a>
		<a class="headerLink" href="http://www.robo-crc.ca/"><%= strCRC %></a>
	</div>
</div>