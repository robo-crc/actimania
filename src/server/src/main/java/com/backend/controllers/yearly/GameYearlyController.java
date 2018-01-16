package com.backend.controllers.yearly;

import org.joda.time.DateTime;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

public class GameYearlyController
{
	public static GameEvent processAction(Essentials essentials, String gameEvent)
	{
		if( gameEvent.equalsIgnoreCase(GameEventEnum.SCOREBOARD_UPDATED.toString()) )
		{
			return new ScoreboardUpdateEvent(
					Helpers.getParameter("cylinderBlue", Integer.class, essentials),
					Helpers.getParameter("cylinderYellow", Integer.class, essentials),
					Helpers.getParameter("prismBlue", Integer.class, essentials),
					Helpers.getParameter("prismYellow", Integer.class, essentials),
					Helpers.getParameter("vShapeBlue", Integer.class, essentials),
					Helpers.getParameter("vShapeYellow", Integer.class, essentials),
					Helpers.getParameter("threeLevelBlue", Integer.class, essentials),
					Helpers.getParameter("threeLevelYellow", Integer.class, essentials),
					Helpers.getParameter("gameMultiplierBlue", Integer.class, essentials),
					Helpers.getParameter("gameMultiplierYellow", Integer.class, essentials),
					DateTime.now()
					);
		}
		return null;
	}	
}
