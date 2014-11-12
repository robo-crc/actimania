package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class TargetHitEvent implements GameEvent
{
	public final SideEnum 	side;
	public final TargetEnum	target;
	public final DateTime	time;

	public TargetHitEvent(
			@JsonProperty("side") 			SideEnum 			_side, 
			@JsonProperty("target") 		TargetEnum 			_target,
			@JsonProperty("time")			DateTime			_time
			)
	{
		side 	= _side;
		target 	= _target;
		time 	= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.TARGET_HIT;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		String english = target.name().substring(0, 1).toUpperCase() + target.name().substring(1).toLowerCase() + " target hit on " + side.name().toLowerCase() + " side.";
		
		String frenchTarget = "";
		switch(target)
		{
		case LOW:
			frenchTarget = "basse";
			break;
		case MID:
			frenchTarget = "moyenne";
			break;
		case HIGH:
			frenchTarget = "haute";
			break;
		}
		
		String frenchSide = "";
		switch(side)
		{
		case YELLOW:
			frenchTarget = "jaune";
			break;
		case BLUE:
			frenchTarget = "bleue";
			break;
		}

		String french = "La cible " + frenchTarget + " du côté " + frenchSide + " a été frappée.";
		return new LocalizedString(locale,
				english,
				french
				);
	}
}
