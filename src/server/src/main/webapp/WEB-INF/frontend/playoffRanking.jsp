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
ArrayList<ArrayList<SchoolInteger>> schoolsRankedByGroup = (ArrayList<ArrayList<SchoolInteger>>) request.getAttribute("schoolsRankedByGroup");

Locale currentLocale = request.getLocale();

LocalizedString strPlayoffRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PLAYOFF RANKING", 
		Locale.FRENCH, 	"CLASSEMENT DES ÉLIMINATOIRES"
		), currentLocale);

LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "RANK", 
		Locale.FRENCH, 	"POSITION"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCHOOL", 
		Locale.FRENCH, 	"ÉCOLE"
		), currentLocale);

LocalizedString strScore = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SCORE", 
		Locale.FRENCH, 	"CLASSEMENT"
		), currentLocale);

LocalizedString strGroup = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "GROUP", 
		Locale.FRENCH, 	"GROUPE"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<title><%= strPlayoffRanking %></title>
<link rel="stylesheet" type="text/css" href="css/template.css">
<script src="jquery/sorttable.js"></script>
<style>
.headerPlayoff
{
	font-weight: bold !important;
}
</style>
</head>

<body>
<%@include file="header.jsp" %>
	<h1 class="grayColor"><%= strPlayoff %></h1>
	<div class="bar grayBackgroundColor"></div>
	
	<%
	for(int i = schoolsRankedByGroup.size() - 1; i >= 0; i--)
	{
		%>
		<table>
			<tr>
				<th class="whiteBackgroundColor"></th>
				<th><%= strRank %></th>
				<th><%= strSchool %></th>
				<th><%= strScore %></th>
			</tr>
		<%
		int j = 0;
		for(SchoolInteger school : schoolsRankedByGroup.get(i))
		{
			%>
			<tr>
			<%
			if(j == 0)
			{
				int nbRows = schoolsRankedByGroup.get(i).size();
				
				out.print("<td class=\"scheduleRoundTd roundDiv\" rowspan=\"" + nbRows + "\">");
				out.print("<div class=\"scheduleRound\">" + strGroup.get(currentLocale) + " " + Character.toString((char) (i + 65)) + "</div></td>");
			}
			j++;
			%>
			<td><%= j %></td>
			<td>
				<div class="scheduleSchoolDiv clear">
					<div class="scheduleSchoolInner">
						<img class="scheduleSchoolLogo" src="images/schools/32x32/<%= school.getCompactName() %>.png" />
					</div>
					<a class="scheduleSchoolText" href="school?schoolId=<%= school._id %>"><%= school.name %></a>
				</div>
			</td>
			<td><%= school.integer %></td>
			</tr>
			<%
		}
		%>
		</table>
	<%
	}
	%>
	
<%@include file="footer.jsp" %>
</body>
</html>