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
Playoff playoff = (Playoff) request.getAttribute("playoff");
@SuppressWarnings("unchecked")
ArrayList<School> excludedSchools = (ArrayList<School>) request.getAttribute("excludedSchools");

GameTypeEnum currentRound = (GameTypeEnum) request.getAttribute("currentRound");
GameTypeEnum nextRound = (GameTypeEnum) request.getAttribute("nextRound");

Boolean isCurrentRoundStarted = (Boolean) request.getAttribute("isCurrentRoundStarted");

Locale currentLocale = request.getLocale();

LocalizedString strPlayoff = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School name : ", 
		Locale.FRENCH, 	"Nom de l'école : "
		), currentLocale);

LocalizedString strOverall = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Overall", 
		Locale.FRENCH, 	"Classement final"
		), currentLocale);

LocalizedString strLogout = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Logout", 
		Locale.FRENCH, 	"Déconnexion"
		), currentLocale);

LocalizedString strSave = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Save", 
		Locale.FRENCH, 	"Sauvegarder"
		), currentLocale);

LocalizedString strUsers = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Users", 
		Locale.FRENCH, 	"Utilisateurs"
		), currentLocale);

LocalizedString strSchedule = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schedule", 
		Locale.FRENCH, 	"Horaire"
		), currentLocale);

LocalizedString strSchool = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "School", 
		Locale.FRENCH, 	"École"
		), currentLocale);

LocalizedString strSchools = new LocalizedString(ImmutableMap.of( 	
		Locale.ENGLISH, "Schools", 
		Locale.FRENCH, 	"Écoles"
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
		Locale.ENGLISH, "This action will delete the current round and there's already at least a match played. Are you sure you want to do this?", 
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= strPlayoff %></title>
<link rel="shortcut icon" href="images/favicon.ico" />
</head>
<body>
	<h1><%= strPlayoff %></h1>

	<h2><%= strExcludedSchools %></h2>
	<%
	for(School school : excludedSchools)
	{
		out.println(school);
	}
	%>
	
	<form method="post">
		<input type="hidden" name="action" value="addExcludedSchool" />
		<h2><%= strAddExcludedSchool %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	
		ArrayList<School> schoolsToAdd = playoff.getRemainingSchools(tournament.schools);
		
		for(School school : schoolsToAdd)
		{ %>
			<option value="<%= school._id %>"> <%= school.name %></option>
	 <% } %>
		</select>
		<input type="submit" value="<%= strAdd %>" />
	</form>
	
	<% if(playoff.excludedSchools.size() > 0)
	{
		%>
	<form method="post">
		<input type="hidden" name="action" value="removeExcludedSchool" />
		<h2><%= strRemoveExcludedSchool %></h2>
		
		<%= strSchool %>
		<select name="school">
	<% 	
		
		for(School school : playoff.excludedSchools)
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
		<input type="hidden" name="action" value="generateNextRound" />
		<input type="hidden" name="nextRound" value="<%= nextRound %>" />
		<input type="hidden" name="currentRound" value="<%= currentRound %>" />
		<input type="submit" value="<%= strDeleteCurrentRound %>" />
	</form>
	
	<br/>
	<a href="../overall"><%= strOverall %></a>
	<br/>
	<a href="../schedule"><%= strSchedule %></a>
	<br/>
	<a href="schools"><%= strSchools %></a>
	<br/>
	<a href="users"><%= strUsers %></a>
	<br/>
	<a href="../logout"><%= strLogout %></a>
</body>