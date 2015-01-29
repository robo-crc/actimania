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

<%
@SuppressWarnings("unchecked")
ArrayList<School> heatRanking = (ArrayList<School>)request.getAttribute("heatRanking");

@SuppressWarnings("unchecked")
ArrayList<School> cumulativeRanking = (ArrayList<School>)request.getAttribute("cumulativeRanking");

Tournament tournament = (Tournament)request.getAttribute("tournament");
SkillsCompetition skillsCompetition = (SkillsCompetition)request.getAttribute("skillsCompetition");

Locale currentLocale = request.getLocale();

LocalizedString strRankingTitle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Ranking", 
		Locale.FRENCH, 	"Classement"
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

LocalizedString strCount = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Count", 
		Locale.FRENCH, 	"Quantité"
		), currentLocale);

LocalizedString strTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Time", 
		Locale.FRENCH, 	"Temps"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<title><%= strRankingTitle %></title>
<link rel="stylesheet" type="text/css" href="css/template.css">
<script src="jquery/sorttable.js"></script>
<style>
.headerRanking
{
	font-weight: bold;
}
</style>
</head>
<body>
<%@include file="header.jsp" %>
<h1><%= strPreliminaryScore %></h1>
<table class="sortable">
<tr>
<td><%= strPosition %></td><td><%= strSchool %></td><td><%= strCompetition %></td><td><%= strPickupRace %></td><td><%= strTwoTargetHits %></td><td><%= strTwoActuatorChanged %></td>
</tr>
<%

for( int position = 0; position < cumulativeRanking.size(); position++ )
{
	School school = cumulativeRanking.get(position);
%>
	<tr>
		<td><%= position + 1 %></td>
		<td><a href="school?schoolId=<%= school._id %>"><%= school.name %></a></td>
		
		<td><%= tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) %></td>
		
		<td><%= skillsCompetition.getPickballs(school).integer %></td>
		<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %></td>
		<td><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %></td>
	</tr>
<%
}
%>
</table>
<%@include file="footer.jsp" %>
</body>
</html>