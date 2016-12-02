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

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<form method=\"post\">");
		strBuilder.append("	<input type=\"hidden\" name=\"gameEvent\" value=\"" + GameEventEnum.SCOREBOARD_UPDATED.toString() + "\" />");
		strBuilder.append("	<input type=\"hidden\" name=\"id\" value=\"" + game._id + "\" />");
		strBuilder.append("	<h2>" + strScoreboardUpdate + "</h2>");
		
		ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;
		
		OutputField(strBuilder, scoreboard.yellowField, scoreboard.yellowDispenser1, scoreboard.yellowDispenser2, TeamEnum.BLUE, scoreboard.hasMultiplier == TeamEnum.BLUE);
		OutputField(strBuilder, scoreboard.blueField, scoreboard.blueDispenser1, scoreboard.blueDispenser2, TeamEnum.YELLOW, scoreboard.hasMultiplier == TeamEnum.YELLOW);
		
		strBuilder.append("	<div class=\"clear\"></div>");
		strBuilder.append("	<br/>");
		strBuilder.append(GameController.outputAddAfterForView(game, currentLocale));
		strBuilder.append("	<br/>");
		strBuilder.append("	<input type=\"submit\" value=\"" + strAdd + "\" />");
		strBuilder.append("</form>");
		
		return strBuilder.toString();
	}
	
	static private void OutputField(StringBuilder strBuilder, Area[] area, int dispenserTop, int dispenserBottom, TeamEnum team, boolean hasMultiplier)
	{
		strBuilder.append("<div class=\"" + AreaPoints.ONE_HUNDRED.toString() + "_" + team.name() + "\">" + area[AreaPoints.ONE_HUNDRED.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.FORTY.toString() + "_" + team.name() + "\">" + area[AreaPoints.FORTY.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.THIRTHY.toString() + "_" + team.name() + "\">" + area[AreaPoints.THIRTHY.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.TWENTY_BIG.toString() + "_" + team.name() + "\">" + area[AreaPoints.TWENTY_BIG.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.TWENTY_SMALL.toString() + "_" + team.name() + "\">" + area[AreaPoints.TWENTY_SMALL.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.TEN_TOP.toString() + "_" + team.name() + "\">" + area[AreaPoints.TEN_TOP.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.TEN_BOTTOM.toString() + "_" + team.name() + "\">" + area[AreaPoints.TEN_BOTTOM.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.FIVE_TOP.toString() + "_" + team.name() + "\">" + area[AreaPoints.FIVE_TOP.ordinal()].spoolCount + "</div>" );
		strBuilder.append("<div class=\"" + AreaPoints.FIVE_BOTTOM.toString() + "_" + team.name() + "\">" + area[AreaPoints.FIVE_BOTTOM.ordinal()].spoolCount + "</div>" );
	}
}
