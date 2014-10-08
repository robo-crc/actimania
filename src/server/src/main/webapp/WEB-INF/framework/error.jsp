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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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