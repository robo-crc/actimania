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
ArrayList<School> schools = (ArrayList<School>) request.getAttribute("schools");

Locale currentLocale = request.getLocale();

LocalizedString strAddSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add", 
		Locale.FRENCH, 	"Ajouter"
		), currentLocale);

LocalizedString strEditSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Edit", 
		Locale.FRENCH, 	"�diter"
		), currentLocale);

LocalizedString strDeleteSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Delete", 
		Locale.FRENCH, 	"Supprimer"
		), currentLocale);

LocalizedString strSchoolName = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'�cole : "
		), currentLocale);

LocalizedString strDivision = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Division", 
		Locale.FRENCH, 	"Division"
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
		<%= strDivision %>
		<select name="division">
		<% 	for(Division division : Division.values())
			{ %>
				<option value="<%= division.name() %>" <% if( division.equals(Division.ONE) ) { %> selected="selected" <% } %>> 
					<%= division.name() %>
				</option>
		 <% } %>
		</select>
		<br/>
		<input type="submit" value="<%= strAddSchool %>" />
	</form>
	
	<h1><%= strEditSchool %></h1>
	
	<% 
	for( School school : schools)
	{
	%>
		<form method="post">
			<input type="hidden" name="action" value="edit" />
			<input type="hidden" name="id" value="<%= school._id %>" />
			<span><%= strSchoolName %><input type="text" name="schoolName" value="<%= school.name %>" /> </span>
			<%= strDivision %>
			<select name="division">
			<% 	for(Division division : Division.values())
				{ %>
					<option value="<%= division.name() %>" <% if( division.equals(school.division) ) { %> selected="selected" <% } %>> 
						<%= division.name() %>
					</option>
			 <% } %>
			</select>
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