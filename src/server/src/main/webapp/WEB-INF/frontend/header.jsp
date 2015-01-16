<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>

<%
LocalizedString strLiveGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live game", 
		Locale.FRENCH, 	"Partie en cours"
		), request.getLocale());

LocalizedString strRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Ranking", 
		Locale.FRENCH, 	"Classement"
		), request.getLocale());

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), request.getLocale());

LocalizedString strInstagram = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Instagram", 
		Locale.FRENCH, 	"Instagram"
		), request.getLocale());

LocalizedString strCRC = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "CRC", 
		Locale.FRENCH, 	"CRC"
		), request.getLocale());

%>

<a href="live"><%= strLiveGame %></a><br/>
<a href="schedule"><%= strSchedule %></a><br/>
<a href="ranking"><%= strRanking %></a><br/>
<a href="http://www.instagram.com/@robocrc"><%= strInstagram %></a><br/>
<a href="http://www.robo-crc.com/"><%= strCRC %></a><br/>