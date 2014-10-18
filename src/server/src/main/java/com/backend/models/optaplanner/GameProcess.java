package com.backend.models.optaplanner;

import java.util.ArrayList;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import com.backend.models.School;

@PlanningEntity
public class GameProcess 
{
	private ArrayList<School> blueTeam;
	private ArrayList<School> yellowTeam;
	private ArrayList<School> schools;
	
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
		schools = new ArrayList<School>();
		schools.addAll(blueTeam);
		schools.addAll(yellowTeam);
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
		return schools;
	}
	
}
