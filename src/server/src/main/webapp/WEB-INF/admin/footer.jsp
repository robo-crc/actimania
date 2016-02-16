<%@page import="java.util.Locale"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>

<%
LocalizedString strFred = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Programmed by Frédéric Joanis", 
		Locale.FRENCH, 	"Programmé par Frédéric Joanis"
		), request.getLocale());

LocalizedString strCompetitionFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Competition", 
		Locale.FRENCH, 	"Compétition"
		), request.getLocale());

LocalizedString strScheduleFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), request.getLocale());

LocalizedString strPlayoffFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Playoff", 
		Locale.FRENCH, 	"Éliminatoires"
		), request.getLocale());

LocalizedString strScoolsFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
		), request.getLocale());

LocalizedString strUsersFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Users", 
		Locale.FRENCH, 	"Utilisateurs"
		), request.getLocale());

LocalizedString strLogoutFooter = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), request.getLocale());
%>

<div class="footer grayBackgroundColor">
	<div class="footerLinks">
		<a href="competition"><%= strCompetitionFooter %></a><br/>
		<a href="../schedule"><%= strScheduleFooter %></a><br/>
		<a href="playoff"><%= strPlayoffFooter %></a><br/>
		<a href="schools"><%= strScoolsFooter %></a><br/>
		<a href="users"><%= strUsersFooter %></a><br/>
		<a href="../logout"><%= strLogoutFooter %></a><br/>
	</div>
	<div class="footerFred">
		<a href="https://ca.linkedin.com/pub/fr%C3%A9d%C3%A9ric-joanis/3/b06/764">
			<img class="footerLinkedin" src="../images/linkedinWhite.svg" />
		</a>
		<a href="https://github.com/fredericjoanis/actimania">
			<img class="footerGithub" src="../images/githubWhite.png" />
		</a>
		<br/>
		<a href="https://ca.linkedin.com/pub/fr%C3%A9d%C3%A9ric-joanis/3/b06/764"><%= strFred %></a>
	</div>
</div>
