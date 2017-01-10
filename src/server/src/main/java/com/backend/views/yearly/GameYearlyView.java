package com.backend.views.yearly;

import java.util.Locale;

import com.backend.controllers.GameController;
import com.backend.models.Game;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.yearly.AreaPoints;
import com.backend.models.yearly.Area;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;

public class GameYearlyView 
{
	// Called by the view
	public static String getScripts()
	{
		StringBuilder strBuilder = new StringBuilder();		
		
		strBuilder.append("$(document).ready(function(){");
		strBuilder.append("	$( '.spinner' ).spinner({classes}));");
		strBuilder.append("	$( '.spinner' ).numeric();");
		strBuilder.append("});");

		return strBuilder.toString();
	}
	
	// Called by the view
	public static String getHtmlForGame(Game game, Locale currentLocale)
	{
		LocalizedString strScoreboardUpdate = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Update score board", 
				Locale.FRENCH, 	"Mettre à jour le tableau de pointage"
				), currentLocale);

		LocalizedString strAdd = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Add", 
				Locale.FRENCH, 	"Ajouter"
				), currentLocale);

		LocalizedString strAllowedSpools = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "ALLOWED<br/>SPOOLS", 
				Locale.FRENCH, 	"BOBINES<br/>ALLOUÉES"
				), currentLocale);

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<form method=\"post\">");
		strBuilder.append("	<input type=\"hidden\" name=\"gameEvent\" value=\"" + GameEventEnum.SCOREBOARD_UPDATED.toString() + "\" />");
		strBuilder.append("	<input type=\"hidden\" name=\"id\" value=\"" + game._id + "\" />");
		strBuilder.append("	<h2>" + strScoreboardUpdate + "</h2>");
		
        ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;

		strBuilder.append("	<div class=\"playingField\"></div>");
		
		OutputField(strBuilder, scoreboard.yellowField, TeamEnum.YELLOW, TeamEnum.BLUE);
		OutputField(strBuilder, scoreboard.blueField, TeamEnum.BLUE, TeamEnum.YELLOW);
	
		strBuilder.append("<input class=\"spinner\" name=\"blueDispenser1\" value=\"" + scoreboard.blueDispenser1 + "\"></input>");
		strBuilder.append("<input class=\"spinner\" name=\"blueDispenser2\" value=\"" + scoreboard.blueDispenser2 + "\"></input>");
		strBuilder.append("<input class=\"spinner\" name=\"yellowDispenser1\" value=\"" + scoreboard.yellowDispenser1 + "\"></input>");
		strBuilder.append("<input class=\"spinner\" name=\"yellowDispenser2\" value=\"" + scoreboard.yellowDispenser2 + "\"></input>");
		strBuilder.append("<input class=\"spinner\" name=\"blueTeamAllowedSpools\" value=\"" + (scoreboard.blueTeamAllowedSpools - scoreboard.blueDispenser1 - scoreboard.blueDispenser2) + "\"></input>");
		strBuilder.append("<input class=\"spinner\" name=\"yellowTeamAllowedSpools\" value=\"" + (scoreboard.yellowTeamAllowedSpools - scoreboard.yellowDispenser1 - scoreboard.yellowDispenser2) + "\"></input>");
		strBuilder.append("<div class=\"allowedSpools\">" + strAllowedSpools.toString() + "</div>");
				
		strBuilder.append("	<div class=\"clear\"></div>");
		strBuilder.append("	<br/>");
		strBuilder.append(GameController.outputAddAfterForView(game, currentLocale));
		strBuilder.append("	<br/>");
		strBuilder.append("	<input type=\"submit\" value=\"" + strAdd + "\" />");
		strBuilder.append("</form>");
		
		return strBuilder.toString();
	}
	
	static private void OutputField(StringBuilder strBuilder, Area[] area, TeamEnum team, TeamEnum oppositeTeam)
	{
		AreaPoints[] areaPoints = AreaPoints.values();
		for(int i = 0; i < areaPoints.length; i++)
		{
			strBuilder.append("<input class=\"spinner score " + areaPoints[i] + "_" + oppositeTeam.name() + "\" type=\"text\" name=\"" + areaPoints[i] + "_" + oppositeTeam.name() + "\" value=\"" + area[i].spoolCount + "\" />\n");
		}
	}
}
