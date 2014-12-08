<%@page import="com.framework.helpers.Helpers"%>
<%@page import="org.joda.time.format.PeriodFormatterBuilder"%>
<%@page import="org.joda.time.format.PeriodFormatter"%>
<%@page import="com.backend.models.SchoolDuration"%>
<%@page import="com.backend.models.SchoolInteger"%>
<%@page import="com.backend.models.SkillsCompetition"%>
<%@page import="com.backend.models.enums.GameTypeEnum"%>
<%@page import="com.backend.models.Tournament"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
@SuppressWarnings("unchecked")
ArrayList<School> heatRanking = (ArrayList<School>)request.getAttribute("heatRanking");

@SuppressWarnings("unchecked")
ArrayList<School> cumulativeRanking = (ArrayList<School>)request.getAttribute("cumulativeRanking");

Tournament tournament = (Tournament)request.getAttribute("tournament");
SkillsCompetition skillsCompetition = (SkillsCompetition)request.getAttribute("skillsCompetition");

Locale currentLocale = request.getLocale();

LocalizedString strRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Ranking", 
		Locale.FRENCH, 	"Classement"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strPosition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Position", 
		Locale.FRENCH, 	"Position"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Score", 
		Locale.FRENCH, 	"Pointage"
		), currentLocale);

LocalizedString strPreliminaryScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary Score", 
		Locale.FRENCH, 	"Pointage préliminaire"
		), currentLocale);

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
		), currentLocale);

LocalizedString strCumulativeScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Cumulative Score", 
		Locale.FRENCH, 	"Pointage cumulatif"
		), currentLocale);

LocalizedString strPickupRace = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Pick-up race", 
		Locale.FRENCH, 	"Ramassage de vitesse"
		), currentLocale);

LocalizedString strTwoTargetHits = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Two target hits", 
		Locale.FRENCH, 	"Toucher deux cibles"
		), currentLocale);

LocalizedString strTwoActuatorChanged = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Two actuator changed", 
		Locale.FRENCH, 	"Changer deux actuateurs"
		), currentLocale);

LocalizedString strLiveGame = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live game", 
		Locale.FRENCH, 	"Partie en cours"
		), currentLocale);

LocalizedString strCount = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Count", 
		Locale.FRENCH, 	"Quantité"
		), currentLocale);

LocalizedString strTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Time", 
		Locale.FRENCH, 	"Temps"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="images/favicon.ico" />
<title><%= strRanking %></title>
</head>
<body>

<h1><%= strPreliminaryScore %></h1>
<table>
<tr>
<td><%= strPosition %></td><td><%= strSchool %></td><td><%= strCompetition %></td><td><%= strScore %></td><td><%= strPickupRace %></td><td><%= strCount %></td><td><%= strTwoTargetHits %></td><td><%= strTime %></td><td><%= strTwoActuatorChanged %></td><td><%= strTime %></td>
</tr>
<%

for( int position = 0; position < cumulativeRanking.size(); position++ )
{
	School school = cumulativeRanking.get(position);
%>
	<tr>
		<td><%= position + 1 %></td>
		<td><a href="school?schoolId=<%= school._id %>"><%= school.name %></a></td>
		
		<td><%= String.valueOf(heatRanking.indexOf(school) + 1) %></td>
		<td><%= tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) %></td>
		
		<td><%= String.valueOf(skillsCompetition.getPickballsPosition(school) + 1)%></td>
		<td><%= skillsCompetition.getPickballs(school).integer %></td>
		
		<td><%= String.valueOf(skillsCompetition.getTwoTargetHitsPosition(school) + 1)%></td>
		<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %></td>
		
		<td><%= String.valueOf(skillsCompetition.getTwoActuatorChangedPosition(school) + 1) %></td>
		<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %></td>
	</tr>
<%
}
%>
</table>

<br/>

<a href="schedule"><%= strSchedule %></a><br/>
<a href="live"><%= strLiveGame %></a>

</body>
</html>