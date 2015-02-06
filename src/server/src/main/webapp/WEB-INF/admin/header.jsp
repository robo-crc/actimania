<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Overall", 
		Locale.FRENCH, 	"Classement final"
		), request.getLocale());


LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), request.getLocale());


LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
		), request.getLocale());


LocalizedString strUsers = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Users", 
		Locale.FRENCH, 	"Utilisateurs"
		), request.getLocale());


LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
		), request.getLocale());

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Playoff", 
		Locale.FRENCH, 	"Éliminatoire"
		), request.getLocale());

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), request.getLocale());

LocalizedString sdrAdmin = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Administration", 
		Locale.FRENCH, 	"Administration"
		), request.getLocale());
%>

<div class="header">
	<img class="headerLogo" src="../images/crc.jpg" />
	
	<h1 class="headerAdmin"><%= sdrAdmin %></h1>
	
	<div class="headerRubban grayBackgroundColor">
		<a href="../overall"><%= strOverall %></a>
		<a href="competition"><%= strCompetition %></a>
		<a href="playoff"><%= strPlayoff %></a>
		<a href="../schedule"><%= strSchedule %></a>
		<a href="users"><%= strUsers %></a>
		<a href="../logout"><%= strLogout %></a>
	</div>
</div>