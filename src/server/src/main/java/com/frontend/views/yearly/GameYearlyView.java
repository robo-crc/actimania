package com.frontend.views.yearly;

import com.backend.models.GameState;
import com.backend.models.enums.yearly.TriangleStateEnum;
import com.backend.models.yearly.GameStateYearly;

public class GameYearlyView 
{
	public final static int GAME_PORTION_HEIGHT = 600;
	
	public static String getHtmlForGameState(GameState gameState)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<div class=\"clear playground\">");
		strBuilder.append("<img src=\"images/triangleLeft.png\" class=\"playfieldBackground triangleLeft\" />");
		strBuilder.append("<img src=\"images/triangleRight.png\" class=\"playfieldBackground triangleRight\" />");
		
		GameStateYearly state = (GameStateYearly)gameState;
		for(int holeNb = 0; holeNb < state.triangleLeft.length; holeNb++)
		{
			for(int unitPos = 0; unitPos < state.triangleLeft[holeNb].triangleStates.length; unitPos++)
			{
				TriangleStateEnum triangleState = state.triangleLeft[holeNb].triangleStates[unitPos];
				if(triangleState != TriangleStateEnum.EMPTY )
				{
					strBuilder.append("<img src=\"images/" + triangleState + "UnitLeft.png\" class=\"Unit Unit" + unitPos + " LEFTUnit" + holeNb + "_" + unitPos + "\"/>");
				}
			}
		}
	
		for(int holeNb = 0; holeNb < state.triangleRight.length; holeNb++)
		{
			for(int unitPos = 0; unitPos < state.triangleRight[holeNb].triangleStates.length; unitPos++)
			{
				TriangleStateEnum triangleState = state.triangleRight[holeNb].triangleStates[unitPos];
				if(triangleState != TriangleStateEnum.EMPTY )
				{
					strBuilder.append("<img src=\"images/" + triangleState + "UnitRight.png\" class=\"Unit Unit" + unitPos + " RIGHTUnit" + holeNb + "_" + unitPos + "\"/>");
				}
			}
		}
		strBuilder.append("</div>");
		
		return strBuilder.toString();
	}
}
