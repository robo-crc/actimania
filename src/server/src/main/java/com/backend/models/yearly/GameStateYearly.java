package com.backend.models.yearly;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.models.GameState;
import com.backend.models.School;
import com.backend.models.SchoolInteger;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.yearly.TriangleStateEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStateYearly extends GameState
{
	@JsonIgnore
	public static final int[] PIECE_VALUE_PER_LEVEL = { 10, 20, 30, 40 };
	
	public final Hole[]						triangleLeft;
	public final Hole[]						triangleRight;

	public GameStateYearly(
			@JsonProperty("_id")					ObjectId 					_gameEventId,
			@JsonProperty("lastGameEvent")			GameEvent					_lastGameEvent,
			@JsonProperty("blueScore")				int 						_blueScore,
			@JsonProperty("yellowScore")			int 						_yellowScore,
			@JsonProperty("penalties")				ArrayList<SchoolInteger>	_penalties,
			@JsonProperty("misconductPenalties")	ArrayList<School>			_misconductPenalties,
			@JsonProperty("didNotScore")			ArrayList<School>			_didNotScore,
			@JsonProperty("pointModifierBlue")		int							_pointModifierBlue,
			@JsonProperty("pointModifierYellow")	int							_pointModifierYellow,
			@JsonProperty("triangleStateLeft")		Hole[] 						_triangleLeft,
			@JsonProperty("triangleStateRight")		Hole[] 						_triangleRight
			)
	{
		super(_gameEventId, _lastGameEvent, _blueScore, _yellowScore, _penalties, _misconductPenalties, _didNotScore, _pointModifierBlue, _pointModifierYellow);
		triangleLeft		= _triangleLeft;
		triangleRight 		= _triangleRight;
	}
	
	public GameStateYearly(GameState previousState, GameEvent gameEvent)
	{
		super(previousState, gameEvent);

		triangleLeft		= GetLeftTriangle(previousState, gameEvent);
		triangleRight		= GetRightTriangle(previousState, gameEvent);
	}
	
	private Hole[] GetRightTriangle(GameState previousState, GameEvent gameEvent)
	{
		Hole[] localTriangleRight = null;
		
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			localTriangleRight = InitializeTriangle();
		}
		else
		{
			// We need to make a copy of the array so that the array is not a reference of each
			localTriangleRight = CloneTriangle(((GameStateYearly)previousState).triangleRight);

			if(gameEvent.getGameEventEnum() == GameEventEnum.SCOREBOARD_UPDATED)
			{
				ScoreboardUpdateEvent scoreboardUpdatedEvent = (ScoreboardUpdateEvent) gameEvent;
				
				localTriangleRight = scoreboardUpdatedEvent.triangleRight.clone();
			}
		}
		
		return localTriangleRight;
	}
	
	private Hole[] GetLeftTriangle(GameState previousState, GameEvent gameEvent)
	{
		Hole[] localTriangleLeft = null;

		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			localTriangleLeft = InitializeTriangle();
		}
		else
		{
			// We need to make a copy of the array so that the array is not a reference of each
			localTriangleLeft = CloneTriangle(((GameStateYearly)previousState).triangleLeft);
			
			if(gameEvent.getGameEventEnum() == GameEventEnum.SCOREBOARD_UPDATED)
			{
				ScoreboardUpdateEvent scoreboardUpdatedEvent = (ScoreboardUpdateEvent) gameEvent;
				
				localTriangleLeft = scoreboardUpdatedEvent.triangleLeft.clone();
			}
		}
		
		return localTriangleLeft;
	}
	
	// When GetBlueScore is called, the triangleLeft/triangleRight is not initialized yet.
	protected int GetBlueScore(GameState previousState, GameEvent gameEvent)
	{
		int localBlueScore = 0;

		
		if(gameEvent.getGameEventEnum() != GameEventEnum.START_GAME)
		{
			Hole[] localTriangleLeft		= GetLeftTriangle(previousState, gameEvent);
			Hole[] localTriangleRight		= GetRightTriangle(previousState, gameEvent);

			localBlueScore = GetScore(TeamEnum.BLUE, localTriangleLeft) + GetScore(TeamEnum.BLUE, localTriangleRight);
		}
		return localBlueScore + pointModifierBlue;
	}
	
	// When GetYellowScore is called, the triangleLeft/triangleRight is not initialized yet.
	protected int GetYellowScore(GameState previousState, GameEvent gameEvent)
	{
		int localYellowScore = 0;
		
		if(gameEvent.getGameEventEnum() != GameEventEnum.START_GAME)
		{
			Hole[] localTriangleLeft		= GetLeftTriangle(previousState, gameEvent);
			Hole[] localTriangleRight		= GetRightTriangle(previousState, gameEvent);

			localYellowScore = GetScore(TeamEnum.YELLOW, localTriangleLeft) + GetScore(TeamEnum.YELLOW, localTriangleRight);
		}
		
		return localYellowScore + pointModifierYellow;
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
