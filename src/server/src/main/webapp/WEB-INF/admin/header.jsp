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


LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHOOLS", 
		Locale.FRENCH, 	"ÉCOLES"
		), request.getLocale());

LocalizedString strUsers = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "USERS", 
		Locale.FRENCH, 	"UTILISATEURS"
		), request.getLocale());

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "COMPETITION", 
		Locale.FRENCH, 	"COMPÉTITION"
		), request.getLocale());

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PLAYOFF", 
		Locale.FRENCH, 	"ÉLIMINATOIRE"
		), request.getLocale());

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "LOGOUT", 
		Locale.FRENCH, 	"DÉCONNEXION"
		), request.getLocale());

LocalizedString sdrAdmin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "ADMINISTRATION", 
		Locale.FRENCH, 	"ADMINISTRATION"
		), request.getLocale());
%>

<div class="header">
	<img class="headerLogo" src="../images/crc.jpg" />
	
	<h1 class="headerAdmin"><%= sdrAdmin %></h1>
	
	<div class="headerRubban grayBackgroundColor">
		<a href="competition"><%= strCompetition %></a>
		<a href="../schedule"><%= strSchedule %></a>
		<a href="../overall"><%= strOverall %></a>
		<a href="playoff"><%= strPlayoff %></a>
		<!-- <a href="schools"><%= strSchools %></a>  -->
		<a href="users"><%= strUsers %></a>
		<a href="../logout"><%= strLogout %></a>
	</div>
</div>