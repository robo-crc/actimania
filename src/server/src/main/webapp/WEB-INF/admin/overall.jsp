<%@page import="com.backend.models.SchoolExtra"%>
<%@page import="com.backend.models.SchoolInteger"%>
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
@SuppressWarnings("unchecked")
ArrayList<SchoolExtra> schoolsExtra = (ArrayList<SchoolExtra>) request.getAttribute("schoolsExtra");


Locale currentLocale = request.getLocale();

LocalizedString strOverallTitle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "ACTIMANIA FINAL RESULTS", 
		Locale.FRENCH, 	"RÉSULTATS FINAUX D'ACTIMANIA"
		), currentLocale);


LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHOOL", 
		Locale.FRENCH, 	"ÉCOLE"
		), currentLocale);

LocalizedString strDivison = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "DIVISION", 
		Locale.FRENCH, 	"DIVISION"
		), currentLocale);

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "RANK", 
		Locale.FRENCH, 	"POSITION"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCORE", 
		Locale.FRENCH, 	"SCORE"
		), currentLocale);

LocalizedString strPlayoff1 = new LocalizedString(ImmutableMap.of( 	
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
<title><%= strOverallTitle %></title>
<link rel="stylesheet" type="text/css" href="../css/template.css">
<script src="../jquery/sorttable.js"></script>
<style>
.headerOverall
{
	font-weight: bold !important;
}
</style>
<script type="text/javascript" src="../jquery/iframeresizer/js/iframeResizer.contentWindow.min.js"></script>
</head>

<body>
	<h1 class="grayColor"><%= strOverallTitle %></h1>
	<div class="bar grayBackgroundColor"></div>
	
	<table class="sortable rank">
		<tr>
			<th><%= strRank %></th>
			<th><%= strSchool %></th>
			<th><%= strScore %></th>
			<th><%= strDivison %>
			<th><%= strPlayoff1 %></th>
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
		
		<%!
		int getDisplayRank(int schoolInt, int nbSchools)
		{
			if(schoolInt == 0)
			{
				return nbSchools;
			}
			else
			{
				return schoolInt;
			}
		}
		%>
		
		<%
		ArrayList<SchoolInteger> heatRanking = tournament.getPlayoffRanking();
		for(int i = 0; i < schoolsRanked.size(); i++)
		{
			School school = schoolsRanked.get(i);
			SchoolExtra schoolExtra = schoolsExtra.get(schoolsExtra.indexOf(school));
			
			int kioskInt = Competition.getSchoolInteger(competition.kiosk, school);
			int kioskPos = getDisplayRank(kioskInt, competition.kiosk.size());

			int programmingInt = Competition.getSchoolInteger(competition.programming, school);
			int programmingPos = getDisplayRank(programmingInt, competition.programming.size());
			
			int robotConstructionInt = Competition.getSchoolInteger(competition.robotConstruction, school);
			int robotConstructionPos = getDisplayRank(robotConstructionInt, competition.robotConstruction.size());
			
			int robotDesignInt = Competition.getSchoolInteger(competition.robotDesign, school);
			int robotDesignPos = getDisplayRank(robotDesignInt, competition.robotDesign.size());
			
			int sportsmanshipInt = Competition.getSchoolInteger(competition.sportsmanship, school);
			int sportsmanshipPos = getDisplayRank(sportsmanshipInt, competition.sportsmanship.size());
			
			int videoInt = Competition.getSchoolInteger(competition.video, school);
			int videoPos = getDisplayRank(videoInt, competition.video.size());
			
			int websiteDesignInt = Competition.getSchoolInteger(competition.websiteDesign, school);
			int websiteDesignPos = getDisplayRank(websiteDesignInt, competition.websiteDesign.size());
			
			int websiteJournalismInt = Competition.getSchoolInteger(competition.websiteJournalism, school);
			int websiteJournalismPos = getDisplayRank(websiteJournalismInt, competition.websiteJournalism.size());
			
			int playoffInt = heatRanking.indexOf(school) + 1;
			int playoffPos = getDisplayRank(playoffInt, heatRanking.size() + 1);
	
		%>
			<tr>
				<td class="rankAlignLeft"><%= String.valueOf(i + 1) %></td>
				<td class="rankAlignLeft">
					<div class="scheduleSchoolDiv clear">
						<div class="scheduleSchoolInner">
							<img class="scheduleSchoolLogo" src="../images/schools/32x32/<%= school.getCompactName() %>.png" />
						</div>
						<a class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
					</div>
				</td>
				<td class="center"><%= competition.getSchoolScore(heatRanking, school) %></td>
				<td class="center"><%= schoolExtra.division.toString() %>
				<td class="center" sorttable_customkey="<%= playoffPos %>"><%= playoffInt %></td>
				<td class="center" sorttable_customkey="<%= kioskPos %>"><%= kioskInt %></td>
				<td class="center" sorttable_customkey="<%= programmingPos %>"><%= programmingInt %></td>
				<td class="center" sorttable_customkey="<%= robotConstructionPos %>"><%= robotConstructionInt %></td>
				<td class="center" sorttable_customkey="<%= robotDesignPos %>"><%= robotDesignInt %></td>
				<td class="center" sorttable_customkey="<%= sportsmanshipPos %>"><%= sportsmanshipInt %></td>
				<td class="center" sorttable_customkey="<%= videoPos %>"><%= videoInt %></td>
				<td class="center" sorttable_customkey="<%= websiteDesignPos %>"><%= websiteDesignInt %></td>
				<td class="center" sorttable_customkey="<%= websiteJournalismPos %>"><%= websiteJournalismInt %></td>
			</tr>
		<%
		}
		%>
	</table>
	<br/>
	<%@include file="footer.jsp" %>
</body>
</html>