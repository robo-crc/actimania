package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class PointModifierEvent implements GameEvent
{
	public final TeamEnum			team;
	public final int 				points;
	public final LocalizedString 	comment;
	public final DateTime			time;
	
	public PointModifierEvent(
			@JsonProperty("team") 		TeamEnum		_team, 
			@JsonProperty("point") 		int 			_points,
			@JsonProperty("comment") 	LocalizedString	_comment,
			@JsonProperty("time")		DateTime		_time
			)
	{
		team 	= _team;
		points 	= _points;
		comment = _comment;
		time = _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.POINT_MODIFIER;
	}
}
