package com.frontend.views.yearly;

import java.text.DecimalFormat;
import java.util.Locale;

import com.backend.models.GameState;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.yearly.GameStateYearly;

public class GameYearlyView 
{
	public final static int GAME_PORTION_HEIGHT = 580;
	
	public static String getHtmlForGameState(GameState gameState, Locale currentLocale)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<div class=\"clear playground\">");
		
		ScoreboardUpdateEvent scoreboard = ((GameStateYearly)gameState).currentScoreboard;

		strBuilder.append("<div class=\"playingField\"></div>");
		strBuilder.append("<div class=\"Score cylinderBlue\">" 			+ scoreboard.cylinderBlue * GameStateYearly.CYLINDER_GP + "</div>");
		strBuilder.append("<div class=\"Score cylinderYellow\">" 		+ scoreboard.cylinderYellow * GameStateYearly.CYLINDER_GP + "</div>");
		strBuilder.append("<div class=\"Score prismBlue\">" 			+ scoreboard.prismBlue * GameStateYearly.PRISM_GP + "</div>");
		strBuilder.append("<div class=\"Score prismYellow\">" 		 	+ scoreboard.prismYellow * GameStateYearly.PRISM_GP + "</div>");
		strBuilder.append("<div class=\"Score vShapeBlue\">"           + scoreboard.vShapeBlue * GameStateYearly.VSHAPE_GP + "</div>");
		strBuilder.append("<div class=\"Score vShapeYellow\">"         + scoreboard.vShapeYellow  * GameStateYearly.VSHAPE_GP + "</div>");
		strBuilder.append("<div class=\"Score threeLevelBlue" + scoreboard.threeLevelBlue + "\">" + scoreboard.threeLevelBlue + "</div>");
		strBuilder.append("<div class=\"Score threeLevelYellow" + scoreboard.threeLevelYellow + "\">"  + scoreboard.threeLevelYellow + "</div>");
		strBuilder.append("<div class=\"Score gameMultiplierBlue\">"   + new DecimalFormat("#.##").format(GameStateYearly.GetBlueMultiplier(scoreboard)) + "</div>");
		strBuilder.append("<div class=\"Score gameMultiplierYellow\">" + new DecimalFormat("#.##").format(GameStateYearly.GetYellowMultiplier(scoreboard)) + "</div>");
		
		return strBuilder.toString();
	}
}
