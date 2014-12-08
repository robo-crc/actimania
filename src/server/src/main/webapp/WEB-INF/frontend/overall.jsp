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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
Tournament tournament 			= (Tournament) request.getAttribute("tournament");
Competition competition 		= (Competition) request.getAttribute("competition");
@SuppressWarnings("unchecked")
ArrayList<School> schoolsRanked 	= (ArrayList<School>) request.getAttribute("schoolsRanked");


Locale currentLocale = request.getLocale();

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Actimania final results", 
		Locale.FRENCH, 	"Résultats finaux d'Actimania"
		), currentLocale);

LocalizedString strSchoolName = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Rank", 
		Locale.FRENCH, 	"Position"
		), currentLocale);

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Playoff", 
		Locale.FRENCH, 	"Éliminatoires"
		), currentLocale);

LocalizedString strKiosk = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Kiosk", 
		Locale.FRENCH, 	"Kiosque"
		), currentLocale);

LocalizedString strProgramming = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Programming", 
		Locale.FRENCH, 	"Programmation"
		), currentLocale);

LocalizedString strRobotConstruction = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Robot construction", 
		Locale.FRENCH, 	"Construction du robot"
		), currentLocale);

LocalizedString strRobotDesign = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Robot design", 
		Locale.FRENCH, 	"Design du robot"
		), currentLocale);

LocalizedString strSportsmanship = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Sportsmanship", 
		Locale.FRENCH, 	"Esprit sportif"
		), currentLocale);

LocalizedString strVideo = new LocalizedString(ImmutableMap.of( 
		Locale.ENGLISH, "Video", 
		Locale.FRENCH, 	"Vidéo"
		), currentLocale);

LocalizedString strWebsiteDesign = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Website Design", 
		Locale.FRENCH, 	"Conception du site web"
		), currentLocale);

LocalizedString strWebsiteJournalism = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Website journalism", 
		Locale.FRENCH, 	"Journalisme du site web"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strOverall %></title>
<link rel="shortcut icon" href="images/favicon.ico" />
</head>

<body>
	<h1><%= strOverall %></h1>
	
	<table>
		<tr>
			<td><%= strRank %></td>
			<td><%= strSchool %></td>
			<td><%= strPlayoff %></td>
			<td><%= strKiosk %></td>
			<td><%= strProgramming %></td>
			<td><%= strRobotConstruction %></td>
			<td><%= strRobotDesign %></td>
			<td><%= strSportsmanship %></td>
			<td><%= strVideo %></td>
			<td><%= strWebsiteDesign %></td>
			<td><%= strWebsiteJournalism %></td>
			<td><b><%= strOverall %></b></td>
		</tr>
		<%
		for(int i = 0; i < schoolsRanked.size(); i++)
		{
			School school = schoolsRanked.get(i);
		%>
			<tr>
				<td><%= String.valueOf(i + 1) %></td>
				<td><%= school.name %></td>
				<td><%= Competition.getAspectPoints(tournament.getHeatRanking(GameTypeEnum.PLAYOFF), school) * 2 %></td>
				<td><%= Competition.getAspectPoints(competition.kiosk, school) %></td>
				<td><%= Competition.getAspectPoints(competition.programming, school) %></td>
				<td><%= Competition.getAspectPoints(competition.robotConstruction, school) %></td>
				<td><%= Competition.getAspectPoints(competition.robotDesign, school) %></td>
				<td><%= Competition.getAspectPoints(competition.sportsmanship, school) %></td>
				<td><%= Competition.getAspectPoints(competition.video, school) %></td>
				<td><%= Competition.getAspectPoints(competition.websiteDesign, school) %></td>
				<td><%= Competition.getAspectPoints(competition.websiteJournalism, school) %></td>
				<td><b><%= competition.getSchoolScore(tournament.getHeatRanking(GameTypeEnum.PLAYOFF), school) %></b></td>
			</tr>
		<%
		}
		%>
	</table>
</body>
</html>