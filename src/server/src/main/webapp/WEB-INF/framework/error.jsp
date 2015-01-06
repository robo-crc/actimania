<%@page import="java.util.ArrayList"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
Locale currentLocale = request.getLocale();

LocalizedString strError = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Error!", 
		Locale.FRENCH, 	"Erreur!"
		), currentLocale);

@SuppressWarnings("unchecked")
ArrayList<LocalizedString> errorList = (ArrayList<LocalizedString>)request.getAttribute("errorList");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strError %></title>
</head>
<body>
<% for(LocalizedString error : errorList) 
{
%>
	<%= error %><br/>
<%
}
%>

</body>
</html>