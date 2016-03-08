package com.backend.models.yearly;

import com.backend.models.enums.TriangleStateEnum;

public class Hole 
{
	public final int MAX_UNIT_IN_HOLE = 5;
	public final TriangleStateEnum[] triangleStates;
	
	public Hole()
	{
		triangleStates = new TriangleStateEnum[MAX_UNIT_IN_HOLE];
		for(int i = 0; i < triangleStates.length; i++)
		{
			triangleStates[i] = TriangleStateEnum.EMPTY;
		}
	}
	
	public Hole(Hole holeToClone)
	{
		triangleStates = new TriangleStateEnum[holeToClone.triangleStates.length];

		for(int currentState = 0 ; currentState < holeToClone.triangleStates.length; currentState++)
		{
			triangleStates[currentState] = holeToClone.triangleStates[currentState];
		}
	}
	
	public TriangleStateEnum Front()
	{
		for(int i = 0; i < triangleStates.length; i++)
		{
			if(triangleStates[i] != TriangleStateEnum.EMPTY)
			{
				return triangleStates[i];
			}
		}
		return TriangleStateEnum.EMPTY;
	}
	
	public int GetNumberOf(TriangleStateEnum triangleState)
	{
		int number = 0;
		for(int i = 0; i < triangleStates.length; i++)
		{
			if(triangleStates[i] == triangleState)
			{
				number++;
			}
		}
		
		return number;
	}
}
