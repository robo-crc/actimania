package com.backend.controllers.yearly;

import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;
import com.google.common.base.Strings;

public class GameYearlyController
{
	public static GameEvent processAction(Essentials essentials, Game game, String gameEvent)
	{
		if( gameEvent.equalsIgnoreCase(GameEventEnum.SCOREBOARD_UPDATED.toString()) )
		{
			if(!Strings.isNullOrEmpty(Helpers.getParameter("blue", String.class, essentials)))
			{
				ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;

				return new ScoreboardUpdateEvent(
						Helpers.getParameter("cylinderBlue", Integer.class, essentials),
						scoreboard.cylinderYellow,
						Helpers.getParameter("prismBlue", Integer.class, essentials),
						scoreboard.prismYellow,
						Helpers.getParameter("vShapeBlue", Integer.class, essentials),
						scoreboard.vShapeYellow,
						Helpers.getParameter("threeLevelBlue", Integer.class, essentials),
						scoreboard.threeLevelYellow,
						Helpers.getParameter("gameMultiplierBlue", Integer.class, essentials),
						scoreboard.gameMultiplierYellow,
						DateTime.now()
						);
			}
			else if(!Strings.isNullOrEmpty(Helpers.getParameter("yellow", String.class, essentials)))
			{
				ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;

				return new ScoreboardUpdateEvent(
						scoreboard.cylinderBlue,
						Helpers.getParameter("cylinderYellow", Integer.class, essentials),
						scoreboard.prismBlue,
						Helpers.getParameter("prismYellow", Integer.class, essentials),
						scoreboard.vShapeBlue,
						Helpers.getParameter("vShapeYellow", Integer.class, essentials),
						scoreboard.threeLevelBlue,
						Helpers.getParameter("threeLevelYellow", Integer.class, essentials),
						scoreboard.gameMultiplierBlue,
						Helpers.getParameter("gameMultiplierYellow", Integer.class, essentials),
						DateTime.now()
						);				
			}
			else
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
		}
		return null;
	}	
}
