package com.backend.models.enums;

public enum GameEventEnum
{
	START_GAME,
	ACTUATOR_CHANGED,
	TARGET_HIT,
	POINT_MODIFIER, // If something happens, we can manually impact the score.
	END_GAME,
}
