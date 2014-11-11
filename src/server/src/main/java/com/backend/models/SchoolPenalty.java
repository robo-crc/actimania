package com.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolPenalty 
{
	public final School	school;
	public final int 	pointsDeduction;

	public SchoolPenalty(
			@JsonProperty("school") 			School 	_school, 
			@JsonProperty("pointsDeduction") 	int 	_pointsDeduction
			)
	{
		school 			= _school;
		pointsDeduction = _pointsDeduction;
	}
}
