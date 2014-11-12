package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class ActuatorStateChangedEvent implements GameEvent
{
	public final SideEnum 			side;
	public final TargetEnum 		target;
	public final ActuatorStateEnum	actuatorState;
	public final DateTime			time;

	public ActuatorStateChangedEvent(
			@JsonProperty("side") 			SideEnum 			_side, 
			@JsonProperty("target") 		TargetEnum 			_target,
			@JsonProperty("actuatorState") 	ActuatorStateEnum 	_actuatorState,
			@JsonProperty("time")			DateTime			_time)
			{
				side = _side;
				target = _target;
				actuatorState = _actuatorState;
				time = _time;
			}
	
	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.ACTUATOR_STATE_CHANGED;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		String english = target.name().substring(0, 1).toUpperCase() + target.name().substring(1).toLowerCase() + " target on " + side.name().toLowerCase() + " side status changed to " + actuatorState.name().toLowerCase();
		
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
		
		String frenchActuatorState = "";
		switch(actuatorState)
		{
		case YELLOW:
			frenchActuatorState = "jaune";
			break;
		case BLUE:
			frenchActuatorState = "bleue";
			break;
		case CLOSED:
			frenchActuatorState = "fermée";
			break;
		}

		String french = "La cible " + frenchTarget + " du côté " + frenchSide + " est maintenant " + frenchActuatorState + ".";
		return new LocalizedString(locale,
				english,
				french
				);
	}
}
