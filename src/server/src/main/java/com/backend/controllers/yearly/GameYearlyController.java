package com.backend.controllers.yearly;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.enums.GameEventEnum;
import com.framework.models.Essentials;

public class GameYearlyController
{
	public static GameEvent processAction(Essentials essentials, String gameEvent)
	{
		if( gameEvent.equalsIgnoreCase(GameEventEnum.SCOREBOARD_UPDATED.toString()) )
		{
			/*Area[] yellowArea = getAreaPoints(essentials, TeamEnum.YELLOW);
			Area[] blueArea = getAreaPoints(essentials, TeamEnum.BLUE);
			int yellowDispenser1 = Helpers.getParameter("yellowDispenser1", Integer.class, essentials);
			int yellowDispenser2 = Helpers.getParameter("yellowDispenser2", Integer.class, essentials);
			int blueDispenser1 = Helpers.getParameter("blueDispenser1", Integer.class, essentials);
			int blueDispenser2 = Helpers.getParameter("blueDispenser2", Integer.class, essentials);
			int yellowTeamAllowedSpools = Helpers.getParameter("yellowTeamAllowedSpools", Integer.class, essentials);
			int blueTeamAllowedSpools = Helpers.getParameter("blueTeamAllowedSpools", Integer.class, essentials);
			TeamEnum teamMultiplier = TeamEnum.valueOf(Helpers.getParameter("teamMultiplier", String.class, essentials));
			
			return new ScoreboardUpdateEvent(yellowArea, blueArea, yellowDispenser1, yellowDispenser2, blueDispenser1, blueDispenser2, yellowTeamAllowedSpools, blueTeamAllowedSpools, teamMultiplier, DateTime.now());
			*/
		}
		return null;
	}	
}
