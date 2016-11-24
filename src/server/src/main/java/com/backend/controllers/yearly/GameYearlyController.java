package com.backend.controllers.yearly;

import org.joda.time.DateTime;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

public class GameYearlyController
{
	public static GameEvent processAction(Essentials essentials, String gameEvent)
	{
		if( gameEvent.equalsIgnoreCase(GameEventEnum.SCOREBOARD_UPDATED.toString()) )
		{
			//return new ScoreboardUpdateEvent(FieldYellow, FieldBlue, DateTime.now());
		}
		return null;
	}	
}
