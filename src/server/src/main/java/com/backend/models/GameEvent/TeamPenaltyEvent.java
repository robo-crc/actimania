package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class TeamPenaltyEvent implements GameEvent
{
	public final TeamEnum	team;
	public final int 		pointsDeduction;
	public final DateTime	time;

	public TeamPenaltyEvent(
			@JsonProperty("team") 				TeamEnum	 _team, 
			@JsonProperty("pointsDeduction") 	int 		_pointsDeduction,
			@JsonProperty("time")				DateTime	_time
			)
	{
		team 				= _team;
		pointsDeduction 	= _pointsDeduction;
		time 				= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.TEAM_PENALTY;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		String frenchTeam = "";
		switch(team)
		{
		case YELLOW:
			frenchTeam = "jaune";
			break;
		case BLUE:
			frenchTeam = "bleue";
			break;
		}
		
		return new LocalizedString(locale,
				String.valueOf(pointsDeduction) + " points penalty for " + team.name() + " team.",
				 "Pénalitée de " + String.valueOf(pointsDeduction) + " points pour l'équipe " + frenchTeam + "."
				);
	}
}
