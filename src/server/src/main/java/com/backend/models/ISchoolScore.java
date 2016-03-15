package com.backend.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface ISchoolScore 
{
	public double getPercentage(ISchoolScore best);
	public String getDisplay();
	public String getDisplayLong();
}
