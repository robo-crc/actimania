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
		Locale.ENGLISH, "Preliminary heats ranking", 
		Locale.FRENCH, 	"Classement de la ronde préliminaire"
		), currentLocale);

LocalizedString strRankingLong = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PRELIMINARY HEATS RANKING", 
		Locale.FRENCH, 	"CLASSEMENT DE LA RONDE PRÉLIMINAIRE"
		), currentLocale);

LocalizedString strPosition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "POSITION", 
		Locale.FRENCH, 	"POSITION"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHOOL", 
		Locale.FRENCH, 	"ÉCOLE"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCORE", 
		Locale.FRENCH, 	"POINTAGE"
		), currentLocale);

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "COMPETITION", 
		Locale.FRENCH, 	"COMPÉTITION"
		), currentLocale);

LocalizedString strPickupRace = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PICK-UP<br/>RACE", 
		Locale.FRENCH, 	"RAMASSAGE<br/>DE VITESSE"
		), currentLocale);

LocalizedString strTwoTargetHits = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "TWO TARGETS<br/>HIT", 
		Locale.FRENCH, 	"TOUCHER<br/>DEUX CIBLES"
		), currentLocale);

LocalizedString strTwoActuatorChanged = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "TWO SWITCHES<br/>CHANGED", 
		Locale.FRENCH, 	"CHANGER DEUX<br/>ACTUATEURS"
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
	font-weight: bold !important;
}
</style>
</head>
<body>
<%@include file="header.jsp" %>
<h1 class="grayColor"><%= strRankingLong %></h1>
<div class="bar grayBackgroundColor"></div>
<table class="sortable rank">
<tr>
<th><%= strPosition %></th><th><%= strSchool %></th><th><%= strCompetition %></th><th><%= strPickupRace %></th><th><%= strTwoTargetHits %></th><th><%= strTwoActuatorChanged %></th>
</tr>
<tr class="whiteBackgroundColor"/>
<%

for( int position = 0; position < cumulativeRanking.size(); position++ )
{
	School school = cumulativeRanking.get(position);
%>
	<tr>
		<td class="rankAlignLeft"><%= position + 1 %></td>
		<td class="rankAlignLeft">
			<div class="scheduleSchoolDiv clear">
				<div class="scheduleSchoolInner">
					<img class="scheduleSchoolLogo" src="images/schools/32x32/<%= school.getCompactName() %>.png" />
				</div>
				<a class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
			</div>
		</td>
		<td class="center"><%= tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) %></td>
		
		<td class="center"><%= skillsCompetition.getPickballs(school).integer %></td>
		<td class="center"><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %></td>
		<td class="center"><%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %></td>
	</tr>
<%
}
%>
</table>
<%@include file="footer.jsp" %>
</body>
</html>