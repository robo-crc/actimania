<%@page import="com.backend.models.Skill"%>
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
ArrayList<School> cumulativeRanking = (ArrayList<School>)request.getAttribute("cumulativeRanking");
@SuppressWarnings("unchecked")
ArrayList<School> excludedSchools = (ArrayList<School>)request.getAttribute("excludedSchools");

Tournament tournament = (Tournament)request.getAttribute("tournament");
SkillsCompetition skillsCompetition = (SkillsCompetition)request.getAttribute("skillsCompetition");

Locale currentLocale = request.getLocale();

LocalizedString strRankingTitle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary heats ranking", 
		Locale.FRENCH, 	"Classement de la ronde pr�liminaire"
		), currentLocale);

LocalizedString strRankingLong = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PRELIMINARY HEATS RANKING", 
		Locale.FRENCH, 	"CLASSEMENT DE LA RONDE PR�LIMINAIRE"
		), currentLocale);

LocalizedString strPosition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "POSITION", 
		Locale.FRENCH, 	"POSITION"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHOOL", 
		Locale.FRENCH, 	"�COLE"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCORE", 
		Locale.FRENCH, 	"POINTAGE"
		), currentLocale);

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "COMPETITION", 
		Locale.FRENCH, 	"COMP�TITION"
		), currentLocale);

LocalizedString strNoShow = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "*No show for playoff", 
		Locale.FRENCH, 	"*Ne se pr�sentera pas aux �liminatoires"
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
	<th><%= strPosition %></th>
	<th><%= strSchool %></th>
	<th><%= strScore %></th>
	<th><%= strCompetition %></th>
<%
	for(Skill skill : skillsCompetition.skills)
	{
		out.write("<th>" + skill.displayNameUpperCompact + "</th>");
	}
%>
</tr>
<tr class="whiteBackgroundColor"/>
<%

int nbExcludedSchools = 0;
for( int position = 0; position < cumulativeRanking.size(); position++ )
{
	School school = cumulativeRanking.get(position);
	boolean isExcluded = excludedSchools.contains(school);
	String excludedStyle = "";
	String posToDisplay = Integer.toString(position + 1 - nbExcludedSchools);
	
	if(isExcluded)
	{
		excludedStyle = "excludedSchool";
		posToDisplay = "* " + 0;
	}
%>
	<tr class="<%= excludedStyle %>">
		<td class="rankAlignLeft" sorttable_customkey="<%= position %>"><%= posToDisplay %></td>
		<td class="rankAlignLeft">
			<div class="scheduleSchoolDiv clear">
				<div class="scheduleSchoolInner">
					<img class="scheduleSchoolLogo" src="images/schools/32x32/<%= school.getCompactName() %>.png" />
				</div>
				<a class="scheduleSchoolText <%= excludedStyle %>" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
			</div>
		</td>
		<td class="center" sorttable_customkey="<%= position %>"><%= String.format("%.2f", tournament.getPreliminaryScore(school, skillsCompetition) * 100) %> %</td>
		<td class="center"><%=tournament.getRoundScore(school, GameTypeEnum.PRELIMINARY)%></td>

<%
	for(Skill skill : skillsCompetition.skills)
	{
		out.write("<td class=\"center\">" + skill.getSchoolScore(school).getDisplay() + "</td>");
	}
%>
</tr>
<%
	if(isExcluded)
	{
		nbExcludedSchools++;
	}
}
%>
</table>

<%
if(nbExcludedSchools > 0)
{
%>
	<br/>
	<div class="excludedSchool center"><%= strNoShow %></div>
<%
}
%>
<%@include file="footer.jsp" %>
</body>
</html>