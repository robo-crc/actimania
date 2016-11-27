<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>

<%
LocalizedString strFred = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Programmed by Fr�d�ric Joanis", 
		Locale.FRENCH, 	"Programm� par Fr�d�ric Joanis"
		), request.getLocale());

LocalizedString strLiveGameFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Live game", 
		Locale.FRENCH, 	"Partie en cours"
		), request.getLocale());

LocalizedString strRankingFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Preliminary heats ranking", 
		Locale.FRENCH, 	"Classement de la ronde pr�liminaire"
		), request.getLocale());

LocalizedString strScheduleFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), request.getLocale());

LocalizedString strFacebookFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Facebook", 
		Locale.FRENCH, 	"Facebook"
		), request.getLocale());

LocalizedString strInstagramFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Instagram", 
		Locale.FRENCH, 	"Instagram"
		), request.getLocale());

LocalizedString strCRCFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "CRC", 
		Locale.FRENCH, 	"CRC"
		), request.getLocale());
%>

<div class="footer grayBackgroundColor">
	<div class="footerLinks">
		<a href="live"><%= strLiveGameFooter %></a><br/>
		<a href="schedule"><%= strScheduleFooter %></a><br/>
		<a href="ranking"><%= strRankingFooter %></a><br/>
		<a href="https://www.facebook.com/roboCRC"><%= strFacebookFooter %></a><br/>
		<a href="http://www.instagram.com/robocrc"><%= strInstagramFooter %></a><br/>
		<a href="http://www.robo-crc.ca/"><%= strCRCFooter %></a><br/>
	</div>
	<div class="footerFred">
		<a href="https://ca.linkedin.com/pub/fr%C3%A9d%C3%A9ric-joanis/3/b06/764" target="_blank">
			<img class="footerLinkedin" src="images/linkedinWhite.svg" />
		</a>
		<a href="https://github.com/fredericjoanis/actimania" target="_blank">
			<img class="footerGithub" src="images/githubWhite.png" />
		</a>
		<br/>
		<a href="https://ca.linkedin.com/pub/fr%C3%A9d%C3%A9ric-joanis/3/b06/764" target="_blank"><%= strFred %></a>
	</div>
</div>
