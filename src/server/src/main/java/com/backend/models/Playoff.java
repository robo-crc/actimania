package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.controllers.yearly.PlayoffYearly;
import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;

public class Playoff 
{
	@JsonIgnore
	public static final int SCHOOLS_PER_TEAM = 2;
	
	public static final int POSITIONS_SKIP_ROUND_ONE = 6;
	public static final int POSITIONS_SKIP_ROUND_TWO = 2;
	
	public final ObjectId _id;
	public final ArrayList<School> excludedSchools;
	
	@JsonIgnore
	public final IPlayoff iPlayoff;
	public Playoff(
			@JsonProperty("_id") 				ObjectId playoffId,
			@JsonProperty("excludedSchools") 	ArrayList<School> _excludedSchools,
												IPlayoff _iPlayoff
			)
	{
		_id = playoffId;
		excludedSchools = _excludedSchools;
		if(_iPlayoff == null)	
		{
			iPlayoff = PlayoffYearly.getPlayoff();
		}
		else
		{
			iPlayoff = _iPlayoff;
		}
	}

	// I've tried to make this function generic, but finally those are kind of decisions my tournament per tournament.
	public PlayoffRound generatePlayoffRound(Database database, Tournament tournament, PlayoffRound previousRound, GameTypeEnum gameType)
	{
		SkillsCompetition skillsCompetition = SkillsCompetition.get(database);
		ArrayList<School> preliminaryRanking = getRemainingSchools(tournament.getPreliminaryRanking(skillsCompetition), excludedSchools);
		
		int groupNo = 0;
		if(previousRound != null)
		{
			groupNo = previousRound.playoffGroups.get(previousRound.playoffGroups.size() - 1).groupNo + 1;
		}
		
		ArrayList<PlayoffGroup> playoffGroups = iPlayoff.GenerateNextRound(tournament, previousRound, groupNo, gameType, preliminaryRanking);
		
		return new PlayoffRound(null, playoffGroups, gameType);
	}
	
	public static Playoff get(Database database)
	{
		Playoff playoff = database.findOne(Playoff.class, "{ }");
		if(playoff == null)
		{
			playoff = new Playoff(null, new ArrayList<School>(), null);
			database.save(playoff);
		}
		return playoff;
	}
	
	public static ArrayList<School> getRemainingSchools(ArrayList<School> allSchools, ArrayList<School> excludedSchools)
	{
		ArrayList<School> ret = new ArrayList<School>(allSchools);
		ret.removeAll(excludedSchools);
		return ret;
	}
}
