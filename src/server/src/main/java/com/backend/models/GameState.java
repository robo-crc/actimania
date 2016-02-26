package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.models.GameEvent.DidNotScoreEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.MisconductPenaltyEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.SchoolPenaltyEvent;
import com.backend.models.GameEvent.ScoreboardUpdateEvent;
import com.backend.models.GameEvent.TeamPenaltyEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.TriangleStateEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameState 
{
	@JsonIgnore
	public static final int[] PIECE_VALUE_PER_LEVEL = { 10, 20, 30, 40 };
	
	public final ObjectId 					_id;
	public final Hole[]						triangleLeft;
	public final Hole[]						triangleRight;
	public final GameEvent					lastGameEvent;
	public final int 						blueScore;
	public final int 						yellowScore;
	
	public final ArrayList<SchoolInteger>	penalties;
	public final ArrayList<School>			misconductPenalties;
	public final ArrayList<School>			didNotScore;
	public final int						pointModifierBlue;
	public final int						pointModifierYellow;
	
	public GameState(
			@JsonProperty("_id")					ObjectId 					_gameEventId,
			@JsonProperty("triangleStateLeft")		Hole[] 						_triangleLeft,
			@JsonProperty("triangleStateRight")		Hole[] 						_triangleRight,
			@JsonProperty("lastGameEvent")			GameEvent					_lastGameEvent,
			@JsonProperty("blueScore")				int 						_blueScore,
			@JsonProperty("yellowScore")			int 						_yellowScore,
			@JsonProperty("penalties")				ArrayList<SchoolInteger>	_penalties,
			@JsonProperty("misconductPenalties")	ArrayList<School>			_misconductPenalties,
			@JsonProperty("didNotScore")			ArrayList<School>			_didNotScore,
			@JsonProperty("pointModifierBlue")		int							_pointModifierBlue,
			@JsonProperty("pointModifierYellow")	int							_pointModifierYellow
			)
	{
		_id 				= _gameEventId;
		triangleLeft		= 	_triangleLeft;
		triangleRight 		= 	_triangleRight;
		lastGameEvent		= _lastGameEvent;
		blueScore 			= _blueScore;
		yellowScore 		= _yellowScore;
		penalties 			= _penalties;
		misconductPenalties	= _misconductPenalties;
		didNotScore			= _didNotScore;
		pointModifierBlue	= _pointModifierBlue;
		pointModifierYellow	= _pointModifierYellow;
	}
	
	public GameState(GameState previousState, GameEvent gameEvent)
	{
		_id = null;
		lastGameEvent = gameEvent;

		Hole[] localTriangleLeft = null;
		Hole[] localTriangleRight = null;
		
		int localBlueScore = 0;
		int localYellowScore = 0;
		
		int localModifierBlue = 0;
		int localModifierYellow = 0;
		
		ArrayList<SchoolInteger> 	localPenalties = null;
		ArrayList<School> 			localMisconductPenalties = null;
		ArrayList<School> 			localDidNotScore = null;
		
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			localTriangleLeft = InitializeTriangle();
			localTriangleRight = InitializeTriangle();
			
			localPenalties = new ArrayList<SchoolInteger>();
			localMisconductPenalties = new ArrayList<School>();
			localDidNotScore = new ArrayList<School>();
		}
		else
		{
			// We need to make a copy of the array so that the array is not a reference of each
			localTriangleLeft = CloneTriangle(previousState.triangleLeft);
			localTriangleRight = CloneTriangle(previousState.triangleRight);

			localBlueScore = GetScore(TeamEnum.BLUE, localTriangleLeft) + GetScore(TeamEnum.BLUE, localTriangleRight);
			localYellowScore = GetScore(TeamEnum.YELLOW, localTriangleLeft) + GetScore(TeamEnum.YELLOW, localTriangleRight);

			localModifierBlue = previousState.pointModifierBlue;
			localModifierYellow = previousState.pointModifierYellow;
			
			localPenalties 				= new ArrayList<SchoolInteger>(previousState.penalties);
			localMisconductPenalties	= new ArrayList<School>(previousState.misconductPenalties);
			localDidNotScore			= new ArrayList<School>(previousState.didNotScore);

			if(gameEvent.getGameEventEnum() == GameEventEnum.SCOREBOARD_UPDATED)
			{
				ScoreboardUpdateEvent scoreboardUpdatedEvent = (ScoreboardUpdateEvent) gameEvent;
				
				localTriangleLeft = scoreboardUpdatedEvent.triangleLeft.clone();
				localTriangleRight = scoreboardUpdatedEvent.triangleRight.clone();

				localBlueScore = GetScore(TeamEnum.BLUE, localTriangleLeft) + GetScore(TeamEnum.BLUE, localTriangleRight);
				localYellowScore = GetScore(TeamEnum.YELLOW, localTriangleLeft) + GetScore(TeamEnum.YELLOW, localTriangleRight);
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.POINT_MODIFIER)
			{
				PointModifierEvent pointModifierEvent = (PointModifierEvent) gameEvent;
				if(pointModifierEvent.team == TeamEnum.BLUE)
				{
					localModifierBlue += pointModifierEvent.points;
				}
				else if(pointModifierEvent.team == TeamEnum.YELLOW)
				{
					localModifierYellow += pointModifierEvent.points;
				}
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.SCHOOL_PENALTY)
			{
				SchoolPenaltyEvent schoolPenaltyEvent = (SchoolPenaltyEvent) gameEvent;
				localPenalties.add(new SchoolInteger(schoolPenaltyEvent.school, schoolPenaltyEvent.pointsDeduction));
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.TEAM_PENALTY)
			{
				TeamPenaltyEvent teamPenaltyEvent = (TeamPenaltyEvent) gameEvent;
				if(teamPenaltyEvent.team == TeamEnum.BLUE)
				{
					localModifierBlue -= teamPenaltyEvent.pointsDeduction;
				}
				else if(teamPenaltyEvent.team == TeamEnum.YELLOW)
				{
					localModifierYellow -= teamPenaltyEvent.pointsDeduction;
				}
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.MISCONDUCT_PENALTY)
			{
				MisconductPenaltyEvent misconductPenaltyEvent = (MisconductPenaltyEvent) gameEvent;
				localMisconductPenalties.add(misconductPenaltyEvent.school);
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.DID_NOT_SCORE)
			{
				DidNotScoreEvent didNotScoreEvent = (DidNotScoreEvent) gameEvent;
				localDidNotScore.add(didNotScoreEvent.school);
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.END_GAME)
			{
				// Nothing to do for now.
			}
		}

		triangleRight		= localTriangleRight;
		triangleLeft		= localTriangleLeft;
		blueScore 			= localBlueScore + localModifierBlue;
		yellowScore 		= localYellowScore + localModifierYellow;
		pointModifierBlue	= localModifierBlue;
		pointModifierYellow	= localModifierYellow;
		penalties			= localPenalties;
		misconductPenalties	= localMisconductPenalties;
		didNotScore			= localDidNotScore;
	}
	
	public static Hole[] CloneTriangle(Hole[] previousTriangle)
	{
		Hole[] triangle = new Hole[previousTriangle.length];
		for(int i = 0; i < triangle.length; i++)
		{
			triangle[i] = new Hole(previousTriangle[i]);
		}
		return triangle;		
	}
	
	public static Hole[] InitializeTriangle()
	{
		Hole[] triangle = new Hole[10];
		for(int i = 0; i < triangle.length; i++)
		{
			triangle[i] = new Hole();
		}
		return triangle;
	}
	
	public static int GetScore(TeamEnum team, Hole[] triangle)
	{
		TriangleStateEnum triangleState = null;
		
		if(team == TeamEnum.BLUE)
		{
			triangleState = TriangleStateEnum.BLUE;
		}
		else if(team == TeamEnum.YELLOW)
		{
			triangleState = TriangleStateEnum.YELLOW;
		}
		
		int score = 0;
		for(int i = 0; i < triangle.length; i++)
		{
			score += (GetPieceValueByVertex(i) * triangle[i].GetNumberOf(triangleState));
		}
		
		score *= GetMultiplier(triangleState, triangle);
		
		return score;
	}
	
	public static int GetPieceValueByVertex(int vertex)
	{
		switch(vertex)
		{
		case 0:
			return PIECE_VALUE_PER_LEVEL[3];
		case 1:
		case 2:
			return PIECE_VALUE_PER_LEVEL[2];
		case 3:
		case 4:
		case 5:
			return PIECE_VALUE_PER_LEVEL[1];
		case 6:
		case 7:
		case 8:
		case 9:
			return PIECE_VALUE_PER_LEVEL[0];
		}
		assert(true);
		return 0;
	}
	
	public static int GetMultiplier(TriangleStateEnum triangleState, Hole[] triangle)
	{
		/*
			   0
			  1 2
			 3 4 5
			6 7 8 9

			0 1 3 6 7 8 9 5 2
			0 1 3 4 5 2
			1 3 6 7 8 4
			2 4 7 8 9 5
			0 1 2
			1 3 4
			2 4 5
			3 6 7
			4 7 8
			5 8 9
			1 4 2
			3 4 7
			4 5 8
		 */
		
		if( foundTriangle(triangle, triangleState, new int[] {0, 1, 3, 6, 7, 8, 9, 5, 2} ))
		{
			return 4;
		}
		else if( foundTriangle(triangle, triangleState, new int[] {0, 1, 3, 4, 5, 2} ) ||
				 foundTriangle(triangle, triangleState, new int[] {1, 3, 6, 7, 8, 4} ) ||
				 foundTriangle(triangle, triangleState, new int[] {2, 4, 7, 8, 9, 5} ))
		{
			return 3;
		}
		else if( foundTriangle(triangle, triangleState, new int[] {0, 1, 2} ) ||
				 foundTriangle(triangle, triangleState, new int[] {1, 3, 4} ) ||
				 foundTriangle(triangle, triangleState, new int[] {2, 4, 5} ) ||
				 foundTriangle(triangle, triangleState, new int[] {3, 6, 7} ) ||
				 foundTriangle(triangle, triangleState, new int[] {4, 7, 8} ) ||
				 foundTriangle(triangle, triangleState, new int[] {5, 8, 9} ) ||
				 foundTriangle(triangle, triangleState, new int[] {1, 4, 2} ) ||
				 foundTriangle(triangle, triangleState, new int[] {3, 4, 7} ) ||	 
				 foundTriangle(triangle, triangleState, new int[] {4, 5, 8} ))
		{
			return 2;
		}
		return 1;
	}
	
	public static boolean foundTriangle(Hole[] triangle, TriangleStateEnum triangleState, int[] vertexList)
	{
		for(int i = 0; i < vertexList.length; i++)
		{
			if(triangle[vertexList[i]].Front() != triangleState)
			{
				return false;
			}
		}
		return true;
	}
}
