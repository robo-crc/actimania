package com.backend.models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.models.Essentials;

public class LiveRefresh 
{
	public final ObjectId 	_id;
	public final Boolean 	isLiveRefreshOn;
	
	public LiveRefresh(
			@JsonProperty("_id") 				ObjectId	id,
			@JsonProperty("isLiveRefreshOn") 	Boolean 	_isLiveRefreshOn)
			{
				_id = id;
				isLiveRefreshOn = _isLiveRefreshOn;
			}
	
	public static LiveRefresh get(Essentials essentials)
	{
		return essentials.database.findOne(LiveRefresh.class, "{ }");
	}
	
	public static boolean liveRefreshAvailable(Essentials essentials)
	{
		if (essentials.subject.isAuthenticated()) 
		{
			return true;
		}
		else
		{
			return get(essentials).isLiveRefreshOn;
		}
	}
}
