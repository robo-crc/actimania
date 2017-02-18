package com.backend.controllers.yearly;

import org.joda.time.DateTime;

import com.backend.models.enums.GameTypeEnum;

public class PlayoffYearlyController 
{
	public static DateTime GetPlayoffRoundStartTime(GameTypeEnum gameType)
	{
		DateTime startTime = null;
		switch(gameType)
		{
		case PLAYOFF_REPECHAGE:
			startTime = new DateTime(2017, 2, 18, 8, 20);
			break;
		case PLAYOFF_QUARTER:
			startTime = new DateTime(2017, 2, 18, 12, 30);
			break;
		case PLAYOFF_DEMI:
			startTime = new DateTime(2017, 2, 18, 15, 30);
			break;
		case PLAYOFF_FINAL:
			startTime = new DateTime(2017, 2, 18, 17, 0);
			break;
		default:
			break;
		}
		
		return startTime;
	}
}
