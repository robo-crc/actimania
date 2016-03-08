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
			startTime = new DateTime(2016, 2, 27, 8, 30);
			break;
		case PLAYOFF_QUARTER:
			startTime = new DateTime(2016, 2, 27, 11, 45);
			break;
		case PLAYOFF_DEMI:
			startTime = new DateTime(2016, 2, 27, 14, 45);
			break;
		case PLAYOFF_FINAL:
			startTime = new DateTime(2016, 2, 27, 16, 15);
			break;
		default:
			break;
		}
		
		return startTime;
	}
}
