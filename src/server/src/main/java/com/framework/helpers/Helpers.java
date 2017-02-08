package com.framework.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.framework.models.Essentials;
import com.framework.models.User;
import com.google.common.collect.ImmutableMap;

public class Helpers 
{
	public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT).withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("EST")));
	public static final DateTimeFormatter hourTimeFormatter = DateTimeFormat.forPattern("HH:mm").withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("EST")));
	public static final DateTimeFormatter html5DateTimePicker = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm").withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("EST")));
	public static final PeriodFormatter stopwatchFormatter = new PeriodFormatterBuilder()
	.printZeroAlways()
	.appendMinutes()
	.appendSuffix(":")
	.minimumPrintedDigits(2)
	.appendSeconds()
	.appendSuffix(".")
	.appendMillis()
	.toFormatter();
	
	public static final PeriodFormatter stopwatchFormatterFull = new PeriodFormatterBuilder()
			.printZeroAlways()
			.minimumPrintedDigits(2)
			.appendMinutes()
			.appendSuffix(":")
			.minimumPrintedDigits(2)
			.appendSeconds()
			.appendSuffix(".")
			.appendMillis()
			.toFormatter();
	
	public static <T> T getParameter(String parameter, Class<T> entityType, Essentials global)
	{
		return getParameter(parameter, entityType, global.request, global.errorList);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getParameter(String parameter, Class<T> entityType, HttpServletRequest request, ArrayList<LocalizedString> errorList)
	{
		try
		{
			String safeParam = StringEscapeUtils.escapeHtml(request.getParameter(parameter));
			
			if(entityType == DateTime.class)
			{
				DateTime dateTime = html5DateTimePicker.parseDateTime(safeParam);
				
				return (T)dateTime;
			}
			else if(entityType == ObjectId.class)
			{
				return (T)new ObjectId(safeParam);
			}
			else if(entityType == Integer.class)
			{
				return (T)Integer.valueOf(safeParam);
			}
			else if(entityType == Boolean.class)
			{
				return (T)Boolean.valueOf(safeParam);
			}
			else if(entityType == Float.class)
			{
				return (T)Float.valueOf(safeParam);
			}
			else
			{
				T applicationSpecific = ApplicationSpecific.getParameter(safeParam, entityType);
				
				if(applicationSpecific != null)
				{
					return applicationSpecific;
				}
				else
				{
					return (T)safeParam;
				}
			}
		}
		catch(Exception e)
		{
			errorList.add( 
					new LocalizedString(ImmutableMap.of( 	
							Locale.ENGLISH, "Error in parameter " + parameter, 
							Locale.FRENCH, 	"Erreur dans le paramètre " + parameter
							), Helpers.getCurrentLocale()));
		}
		
		return null;
	}
	
	private static final char[] tableOfChar = 
	{
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'!', '/', '$', '%', '?', '&', '*', '(', ')'
	};
	public static String randomString(int characterCount)
	{
		String str = "";
		for(int i = 0; i < characterCount; i++)
		{
			str += tableOfChar[(int)(Math.random() * (tableOfChar.length - 1))];
		}
		
		return str;
	}
	
	public static ObjectId getClientId()
	{
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
		return user._id;
	}
	
	public static User getCurrentUser()
	{
		return (User)SecurityUtils.getSubject().getSession().getAttribute("user");
	}
	
	public static Locale getCurrentLocale()
	{
		return Locale.ENGLISH;
	}
	
	public static <T> String getClassName(Class<T> entityType)
	{
		return entityType.getSimpleName().toLowerCase();
	}
	
	public static <T> boolean checkWritePermission(Essentials essentials, Class<T> entityType)
	{
		return checkPermission(essentials, getClassName(entityType) + ":write");
	}
	
	public static <T> boolean checkReadPermission(Essentials essentials, Class<T> entityType)
	{
		return checkPermission(essentials, getClassName(entityType) + ":read");
	}
	
	public static <T> boolean checkPermission(Essentials essentials, String permission)
	{
		try
		{
			essentials.subject.checkPermission(permission);
			return true;
		}
		catch(UnauthorizedException ex)
		{
			essentials.errorList.add( new LocalizedString( essentials,
					"You don't have the permission to access this page.", 
					"Vous n'avez pas les autorisations nécessaires pour accéder cette page") );
			
			try
			{
				essentials.request.setAttribute("errorList", essentials.errorList);
				essentials.request.getRequestDispatcher("/error").forward(essentials.request, essentials.response);
			}
			catch(IOException io)
			{
			} catch (ServletException e) {
			}
		}
		
		return false;
	}
}
