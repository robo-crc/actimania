package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Playoff 
{
	final ArrayList<School> preliminaryRanking;
	@JsonIgnore
	public static final int SCHOOLS_PER_TEAM = 2;
	
	public static final int POSITIONS_SKIP_ROUND_ONE = 6;
	public static final int POSITIONS_SKIP_ROUND_TWO = 2;
	
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
		while(teamCountInRound < nbOfTeams )
		{
			teamCountInRound *= 2;
			roundsCount++;
		}
		return roundsCount;
	}
	
	public static PlayoffRound generateFirstRound(ArrayList<School> preliminaryRanking)
	{
		ArrayList<School> schoolsInRound = new ArrayList<School>();
		int roundCount = getRoundsCount(preliminaryRanking.size());
		int nbSchoolsPassToNextRound = (int) (Math.pow(2,roundCount) - POSITIONS_SKIP_ROUND_ONE + POSITIONS_SKIP_ROUND_TWO);

		for(int index = POSITIONS_SKIP_ROUND_ONE; index < preliminaryRanking.size(); index++)
		{
			schoolsInRound.add(preliminaryRanking.get(index));
		}

		int schoolsCount = schoolsInRound.size();
		
		int minGroupsCount = schoolsCount / (SCHOOLS_PER_TEAM * 4 - 1);
		int maxGroupsCount = schoolsCount / (SCHOOLS_PER_TEAM * 2);
		
		boolean foundGroupCount = false;
		int groupsCount = maxGroupsCount;
		while( groupsCount >= minGroupsCount && !foundGroupCount)
		{
			if(nbSchoolsPassToNextRound % groupsCount == 0)
			{
				foundGroupCount = true;
			}
			else
			{
				 groupsCount--;
			}
		}
		
		// If we did not find the group count, there's no point in continuing.
		Validate.isTrue(foundGroupCount);
		
		int nbOfGroupsWithExtraSchool = schoolsCount % groupsCount;
		int schoolsPerGroup = schoolsCount / groupsCount;
		
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		for(int groupNb = 0; groupNb < groupsCount; groupNb++)
		{
			ArrayList<School> schoolsInGroup = new ArrayList<School>();
			
			for(int schoolNb = 0; schoolNb < schoolsPerGroup; schoolNb++)
			{
				if(schoolNb < schoolsPerGroup / 2)
				{
					// Lowest first
					schoolsInGroup.add(schoolsInRound.get(schoolNb * groupsCount + groupNb));
				}
				else
				{
					// Highest first
					int position = schoolsCount  - ((schoolsPerGroup - schoolNb - 1) * groupsCount + groupNb) - 1;
					schoolsInGroup.add(schoolsInRound.get(position));
				}
			}
			
			// The last groups will get the extra school. Those groups contains the most average groups.
			int invertGroupNb = groupsCount - groupNb -1;
			if(nbOfGroupsWithExtraSchool > invertGroupNb)
			{
				schoolsInGroup.add(schoolsInRound.get((schoolsCount / 2) + invertGroupNb));
			}
			
			playoffGroups.add(new PlayoffGroup(schoolsInGroup));
		}
		
		PlayoffRound playoffRound = new PlayoffRound(playoffGroups);
		return playoffRound;
	}
	
	public static ArrayList<PlayoffRound> generateNextRound(PlayoffRound previousRound)
	{
		ArrayList<PlayoffRound> rounds = new ArrayList<PlayoffRound>();
		
		return rounds;
	}
}
