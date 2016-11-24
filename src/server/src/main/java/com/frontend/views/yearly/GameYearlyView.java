package com.frontend.views.yearly;

import com.backend.models.GameState;
import com.backend.models.yearly.GameStateYearly;

public class GameYearlyView 
{
	public final static int GAME_PORTION_HEIGHT = 600;
	
	public static String getHtmlForGameState(GameState gameState)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<div class=\"clear playground\">");
		
		GameStateYearly state = (GameStateYearly)gameState;

		
		return strBuilder.toString();
	}
}
