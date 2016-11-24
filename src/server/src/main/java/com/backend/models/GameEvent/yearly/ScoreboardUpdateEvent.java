package com.backend.models.GameEvent.yearly;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.yearly.Area;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class ScoreboardUpdateEvent implements GameEvent
{
	public final Area[]	 	yellowField;
	public final Area[]	 	blueField;
	public final DateTime	time;
	public final int 	yellowTeamAllowedSpools;
	public final int 	blueTeamAllowedSpools;

	public final int 	yellowDispenser1;
	public final int 	yellowDispenser2;
	public final int 	blueDispenser1;
	public final int 	blueDispenser2;
	
	public final TeamEnum hasMultiplier;

	public ScoreboardUpdateEvent(
			@JsonProperty("yellowField") 			Area[]	 	_yellowField,
			@JsonProperty("blueField") 				Area[]	 	_blueField,
			@JsonProperty("yellowDispenser1")		int 		_yellowDispenser1,
			@JsonProperty("yellowDispenser2")		int 		_yellowDispenser2,
			@JsonProperty("blueDispenser1")			int 		_blueDispenser1,
			@JsonProperty("blueDispenser2")			int 		_blueDispenser2,
			@JsonProperty("yellowTeamAllowedSpools")int 		_yellowTeamAllowedSpools,
			@JsonProperty("blueTeamAllowedSpools")	int 		_blueTeamAllowedSpools,
			@JsonProperty("hasMultiplier")			TeamEnum 	_hasMultipler,
			@JsonProperty("time")					DateTime	_time
			)
	{
		yellowField			= _yellowField;
		blueField 			= _blueField;
		yellowDispenser1 	= _yellowDispenser1;
		yellowDispenser2 	= _yellowDispenser2;
		blueDispenser1 		= _blueDispenser1;
		blueDispenser2 		= _blueDispenser2;
		
		yellowTeamAllowedSpools = _yellowTeamAllowedSpools;
		blueTeamAllowedSpools 	= _blueTeamAllowedSpools;
		
		hasMultiplier		= _hasMultipler;

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
