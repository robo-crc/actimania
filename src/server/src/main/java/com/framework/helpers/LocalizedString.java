package com.framework.helpers;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.models.Essentials;
import com.google.common.collect.ImmutableMap;

public class LocalizedString 
{
	private final Map<String, String> localizedStrings;
	@JsonIgnore
	private final Locale defaultLocale;
	
	public LocalizedString( @JsonProperty("localizedStrings") Map<String, String> _localizedStrings )
	{
		Validate.notNull(_localizedStrings);
		Validate.notEmpty(_localizedStrings);
		localizedStrings = _localizedStrings;
		defaultLocale = Locale.ENGLISH;
	}
	
	public LocalizedString( Map<Locale, String> _localizedStrings, Locale _defaultLocale )
	{
		Validate.notNull(_localizedStrings);
		Validate.notEmpty(_localizedStrings);

		localizedStrings = new TreeMap<String, String>();
		for(Entry<Locale, String> entry : _localizedStrings.entrySet())
		{
			localizedStrings.put(entry.getKey().getLanguage(), entry.getValue());
		}
		
		defaultLocale = _defaultLocale;
	}
	
	public LocalizedString( Locale _defaultLocale, String english, String french )
	{
		localizedStrings = ImmutableMap.of(
				Locale.ENGLISH.getLanguage(), english,
				Locale.FRENCH.getLanguage(), french
				);
		defaultLocale = _defaultLocale;
	}
	
	public LocalizedString( Essentials essentials, String english, String french )
	{
		localizedStrings = ImmutableMap.of(
				Locale.ENGLISH.getLanguage(), english,
				Locale.FRENCH.getLanguage(), french
				);
		defaultLocale = essentials.request.getLocale();
	}
	
	public String get(Locale locale)
	{
		String languageTag = locale.getLanguage();
		if(localizedStrings.containsKey(languageTag))
		{
			return localizedStrings.get(languageTag);
		}
		else if(localizedStrings.containsKey(defaultLocale.getLanguage()))
		{
			return localizedStrings.get(defaultLocale.getLanguage());
		}
		else if(localizedStrings.containsKey(Locale.ENGLISH.getLanguage()))
		{
			return localizedStrings.get(Locale.ENGLISH.getLanguage());
		}
		else if(!localizedStrings.isEmpty())
		{
			// Not empty, no english, no default ... fall back on first found language.
			for(Entry<String, String> entry : localizedStrings.entrySet())
			{
				return entry.getValue();
			}
		}
		// The localizedString is empty.
		return "Not localized";
	}
	
	public Set<Entry<String, String>> getAllStrings()
	{
		return localizedStrings.entrySet();
	}

	@Override 
	public String toString()
	{
		return get(defaultLocale);
	}
}
