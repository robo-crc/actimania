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
ArrayList<ArrayList<SchoolInteger>> repechageRankedByGroup = (ArrayList<ArrayList<SchoolInteger>>) request.getAttribute("repechageRankedByGroup");
@SuppressWarnings("unchecked")
ArrayList<ArrayList<SchoolInteger>> quarterRankedByGroup = (ArrayList<ArrayList<SchoolInteger>>) request.getAttribute("quarterRankedByGroup");
@SuppressWarnings("unchecked")
ArrayList<ArrayList<SchoolInteger>> semiRankedByGroup = (ArrayList<ArrayList<SchoolInteger>>) request.getAttribute("semiRankedByGroup");
@SuppressWarnings("unchecked")
ArrayList<ArrayList<SchoolInteger>> finalRankedByGroup = (ArrayList<ArrayList<SchoolInteger>>) request.getAttribute("finalRankedByGroup");

boolean showHeader = ((Boolean) request.getAttribute("showHeader")).booleanValue();

Locale currentLocale = request.getLocale();

LocalizedString strPlayoffRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "PLAYOFF RANKING", 
		Locale.FRENCH, 	"CLASSEMENT DES ÉLIMINATOIRES"
		), currentLocale);

final LocalizedString strRank = new LocalizedString(ImmutableMap.of( 	
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

LocalizedString strRepechage = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "REPECHAGE", 
		Locale.FRENCH, 	"REPÊCHAGE"
		), currentLocale);

LocalizedString strQuarter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "QUARTER FINAL", 
		Locale.FRENCH, 	"QUART DE FINALE"
		), currentLocale);

LocalizedString strSemi = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "SEMI FINAL", 
		Locale.FRENCH, 	"DEMI FINALE"
		), currentLocale);

LocalizedString strFinal = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "FINAL", 
		Locale.FRENCH, 	"FINALE"
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
<% if(showHeader)
{
%>

<%@include file="header.jsp" %>
	<h1 class="grayColor"><%= strPlayoff %></h1>
	<div class="bar grayBackgroundColor"></div>
<%
}
%>	
	<% 
		outputRankedByGroup(finalRankedByGroup, currentLocale, strFinal, strRank, strSchool, strScore, strGroup, repechageRankedByGroup.size() + quarterRankedByGroup.size() + semiRankedByGroup.size(), out);
		outputRankedByGroup(semiRankedByGroup, currentLocale, strSemi, strRank, strSchool, strScore, strGroup, repechageRankedByGroup.size() + quarterRankedByGroup.size(), out);
		outputRankedByGroup(quarterRankedByGroup, currentLocale, strQuarter, strRank, strSchool, strScore, strGroup, repechageRankedByGroup.size(), out);
		outputRankedByGroup(repechageRankedByGroup, currentLocale, strRepechage, strRank, strSchool, strScore, strGroup, 0, out);
	%>

	<%!
	public void outputRankedByGroup(ArrayList<ArrayList<SchoolInteger>> schoolsRankedByGroup, 
			Locale currentLocale, 
			LocalizedString roundString, 
			LocalizedString strRank, 
			LocalizedString strSchool, 
			LocalizedString strScore, 
			LocalizedString strGroup, 
			int groupStart,
			JspWriter out) throws IOException
	{
		if(schoolsRankedByGroup == null || schoolsRankedByGroup.size() == 0)
		{
			return;
		}
		
		out.write("<h1 class=\"grayColor\">" + roundString.get(currentLocale) + "</h1>");
		
		for(int i = schoolsRankedByGroup.size() - 1; i >= 0; i--)
		{
			if(i != schoolsRankedByGroup.size() - 1)
			{
				out.write("<br/>");
			}
			out.write("<table class=\"rank\">");
			out.write("	<tr>");
			out.write("		<th class=\"whiteBackgroundColor\"></th>");
			out.write("		<th>" + strRank.get(currentLocale) + "</th>");
			out.write("		<th>" + strSchool.get(currentLocale) + "</th>");
			out.write("		<th>" + strScore.get(currentLocale) + "</th>");
			out.write("	</tr>");
			
			int j = 0;
			for(SchoolInteger school : schoolsRankedByGroup.get(i))
			{
				out.write("	<tr>");
				
				if(j == 0)
				{
					int nbRows = schoolsRankedByGroup.get(i).size();
					
					out.print("<td class=\"scheduleRoundTd roundDiv\" rowspan=\"" + nbRows + "\">");
					out.print("<div class=\"scheduleRound\">" + strGroup.get(currentLocale) + " " + Character.toString((char) (groupStart + i + 65)) + "</div></td>");
				}
				j++;
				
				out.write("	<td class=\"center\">" + j + "</td>");
				out.write("	<td class=\"rankAlignLeft\">");
				out.write("		<div class=\"scheduleSchoolDiv clear\">");
				out.write("		<div class=\"scheduleSchoolInner\">");
				out.write("			<img class=\"scheduleSchoolLogo\" src=\"images/schools/32x32/" + school.getCompactName() + ".png\" />");
				out.write("		</div>");
				out.write("		<a class=\"scheduleSchoolText\" href=\"school?schoolId=" + school._id  + "\">" + school.name + "</a>");
				out.write("	</div>");
				out.write("	</td>");
				out.write("	<td class=\"center\">" + school.integer + "</td>");
				out.write("</tr>");
			}
			out.write("</table>");
		}
	}
	%>

<script type="text/javascript" src="jquery/iframeresizer/js/iframeResizer.contentWindow.min.js"></script>
<% if(showHeader)
{
%>
<%@include file="footer.jsp" %>
<%
}
%>
</body>
</html>