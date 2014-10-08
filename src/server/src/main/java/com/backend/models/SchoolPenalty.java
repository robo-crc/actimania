package com.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolPenalty 
{
	public final School	 	school;
	public final int 		pointsDeduction;
	public final boolean 	isMisconductPenalty;
	
	public SchoolPenalty(
			@JsonProperty("school") 				School	 	_school, 
			@JsonProperty("pointsDeduction") 		int 		_pointsDeduction, 
			@JsonProperty("isMisconductPenalty") 	boolean 	_isMisconductPenalty
			)
	{
		school 				= _school;
		pointsDeduction 	= _pointsDeduction;
		isMisconductPenalty = _isMisconductPenalty;
	}
}
