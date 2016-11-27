<%@page import="com.backend.models.enums.GameTypeEnum"%>
<%@page import="com.backend.models.Tournament"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.ArrayList"%>

<%
Locale currentLocale = request.getLocale();

LocalizedString strLive = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live game", 
		Locale.FRENCH, 	"Partie en cours"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head.jsp" %>
<link rel="stylesheet" type="text/css" href="css/live.css"/>
<title><%= strLive %></title>
<script src="jquery/iframeresizer/js/iframeResizer.min.js"></script>
<script>
	$(function() {
	  	  $('.gameFrame').iFrameResize({});
	});
</script>
<style>
.headerLive
{
	font-weight: bold !important;
}
</style>
</head>
<body>
<%@include file="header.jsp" %>
<iframe src="http://www.twitch.tv/crc_robotics/embed" class="twitchStream" frameborder="0" scrolling="no" height="378" width="620"></iframe>
<div class="clear"></div>
<%@include file="footer.jsp" %>
</body>
</html>