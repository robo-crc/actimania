<%@page import="com.backend.models.enums.GameTypeEnum"%>
<%@page import="com.backend.models.Tournament"%>
<%@page import="java.io.IOException"%>
<%@page import="com.framework.helpers.Helpers"%>
<%@page import="com.backend.models.SkillsCompetition"%>
<%@page import="com.backend.models.Competition"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.Set"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>

<% 
Tournament tournament 			= (Tournament) request.getAttribute("tournament");
Competition competition 		= (Competition) request.getAttribute("competition");
@SuppressWarnings("unchecked")
ArrayList<School> schoolsRanked 	= (ArrayList<School>) request.getAttribute("schoolsRanked");


Locale currentLocale = request.getLocale();

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "ACTIMANIA FINAL RESULTS", 
		Locale.FRENCH, 	"RÉSULTATS FINAUX D'ACTIMANIA"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHOOL", 
		Locale.FRENCH, 	"ÉCOLE"
		), currentLocale);

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "RANK", 
		Locale.FRENCH, 	"POSITION"
		), currentLocale);

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PLAYOFF", 
		Locale.FRENCH, 	"ÉLIMINATOIRES"
		), currentLocale);

LocalizedString strKiosk = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "KIOSK", 
		Locale.FRENCH, 	"KIOSQUE"
		), currentLocale);

LocalizedString strProgramming = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PROGRAMMING", 
		Locale.FRENCH, 	"PROGRAMMATION"
		), currentLocale);

LocalizedString strRobotConstruction = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "ROBOT<br/>CONSTRUCTION", 
		Locale.FRENCH, 	"CONSTRUCTION<br/>DU ROBOT"
		), currentLocale);

LocalizedString strRobotDesign = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "ROBOT<br/>DESIGN", 
		Locale.FRENCH, 	"DESIGN DU<br/>ROBOT"
		), currentLocale);

LocalizedString strSportsmanship = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SPORTSMANSHIP", 
		Locale.FRENCH, 	"ESPRIT SPORTIF"
		), currentLocale);

LocalizedString strVideo = new LocalizedString(ImmutableMap.of( 
		Locale.ENGLISH, "VIDEO", 
		Locale.FRENCH, 	"VIDÉO"
		), currentLocale);

LocalizedString strWebsiteDesign = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "WEBSITE<br/>DESIGN", 
		Locale.FRENCH, 	"CONCEPTION DU<br/>SITE WEB"
		), currentLocale);

LocalizedString strWebsiteJournalism = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "WEBSITE<br/>JOURNALISM", 
		Locale.FRENCH, 	"JOURNALISME DU<br/>SITE WEB"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<title><%= strOverall %></title>
<link rel="stylesheet" type="text/css" href="css/template.css">
<script src="jquery/sorttable.js"></script>
</head>

<body>
<%@include file="header.jsp" %>
	<h1 class="grayColor"><%= strOverall %></h1>
	<div class="bar grayBackgroundColor"></div>
	
	<table class="sortable rank">
		<tr>
			<th><%= strRank %></th>
			<th><%= strSchool %></th>
			<th><%= strPlayoff %></th>
			<th><%= strKiosk %></th>
			<th><%= strProgramming %></th>
			<th><%= strRobotConstruction %></th>
			<th><%= strRobotDesign %></th>
			<th><%= strSportsmanship %></th>
			<th><%= strVideo %></th>
			<th><%= strWebsiteDesign %></th>
			<th><%= strWebsiteJournalism %></th>
		</tr>
		<tr class="whiteBackgroundColor"/>
		
		<%
		ArrayList<School> heatRanking = tournament.getPlayoffRanking();
		for(int i = 0; i < schoolsRanked.size(); i++)
		{
			School school = schoolsRanked.get(i);
		%>
			<tr>
				<td class="rankAlignLeft"><%= String.valueOf(i + 1) %></td>
				<td class="rankAlignLeft">
					<div class="scheduleSchoolDiv clear">
						<div class="scheduleSchoolInner">
							<img class="scheduleSchoolLogo" src="images/schools/32x32/<%= school.getCompactName() %>.png" />
						</div>
						<a class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
					</div>
				</td>
				<td class="center"><%= heatRanking.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.kiosk.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.programming.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.robotConstruction.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.robotDesign.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.sportsmanship.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.video.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.websiteDesign.indexOf(school) + 1 %></td>
				<td class="center"><%= competition.websiteJournalism.indexOf(school) + 1 %></td>
			</tr>
		<%
		}
		%>
	</table>
<%@include file="footer.jsp" %>
</body>
</html>