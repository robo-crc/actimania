package com.backend.models.GameEvent.yearly;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class ScoreboardUpdateEvent implements GameEvent
{
	public final int 	cylinderBlue;
	public final int 	cylinderYellow;

	public final int 	prismBlue;
	public final int 	prismYellow;
	
	public final int 	vShapeBlue;
	public final int 	vShapeYellow;

	public final int 	threeLevelBlue;
	public final int 	threeLevelYellow;
	
	public final int 	gameMultiplierBlue;
	public final int 	gameMultiplierYellow;
	
	public final DateTime time;
	
	public ScoreboardUpdateEvent(
			@JsonProperty("cylinderBlue")			int _cylinderBlue,
			@JsonProperty("cylinderYellow")			int _cylinderYellow,
			@JsonProperty("prismBlue")				int _prismBlue,
			@JsonProperty("prismYellow")			int _prismYellow,
			@JsonProperty("vShapeBlue")				int _vShapeBlue,
			@JsonProperty("vShapeYellow")			int _vShapeYellow,
			@JsonProperty("threeLevelBlue")			int _threeLevelBlue,
			@JsonProperty("threeLevelYellow")		int _threeLevelYellow,
			@JsonProperty("gameMultiplierBlue")		int _gameMultiplierBlue,
			@JsonProperty("gameMultiplierYellow")	int _gameMultiplierYellow,
			@JsonProperty("time")					DateTime	_time
			)
	{
		cylinderBlue 		= _cylinderBlue;
		cylinderYellow 		= _cylinderYellow;
		prismBlue 			= _prismBlue;
		prismYellow 		= _prismYellow;
		vShapeBlue 			= _vShapeBlue;
		vShapeYellow 		= _vShapeYellow;
		threeLevelBlue 		= _threeLevelBlue;
		threeLevelYellow 	= _threeLevelYellow;
		gameMultiplierBlue 	= _gameMultiplierBlue;
		gameMultiplierYellow = _gameMultiplierYellow;
		
		time 				= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.SCOREBOARD_UPDATED;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		return new LocalizedString(locale,
				"Scoreboard update",
				"Pointage mis-à-jour"
				);
	}
}
