package com.backend.models.GameEvent;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamPenaltyEvent extends GameEvent
{
	public final TeamEnum	team;
	public final int 		pointsDeduction;

	public TeamPenaltyEvent(
			@JsonProperty("team") 				TeamEnum	 _team, 
			@JsonProperty("pointsDeduction") 	int 		_pointsDeduction
			)
	{
		super(GameEventEnum.TEAM_PENALTY);
		team 				= _team;
		pointsDeduction 	= _pointsDeduction;
	}
}
