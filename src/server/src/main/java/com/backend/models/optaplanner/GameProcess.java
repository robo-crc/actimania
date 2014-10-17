package com.backend.models.optaplanner;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import com.backend.models.Game;
import com.backend.models.GameEvent;
import com.backend.models.School;
import com.backend.models.SchoolPenalty;
import com.backend.models.enums.GameTypeEnum;

@PlanningEntity
public class GameProcess 
{
	private ArrayList<School> blueTeam;
	private ArrayList<School> yellowTeam;
	
	public GameProcess()
	{
	}
	
	public GameProcess(
			ArrayList<School> _blueTeam, 
			ArrayList<School> _yellowTeam
			)
	{
		blueTeam = _blueTeam;
		yellowTeam = _yellowTeam;
	}
	
	@PlanningEntityCollectionProperty
	public ArrayList<School> getBlueTeam()
	{
		return blueTeam;
	}
	
	public void setBlueTeam(ArrayList<School> _blueTeam)
	{
		blueTeam = _blueTeam;
	}
	
	@PlanningEntityCollectionProperty
	public ArrayList<School> getYellowTeam()
	{
		return yellowTeam;
	}
	
	public void setYellowTeam(ArrayList<School> _yellowTeam)
	{
		yellowTeam = _yellowTeam;
	}
	
	public ArrayList<School> getSchools()
	{
		ArrayList<School> schools = new ArrayList<School>(blueTeam);
		schools.addAll(yellowTeam);
		
		return schools;
	}
	
}
