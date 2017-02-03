<%@page import="com.framework.helpers.Helpers"%>
<%@page import="com.backend.models.SchoolExtra"%>
<%@page import="com.backend.models.enums.Division"%>
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
ArrayList<SchoolExtra> schools = (ArrayList<SchoolExtra>) request.getAttribute("schools");

Locale currentLocale = request.getLocale();

LocalizedString strAddSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add", 
		Locale.FRENCH, 	"Ajouter"
		), currentLocale);

LocalizedString strEditSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Edit", 
		Locale.FRENCH, 	"Éditer"
		), currentLocale);

LocalizedString strDeleteSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Delete", 
		Locale.FRENCH, 	"Supprimer"
		), currentLocale);

LocalizedString strSchoolName = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strDivision = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Division", 
		Locale.FRENCH, 	"Division"
		), currentLocale);

LocalizedString strDesignEvalTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Design evaluation time", 
		Locale.FRENCH, 	"Heure évaluation du design"
		), currentLocale);

LocalizedString strConstructionEvalTime = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Construction evaluation time", 
		Locale.FRENCH, 	"Heure évaluation construction"
		), currentLocale);


LocalizedString strWebsiteURL = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Website URL", 
		Locale.FRENCH, 	"URL du site web"
		), currentLocale);


LocalizedString strJournalURL = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Journal URL", 
		Locale.FRENCH, 	"URL du journal"
		), currentLocale);


LocalizedString strVideoURL = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Video URL", 
		Locale.FRENCH, 	"URL du vidéo"
		), currentLocale);

%>

<!DOCTYPE html>
<html>
<head>
<title><%= strAddSchool %></title>
<%@include file="head.jsp" %>
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
	<h1><%= strAddSchool %></h1>
	<form method="post">
		<input type="hidden" name="action" value="create" />
		<span><%= strSchoolName %><input type="text" name="schoolName" /> </span>
		<br/>
		<%= strDivision %>
		<select name="division">
		<% 	for(Division division : Division.values())
			{ %>
				<option value="<%= division.name() %>" <% if( division.equals(Division.ONE) ) { %> selected="selected" <% } %>> 
					<%= division.name() %>
				</option>
		 <% } %>
		</select>
		
		<div><%= strDesignEvalTime %></div>
		<input type="datetime-local" name="designEvalTime" /> 
		<div><%= strConstructionEvalTime %></div>
		<input type="datetime-local" name="constructionEvalTime" /> 
		<br/>
		<span><%= strWebsiteURL %><input class="schoolInputText" type="text" name="websiteURL" value="" /> </span>
		<br/>
		<span><%= strJournalURL %><input class="schoolInputText" type="text" name="journalURL" value="" /> </span>
		<br/>
		<span><%= strVideoURL %><input class="schoolInputText" type="text" name="videoURL" value="" /> </span>
		<br/>
		<input type="submit" value="<%= strAddSchool %>" />
	</form>
	
	<h1><%= strEditSchool %></h1>
	
	<% 
	for( SchoolExtra school : schools)
	{
	%>
		<form method="post">
			<input type="hidden" name="action" value="edit" />
			<input type="hidden" name="id" value="<%= school._id %>" />
			<span><%= strSchoolName %><input type="text" name="schoolName" value="<%= school.name %>" /> </span>
			<br/>
			<%= strDivision %>
			<select name="division">
			<% 	for(Division division : Division.values())
				{ %>
					<option value="<%= division.name() %>" <% if( division.equals(school.division) ) { %> selected="selected" <% } %>> 
						<%= division.name() %>
					</option>
			 <% } %>
			</select>
			<div><%= strDesignEvalTime %></div>
			<input type="datetime-local" name="designEvalTime" value="<%= Helpers.html5DateTimePicker.print(school.designEvalTime) %>"/> 
			<div><%= strConstructionEvalTime %></div>
			<input type="datetime-local" name="constructionEvalTime" value="<%= Helpers.html5DateTimePicker.print(school.constructionEvalTime) %>" />
			<br/>
			<span><%= strWebsiteURL %><input class="schoolInputText" type="text" name="websiteURL" value="<%= school.websiteURL %>" /> </span>
			<br/>
			<span><%= strJournalURL %><input class="schoolInputText" type="text" name="journalURL" value="<%= school.journalURL %>" /> </span>
			<br/>
			<span><%= strVideoURL %><input class="schoolInputText" type="text" name="videoURL" value="<%= school.videoURL %>" /> </span>
			<br/>
			<input type="submit" value="<%= strEditSchool %>" />
		</form>
		
		<form method="post">
			<input type="hidden" name="action" value="delete" />
			<input type="hidden" name="id" value="<%= school._id %>" />
			<input type="submit" value="<%= strDeleteSchool %>" />
		</form>
		<br/>
	<% 
	}
	%>
<%@include file="footer.jsp" %>
</body>
</html>