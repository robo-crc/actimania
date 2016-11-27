package com.backend.models;

import java.util.ArrayList;

import com.backend.models.enums.GameTypeEnum;

public interface IPlayoff 
{
	public ArrayList<PlayoffGroup> GenerateNextRound(Tournament tournament, PlayoffRound previousRound, int groupNo, GameTypeEnum gameType, ArrayList<School> preliminaryRanking);
}
