<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "OVERALL", 
		Locale.FRENCH, 	"CLASSEMENT"
		), request.getLocale());

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHEDULE", 
		Locale.FRENCH, 	"HORAIRE"
		), request.getLocale());

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "COMPETITION", 
		Locale.FRENCH, 	"COMPÉTITION"
		), request.getLocale());

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PLAYOFF", 
		Locale.FRENCH, 	"ÉLIMINATOIRE"
		), request.getLocale());

LocalizedString sdrAdmin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "ADMINISTRATION", 
		Locale.FRENCH, 	"ADMINISTRATION"
		), request.getLocale());


LocalizedString strLive = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "LIVE", 
		Locale.FRENCH, 	"EN COURS"
		), request.getLocale());
%>

<div class="header">
	<img class="headerLogo" src="../images/crc.jpg" />
	
	<h1 class="headerAdmin"><%= sdrAdmin %></h1>
	
	<div class="headerRubban grayBackgroundColor">
		<a href="../game"><%= strLive %></a>
		<a href="competition"><%= strCompetition %></a>
		<a href="../schedule"><%= strSchedule %></a>
		<a href="playoff"><%= strPlayoff %></a>
	</div>
</div>