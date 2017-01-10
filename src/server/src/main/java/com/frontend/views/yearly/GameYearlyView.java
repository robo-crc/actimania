package com.frontend.views.yearly;

import java.util.Locale;

import com.backend.controllers.GameController;
import com.backend.models.GameState;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.yearly.AreaPoints;
import com.backend.models.yearly.Area;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;

public class GameYearlyView 
{
	public final static int GAME_PORTION_HEIGHT = 650;
	
	public static String getHtmlForGameState(GameState gameState, Locale currentLocale)
	{		
		LocalizedString strAllowedSpools = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "ALLOWED<br/>SPOOLS", 
				Locale.FRENCH, 	"BOBINES<br/>ALLOUÉES"
				), currentLocale);

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<div class=\"clear playground\">");
		
		ScoreboardUpdateEvent scoreboard = ((GameStateYearly)gameState).currentScoreboard;
		
		switch(scoreboard.hasMultiplier)
		{
		case NONE:
			strBuilder.append("	<div class=\"playingField playingFieldNone\"></div>");
			break;
		case BLUE:
			strBuilder.append("	<div class=\"playingField playingFieldBlue\"></div>");
			break;
		case YELLOW:
			strBuilder.append("	<div class=\"playingField playingFieldYellow\"></div>");
			break;
		}
		
		OutputField(strBuilder, scoreboard.yellowField, TeamEnum.YELLOW, TeamEnum.BLUE);
		OutputField(strBuilder, scoreboard.blueField, TeamEnum.BLUE, TeamEnum.YELLOW);
	
		strBuilder.append("<div class=\"Score BLUE blueDispenser1\">" + scoreboard.blueDispenser1 + "</div>");
		strBuilder.append("<div class=\"Score BLUE blueDispenser2\">" + scoreboard.blueDispenser2 + "</div>");
		strBuilder.append("<div class=\"Score YELLOW yellowDispenser1\">" + scoreboard.yellowDispenser1 + "</div>");
		strBuilder.append("<div class=\"Score YELLOW yellowDispenser2\">" + scoreboard.yellowDispenser2 + "</div>");
		strBuilder.append("<div class=\"Score BLUE blueTeamAllowedSpools\">" + (scoreboard.blueTeamAllowedSpools - scoreboard.blueDispenser1 - scoreboard.blueDispenser2) + "</div>");
		strBuilder.append("<div class=\"Score YELLOW yellowTeamAllowedSpools\">" + (scoreboard.yellowTeamAllowedSpools - scoreboard.yellowDispenser1 - scoreboard.yellowDispenser2) + "</div>");
		strBuilder.append("<div class=\"allowedSpools\">" + strAllowedSpools.toString() + "</div>");
		
		return strBuilder.toString();
	}
		
	static private void OutputField(StringBuilder strBuilder, Area[] area, TeamEnum team, TeamEnum oppositeTeam)
	{
		AreaPoints[] areaPoints = AreaPoints.values();
		for(int i = 0; i < areaPoints.length; i++)
		{
			strBuilder.append("<div class=\"Score " + team.name() + " " + areaPoints[i] + "_" + oppositeTeam.name() + "\">" + area[i].spoolCount + "</div>" );					
		}
	}	
}
