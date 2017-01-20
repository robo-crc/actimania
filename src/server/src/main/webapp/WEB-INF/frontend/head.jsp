<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="shortcut icon" href="images/favicon.ico" />
<!-- Activate this when online. -->

<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<!-- 
<link href='css/open-sans.css' rel='stylesheet' type='text/css'>
-->

<% if(request.getParameter("PrinterFriendly") == null) 
{ %> 
<link rel="stylesheet" type="text/css" href="css/global.css"/>
<% } else { %>
<link rel="stylesheet" type="text/css" href="css/globalPrint.css"/>
<% } %>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-58398665-1', 'auto');
  ga('send', 'pageview');
</script>

<script>
function GoToURLWithParam(param)
{
	_url = location.href;
	_url += (_url.split('?')[1] ? '&' : '?') + param;
	
	window.open(_url, '_blank');
	return false;
}
</script>