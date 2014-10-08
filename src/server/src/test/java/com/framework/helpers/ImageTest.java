package com.framework.helpers;

import java.io.File;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.framework.helpers.Image;

public class ImageTest 
{
	static final File outputFile = new File("..\\..\\tmp\\images\\test.jpg");
	static final File backgroundFile = new File("src\\main\\webapp\\images\\promotions\\background\\4.jpg");
	
	@BeforeClass
	public static void setUp()
    {
		if(outputFile.exists())
		{
			outputFile.delete();
		}
    }
	
	@AfterClass
	public static void tearDown()
	{
	}
	
	@Test
	public void testCreateMeme()
	{
		Validate.isTrue(backgroundFile.exists());
		String text = "This is a test sir!";
		Image.createMeme(backgroundFile, text, outputFile);
		
		// Just make sure there's a file created, which means something did output.
		Validate.isTrue(outputFile.exists());
	}
}
