package com.framework.helpers;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelpersTests 
{
	@BeforeClass
	public static void setUp()
    {
    }
	
	@AfterClass
	public static void tearDown()
	{
	}
		
	@Test
	public void testParseDateTime()
	{
		DateTime dateTime = Helpers.html5DateTimePicker.parseDateTime("2016-12-01T15:34");
		Validate.isTrue(dateTime != null);
	}
}

