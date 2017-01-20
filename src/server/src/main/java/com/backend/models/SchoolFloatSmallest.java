package com.backend.models;

import org.bson.types.ObjectId;

import com.backend.models.enums.Division;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolFloatSmallest extends School implements ISchoolScore
{
	public final Float 	floatSmallest;
	
	// SchoolFloatSmallest is for situation where a smaller float is better.
	public SchoolFloatSmallest(
			@JsonProperty("_id") 			ObjectId 	_schoolId,
			@JsonProperty("name") 			String 		_name,
			@JsonProperty("floatSmallest") 	Float 		_floatSmallest
			)
	{
		super(_schoolId, _name);
		floatSmallest = _floatSmallest;
	}
	
	public SchoolFloatSmallest(School school, Float _floatSmallest)
	{
		super(school._id, school.name);
		floatSmallest = _floatSmallest;
	}
	
	public double getPercentage(ISchoolScore _best)
	{
		SchoolFloatSmallest best = (SchoolFloatSmallest)_best;
		// Don't divide by 0
		if(best.floatSmallest.intValue() == 0)
			return 0;
			
		return (best.floatSmallest.doubleValue() / floatSmallest.doubleValue());
	}
	
	public String getDisplay()
	{
		if(floatSmallest == Float.MAX_VALUE)
		{
			return "-";
		}
		else
		{
			return floatSmallest.toString();
		}
	}
	
	public String getDisplayLong()
	{
		if(floatSmallest == Float.MAX_VALUE)
		{
			return "-";
		}
		else
		{
			return floatSmallest.toString();
		}
	}
}
