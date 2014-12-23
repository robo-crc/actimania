package com.backend.models;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffGroup 
{
	public final ArrayList<School> schools;
	
	public PlayoffGroup(@JsonProperty("schools") 	ArrayList<School> _schools)
	{
		schools = _schools;
	}
}
