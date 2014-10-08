package com.framework.models;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.framework.helpers.Database;
import com.framework.helpers.LocalizedString;

public class Essentials implements java.lang.AutoCloseable
{
	public final Database database;
	public final Subject subject;
	public final HttpServletRequest request;
	public final HttpServletResponse response;
	public final ArrayList<LocalizedString> errorList;
	
	public Essentials(
				Database _database,
				Subject _subject,
				HttpServletRequest _request,
				HttpServletResponse _response,
				ArrayList<LocalizedString> _errorList
				)
	{
		database = _database;
		subject = _subject;
		request = _request;
		response = _response;
		errorList = _errorList;
	}
	
	public static Essentials createEssentials(HttpServletRequest _request, HttpServletResponse _response)
	{
		Essentials essentials = new Essentials(new Database(Database.DatabaseType.PRODUCTION), SecurityUtils.getSubject(), _request, _response, new ArrayList<LocalizedString>());
		essentials.request.setAttribute("errorList", essentials.errorList);
		return essentials;
	}

	@Override
	public void close()  
	{
		try
		{
			if(database != null)
			{
				database.close();
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
}
