package com.backend.models.GameEvent;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class PointModifierEvent extends GameEvent
{
	public final TeamEnum			team;
	public final int 				points;
	public final LocalizedString 	comment;
	
	public PointModifierEvent(
			@JsonProperty("team") 		TeamEnum		_team, 
			@JsonProperty("point") 		int 			_points,
			@JsonProperty("comment") 	LocalizedString	_comment
			)
	{
		super(GameEventEnum.POINT_MODIFIER);
		team 	= _team;
		points 	= _points;
		comment = _comment;
	}
}
