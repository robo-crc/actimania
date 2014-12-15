package com.backend.models;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffCluster 
{
	public final ArrayList<School> schools;
	
	public PlayoffCluster(@JsonProperty("schools") 	ArrayList<School> _schools)
	{
		schools = _schools;
	}
}
