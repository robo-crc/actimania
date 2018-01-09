<%@page import="com.backend.models.enums.GameTypeEnum"%>
<%@page import="com.backend.models.Playoff"%>
<%@page import="com.backend.models.School"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.common.collect.ImmutableMap"%>
<%@page import="com.framework.helpers.LocalizedString"%>
<%@page import="java.util.Locale"%>
<%@page import="com.backend.models.Tournament"%>

<% 
Tournament tournament = (Tournament) request.getAttribute("tournament");
@SuppressWarnings("unchecked")
ArrayList<School> excludedSchools = (ArrayList<School>) request.getAttribute("excludedSchools");

GameTypeEnum currentRound = (GameTypeEnum) request.getAttribute("currentRound");
GameTypeEnum nextRound = (GameTypeEnum) request.getAttribute("nextRound");

boolean isCurrentRoundStarted = ((Boolean) request.getAttribute("isCurrentRoundStarted")).booleanValue();

Locale currentLocale = request.getLocale();

LocalizedString strPlayoffTitle = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Playoff admin", 
		Locale.FRENCH, 	"Administration des éliminatoires"
		), currentLocale);

LocalizedString strSave = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Save", 
		Locale.FRENCH, 	"Sauvegarder"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strExcludedSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Excluded schools", 
		Locale.FRENCH, 	"Écoles exclues"
		), currentLocale);

LocalizedString strAddExcludedSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add excluded school", 
		Locale.FRENCH, 	"Ajouter une école exclue"
		), currentLocale);

LocalizedString strAdd = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Add", 
		Locale.FRENCH, 	"Ajouter"
		), currentLocale);

LocalizedString strRemoveExcludedSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Remove excluded school", 
		Locale.FRENCH, 	"Enlever une école exclue"
		), currentLocale);

LocalizedString strRemove = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Remove", 
		Locale.FRENCH, 	"Enlever"
		), currentLocale);

LocalizedString strGenerateNextRound = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Generate next round", 
		Locale.FRENCH, 	"Généré la ronde suivante"
		), currentLocale);

LocalizedString strDeleteCurrentRound = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "DANGER! Delete current round", 
		Locale.FRENCH, 	"DANGER! Supprimer la ronde actuelle"
		), currentLocale);

LocalizedString strDeleteConfirm = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "This action will delete the current round and there is already at least a match played. Are you sure you want to do this?", 
		Locale.FRENCH, 	"Cette action entrainera la suppresion de la ronde en cours. Au moins un match a déjà été joué! Êtes-vous sûr de vouloir supprimer la ronde actuelle?"
		), currentLocale);

LocalizedString strCurrentRound = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Current round", 
		Locale.FRENCH, 	"Ronde actuelle"
		), currentLocale);

LocalizedString strNextRound = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Next round", 
		Locale.FRENCH, 	"Prochaine ronde"
		), currentLocale);
%>

<!DOCTYPE html>
<html>
<head>
<title><%= strPlayoffTitle %></title>
<%@include file="head.jsp" %>
<script src="../jquery/iframeresizer/js/iframeResizer.min.js"></script>
<script>
	$(function() {
	  	  $('.frontendFrame').iFrameResize({});
	});
</script>
</head>
<body>
	<%@include file="header.jsp" %>

	<h1 class="grayColor"><%= strPlayoff %></h1>
	<div class="bar grayBackgroundColor"></div>
	
	<form method="post">
		<input type="hidden" name="action" value="addExcludedSchool" />
		<h2><%= strAddExcludedSchool %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	
		ArrayList<School> schoolsToAdd = Playoff.getRemainingSchools(tournament.schools, excludedSchools);
		
		for(School school : schoolsToAdd)
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<% if(excludedSchools.size() > 0)
	{
	%>
		<h2><%= strExcludedSchools %></h2>
		<%
		for(School school : excludedSchools)
		{
			out.println(school.name);
			out.print("<br/>");
		}
		%>
		
	<form method="post">
		<input type="hidden" name="action" value="removeExcludedSchool" />
		<h2><%= strRemoveExcludedSchool %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	
		
		for(School school : excludedSchools)
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<input type="submit" value="<%= strRemove %>" />
	</form>
	<%
	}
	%>
	
	<h2><%= strCurrentRound + " : " + currentRound %></h2>
	<h2><%= strNextRound + " : " + nextRound %></h2>
	<form method="post">
		<input type="hidden" name="action" value="generateNextRound" />
		<input type="hidden" name="nextRound" value="<%= nextRound %>" />
		<input type="hidden" name="currentRound" value="<%= currentRound %>" />
		<input type="submit" value="<%= strGenerateNextRound %>" />
	</form>
	<br/>
	<br/>
	<form method="post" <% if( isCurrentRoundStarted ) { %> onsubmit="return confirm('<%= strDeleteConfirm %>');"<% } %>>
		<input type="hidden" name="action" value="deleteCurrentRound" />
		<input type="hidden" name="nextRound" value="<%= nextRound %>" />
		<input type="hidden" name="currentRound" value="<%= currentRound %>" />
		<input type="submit" value="<%= strDeleteCurrentRound %>" />
	</form>
	
	<iframe src="../playoff?showHeader=false" class="frontendFrame" scrolling="no" frameborder="0" height="1500px" width="1200px"></iframe>
	
	<%@include file="footer.jsp" %>
</body>