package com.backend.controllers.yearly;

import org.joda.time.DateTime;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.yearly.TriangleStateEnum;
import com.backend.models.yearly.GameStateYearly;
import com.backend.models.yearly.Hole;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

public class GameYearlyController
{
	public static GameEvent processAction(Essentials essentials, String gameEvent)
	{
		if( gameEvent.equalsIgnoreCase(GameEventEnum.SCOREBOARD_UPDATED.toString()) )
		{
			Hole[] triangleSide1 = getTriangleFromParameters(SideEnum.SIDE1, essentials);
			Hole[] triangleSide2 = getTriangleFromParameters(SideEnum.SIDE2, essentials);
			
			return new ScoreboardUpdateEvent(triangleSide1, triangleSide2, DateTime.now());
		}
		return null;
	}
	
	private static Hole[] getTriangleFromParameters(SideEnum side, Essentials essentials)
	{
		Hole[] triangle = GameStateYearly.InitializeTriangle();
		
		for(int holeNb = 0; holeNb < triangle.length; holeNb++)
		{
			for(int posInHole = 0; posInHole < triangle[holeNb].triangleStates.length; posInHole++)
			{
				String parameterName = "hole_" + side.toString() + "_" + holeNb + "_" + posInHole;
				TriangleStateEnum triangleState = TriangleStateEnum.valueOf(Helpers.getParameter(parameterName, String.class, essentials));
				triangle[holeNb].triangleStates[posInHole] = triangleState;
			}
		}
		
		return triangle;
	}
	
}
