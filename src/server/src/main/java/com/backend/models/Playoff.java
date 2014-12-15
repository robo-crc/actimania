package com.backend.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Playoff 
{
	final ArrayList<School> preliminaryRanking;
	@JsonIgnore
	public static final int SCHOOLS_PER_TEAM = 2;
	
	public static final int[] POSITIONS_SKIP_ROUND_ONE = { 0, 1, 2, 3, 4, 5 };
	public static final int[] POSITIONS_SKIP_ROUND_TWO = { 0, 1 };
	
	public Playoff(
			ArrayList<School> _preliminaryRanking,
			ArrayList<School> _excludedSchools)
	{
		preliminaryRanking = _preliminaryRanking;
		for(School excludedSchool : _excludedSchools)
		{
			preliminaryRanking.remove(excludedSchool);
		}
	}
	
	public static int getRoundsCount(int nbOfTeams)
	{
		int roundsCount = 1;
		int teamCountInRound = SCHOOLS_PER_TEAM * 2; // Always start with the final
		while(teamCountInRound <= (nbOfTeams - POSITIONS_SKIP_ROUND_ONE.length) )
		{
			teamCountInRound *= 2;
			roundsCount++;
		}
		return roundsCount;
	}
	
	/*
	public static ArrayList<Game> generatePlayoffRounds()
	{
		
	}
	*/
}
