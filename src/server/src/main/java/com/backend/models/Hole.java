package com.backend.models;

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
	
	public TriangleStateEnum Front()
	{
		return triangleStates[0];
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
