package com.backend.models.enums;

public enum GameEventEnum
{
	START_GAME,
	POINT_MODIFIER, // If something happens, we can manually impact the score.
	SCHOOL_PENALTY,
	TEAM_PENALTY,
	MISCONDUCT_PENALTY,
	DID_NOT_SCORE,
	END_GAME,
	// YEARLY EVENTS
	SCOREBOARD_UPDATED,
}
