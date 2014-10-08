package com.backend.models;

import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class PointModifier 
{
	public final TeamEnum			team;
	public final int 				points;
	public final LocalizedString 	comment;
	
	public PointModifier(
			@JsonProperty("team") 		TeamEnum		_team, 
			@JsonProperty("point") 		int 			_points,
			@JsonProperty("comment") 	LocalizedString	_comment
			)
	{
		team 	= _team;
		points 	= _points;
		comment = _comment;
	}
}
