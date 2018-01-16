package com.backend.controllers.yearly;

import org.joda.time.DateTime;

import com.backend.models.IPlayoff;
import com.backend.models.enums.GameTypeEnum;

public class PlayoffYearlyController 
{
	public static DateTime GetPlayoffRoundStartTime(GameTypeEnum gameType)
	{
		DateTime startTime = null;
		switch(gameType)
		{
		case PLAYOFF_REPECHAGE:
			startTime = new DateTime(2017, 2, 3, 9, 0);
			break;
		case PLAYOFF_QUARTER:
			startTime = new DateTime(2017, 2, 3, 12, 0);
			break;
		case PLAYOFF_DEMI:
			startTime = new DateTime(2017, 2, 3, 14, 30);
			break;
		case PLAYOFF_FINAL:
			startTime = new DateTime(2017, 2, 3, 16, 30);
			break;
		default:
			break;
		}
		
		return startTime;
	}
	
	public static IPlayoff getPlayoff()
	{
		return new Playoff26Teams();
	}
}
