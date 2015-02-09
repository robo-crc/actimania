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
@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>) request.getAttribute("errorList");

@SuppressWarnings("unchecked")
ArrayList<School> schools = (ArrayList<School>) request.getAttribute("schools");
Competition competition = (Competition) request.getAttribute("competition");
SkillsCompetition skillsCompetition = (SkillsCompetition) request.getAttribute("skillsCompetition");
boolean isLivreRefreshOn = ((Boolean) request.getAttribute("isLivreRefreshOn"));

Locale currentLocale = request.getLocale();

LocalizedString strSchoolName = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strSave = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Save", 
		Locale.FRENCH, 	"Sauvegarder"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strCompetitionTitle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
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
		Locale.ENGLISH, "Two switches changed", 
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

LocalizedString strLiveRefreshOn = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Turn on live refresh", 
		Locale.FRENCH, 	"Activer le rafraichissement des pages automatiques"
		), currentLocale);

LocalizedString strLiveRefreshOff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Turn off live refresh", 
		Locale.FRENCH, 	"Désactiver le rafraichissement des pages automatiques"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<title><%= strCompetitionTitle %></title>
<%@include file="head.jsp" %>

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
<%@include file="header.jsp" %>
	<%
	for(LocalizedString error : errorList)
	{
		%>
		<span class="error"><%= error %></span><br/>
		<%
	}
	%>

	<h1 class="grayColor"><%= strSkillsCompetition %></h1>
	<div class="bar grayBackgroundColor"></div>

	<div class="center">
		<form method="post">
			<input type="hidden" name="action" value="toggleLiveRefresh" />
			
			<% 
			LocalizedString strToggle = strLiveRefreshOn;
			if(isLivreRefreshOn) 
			{
				strToggle = strLiveRefreshOff;
			}
			%>
			<input type="submit" value="<%= strToggle %>" />
		</form>
	</div>
	<br/><br/><br/>
		
	<form method="post">
		<input type="hidden" name="action" value="skillsCompetition" />
		
		<table>
		<tr><th><%= strSchool %></th><th><%= strPickupRace %></th><th><%= strTwoTargetHits %></th><th><%= strTwoActuatorChanged %></th></tr>
		
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
		
		<div class="center">
			<input type="submit" value="<%= strSave %>" />
		</div>
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
			<th><%= strRank %></th>
			<th><%= strKiosk %></th>
			<th><%= strProgramming %></th>
			<th><%= strRobotConstruction %></th>
			<th><%= strRobotDesign %></th>
		</tr>
		<tr>
			<td>
				<ul id="position" class="sortableUI">
				<%
				for(int i = 1; i <= schools.size(); i++)
				{
					out.write("<li class=\"positionLi\">" + i + "</li>\n");
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
			<th><%= strRank %></th>
			<th><%= strSportsmanship %></th>
			<th><%= strVideo %></th>
			<th><%= strWebsiteDesign %></th>
			<th><%= strWebsiteJournalism %></th>
		</tr>
		<tr>
			<td>
				<ul id="position" class="sortableUI">
				<%
				for(int i = 1; i <= schools.size(); i++)
				{
					out.write("<li class=\"positionLi\">" + i + "</li>\n");
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
</body>
</html>