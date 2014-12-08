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
@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>) request.getAttribute("errorList");

@SuppressWarnings("unchecked")
ArrayList<School> schools = (ArrayList<School>) request.getAttribute("schools");
Competition competition = (Competition) request.getAttribute("competition");
SkillsCompetition skillsCompetition = (SkillsCompetition) request.getAttribute("skillsCompetition");

Locale currentLocale = request.getLocale();

LocalizedString strSchoolName = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);

LocalizedString strSave = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Save", 
		Locale.FRENCH, 	"Sauvegarder"
		), currentLocale);

LocalizedString strUsers = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Users", 
		Locale.FRENCH, 	"Utilisateurs"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strSkillsCompetition = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Skills competition", 
		Locale.FRENCH, 	"Compétitions d'agilités"
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

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Rank", 
		Locale.FRENCH, 	"Position"
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

LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
		), currentLocale);

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Overall", 
		Locale.FRENCH, 	"Classement final"
		), currentLocale);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strCompetition %></title>
<link rel="shortcut icon" href="images/favicon.ico" />

<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="../jquery/jquery.js"></script>
<script src="../jquery/jquery.inputmask.min.js"></script>
<script src="../jquery/jquery.numeric.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

<style>
  .sortableUI, .sortable { list-style-type: none; margin: 0; padding: 0; }
  .sortableUI li, .sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; height: 40px; }
  .sortableUI li { margin-bottom: 5px; margin-top: 4px; }
  .sortable li { width : 175px; }
  .sortableUI li span, .sortable li span { position: absolute; margin-left: -1.3em; }
</style>

<script>
$(document).ready(function(){
	$( ".spinner" ).spinner();
	$( ".spinner" ).numeric();
	
	$(".chrono").inputmask("9:99.99");
});

$(function() {
    $( ".sortable" ).sortable({
	    		update: function (event, ui) 
	    		{
	    	        var data = $(this).sortable('serialize');
	    	        data += "&arrayId=" + this.id;
	    	        console.log(data);
	    	        // POST to server using $.post or $.ajax
	    	        $.ajax(
	    	        {
	    	            data: data,
	    	            type: 'POST',
	    	            url: 'competitionUpdate'
	    	        });
	    	    }});
    $( ".sortable" ).disableSelection();
  });
</script>
</head>
<body>
	<%
	for(LocalizedString error : errorList)
	{
		%>
		<span class="error"><%= error %></span><br/>
		<%
	}
	%>
	<h1><%= strSkillsCompetition %></h1>
	<form method="post">
		<input type="hidden" name="action" value="skillsCompetition" />
		
		<table>
		<tr><td><%= strSchool %></td><td><%= strPickupRace %></td><td><%= strTwoTargetHits %></td><td><%= strTwoActuatorChanged %></td></tr>
		
		<%
		for(School school : schools)
		{
		%>
			<tr>
				<td><%= school.name %></td>
				<td><input class="spinner" type="text" name="pickballs_<%= school._id %>" value="<%= skillsCompetition.getPickballs(school).integer %>" /></td>
				<td><input type="text" class="chrono" name="twoTargets_<%= school._id %>" value="<%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoTargetHits(school).duration.toPeriod()) %>" /></td>
				<td><input type="text" class="chrono" name="twoActuators_<%= school._id %>" value="<%= Helpers.stopwatchFormatter.print(skillsCompetition.getTwoActuatorChanged(school).duration.toPeriod()) %>" /></td>
			</tr>
			<%
		}
		%>
		</table>
		
		<input type="submit" value="<%= strSave %>" />
	</form>
	
	<h1><%= strCompetition %></h1>
	
	<%!
	public void outputListSchools(ArrayList<School> schools, JspWriter out) throws IOException
	{
		for(School school : schools)
		{
			out.write("<li id=\"id_" + school._id.toString() + "\"class=\"ui-state-default\"><span class=\"ui-icon ui-icon-arrowthick-2-n-s\"></span>" + school.name + "</li>\n");
		}
	}
	%>
	
	<table>
		<tr>
			<td><%= strRank %></td>
			<td><%= strKiosk %></td>
			<td><%= strProgramming %></td>
			<td><%= strRobotConstruction %></td>
			<td><%= strRobotDesign %></td>
		</tr>
		<tr>
			<td>
				<ul id="position" class="sortableUI">
				<%
				for(int i = 1; i <= schools.size(); i++)
				{
					out.write("<li>" + i + "</li>\n");
				}
				%>
				</ul>
			</td>
			<td>
				<ul id="kiosk" class="sortable">
					<% outputListSchools(competition.kiosk, out); %>
				</ul>
			</td>
			<td>
				<ul id="programming" class="sortable">
					<% outputListSchools(competition.programming, out); %>
				</ul>
			</td>
			<td>
				<ul id="robotConstruction" class="sortable">
					<% outputListSchools(competition.robotConstruction, out); %>
				</ul>
			</td>
			<td>
				<ul id="robotDesign" class="sortable">
					<% outputListSchools(competition.robotDesign, out); %>
				</ul>
			</td>
		</tr>
	</table>
	
		<table>
		<tr>
			<td><%= strRank %></td>
			<td><%= strSportsmanship %></td>
			<td><%= strVideo %></td>
			<td><%= strWebsiteDesign %></td>
			<td><%= strWebsiteJournalism %></td>
		</tr>
		<tr>
			<td>
				<ul id="position" class="sortableUI">
				<%
				for(int i = 1; i <= schools.size(); i++)
				{
					out.write("<li>" + i + "</li>\n");
				}
				%>
				</ul>
			</td>
			<td>
				<ul id="sportsmanship" class="sortable">
					<% outputListSchools(competition.sportsmanship, out); %>
				</ul>
			</td>
			<td>
				<ul id="video" class="sortable">
					<% outputListSchools(competition.video, out); %>
				</ul>
			</td>
			<td>
				<ul id="websiteDesign" class="sortable">
					<% outputListSchools(competition.websiteDesign, out); %>
				</ul>
			</td>
			<td>
				<ul id="websiteJournalism" class="sortable">
					<% outputListSchools(competition.websiteJournalism, out); %>
				</ul>
			</td>
		</tr>
	</table>
	
	<br/>
	<a href="../overall"><%= strOverall %></a>
	<br/>
	<a href="../schedule"><%= strSchedule %></a>
	<br/>
	<a href="schools"><%= strSchools %></a>
	<br/>
	<a href="users"><%= strUsers %></a>
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>
</html>