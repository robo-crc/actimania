<%@page import="com.backend.models.enums.GameTypeEnum"%>
<%@page import="com.backend.models.Tournament"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
@SuppressWarnings("unchecked")
ArrayList<School> ranking = (ArrayList<School>)request.getAttribute("ranking");

Tournament tournament = (Tournament)request.getAttribute("tournament");

Locale currentLocale = request.getLocale();

LocalizedString strRanking = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Ranking", 
		Locale.FRENCH, 	"Classement"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
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
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strRanking %></title>
</head>
<body>

<table>
<tr>
<td><%= strPosition %></td><td><%= strSchool %></td><td><%= strScore %></td>
</tr>
<%
for( int position = 0; position < ranking.size(); position++ )
{
	School school = ranking.get(position);
	int score = tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY);
%>
	<tr>
		<td><%= position + 1 %></td>
		<td><a href="school?schoolId=<%= school._id %>"><%= school.name %></a></td>
		<td><%= score %></td>
	</tr>
<%
}
%>

</table>

<a href="schedule"><%= strSchedule %></a>

</body>
</html>