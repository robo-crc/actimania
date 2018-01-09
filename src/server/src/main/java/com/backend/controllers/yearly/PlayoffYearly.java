package com.backend.controllers.yearly;

import com.backend.models.IPlayoff;

public class PlayoffYearly 
{
	public static IPlayoff getPlayoff()
	{
		return new Playoff26Teams();
	}
}
