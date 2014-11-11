package com.backend.models.enums;

public enum GameEventEnum
{
	START_GAME,
	ACTUATOR_STATE_CHANGED,
	TARGET_HIT,
	POINT_MODIFIER, // If something happens, we can manually impact the score.
	SCHOOL_PENALTY,
	TEAM_PENALTY,
	MISCONDUCT_PENALTY,
	END_GAME,
}
