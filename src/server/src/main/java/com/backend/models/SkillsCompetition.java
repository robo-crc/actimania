package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;

public class SkillsCompetition 
{
	public final ObjectId 			_id;
	
	public final ArrayList<Skill> 	skills;

	@JsonIgnore
	public final static double SKILL_WEIGTH = 0.1; // 10% per skill competition in the final note
	
	public SkillsCompetition(
			@JsonProperty("_id") 		ObjectId 			_skillCompetitionId,
			@JsonProperty("skills") 	ArrayList<Skill>	_skills)
	{
		_id 	=_skillCompetitionId;
		skills 	= _skills;
	}
	
	public double getSchoolScore(School school)
	{
		double score = 0;
		
		for(Skill skillCompetition : skills)
		{
			score += skillCompetition.getPercentage(school) * SKILL_WEIGTH;
		}

		return score;
	}
	
	public static SkillsCompetition get(Database database)
	{
		return database.findOne(SkillsCompetition.class, "{ }");
	}
}
