package com.backend.views.yearly;

import java.util.Locale;

import com.backend.controllers.GameController;
import com.backend.models.Game;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.yearly.AreaPoints;
import com.backend.models.yearly.Area;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;

public class GameYearlyView 
{
	// Called by the view
	public static String getScripts()
	{
		StringBuilder strBuilder = new StringBuilder();		
		
		strBuilder.append("$(document).ready(function(){");
		
		strBuilder.append(" $('.selectColor').change(function()");
        strBuilder.append("        {");
        strBuilder.append("            if(this.value == \"EMPTY\")");
        strBuilder.append("            {");
        strBuilder.append("                $(this).css(\"background-color\", \"white\");    ");
        strBuilder.append("            }");
        strBuilder.append("            else if(this.value == \"BLUE\")");
        strBuilder.append("            {");
        strBuilder.append("                $(this).css(\"background-color\", \"lightblue\");");
        strBuilder.append("            }");
        strBuilder.append("            else if(this.value == \"YELLOW\")");
        strBuilder.append("            {");
        strBuilder.append("                $(this).css(\"background-color\", \"yellow\");");
        strBuilder.append("            }");
        strBuilder.append("		});");
        strBuilder.append("});");

		return strBuilder.toString();
	}
	
	// Called by the view
	public static String getHtmlForGame(Game game, Locale currentLocale)
	{
		LocalizedString strScoreboardUpdate = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Update score board", 
				Locale.FRENCH, 	"Mettre à jour le tableau de pointage"
				), currentLocale);

		LocalizedString strAdd = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Add", 
				Locale.FRENCH, 	"Ajouter"
				), currentLocale);

		LocalizedString strMultiplier = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Multiplier", 
				Locale.FRENCH, 	"Multiplicateur"
				), currentLocale);

		LocalizedString strAllowedSpools = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "ALLOWED<br/>SPOOLS", 
				Locale.FRENCH, 	"BOBINES<br/>ALLOUÉES"
				), currentLocale);

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<form method=\"post\" class=\"playingFieldForm\">");
		strBuilder.append("	<input type=\"hidden\" name=\"gameEvent\" value=\"" + GameEventEnum.SCOREBOARD_UPDATED.toString() + "\" />");
		strBuilder.append("	<input type=\"hidden\" name=\"id\" value=\"" + game._id + "\" />");
		strBuilder.append("	<h2>" + strScoreboardUpdate + "</h2>");
		
        ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;

		strBuilder.append("	<div class=\"playingField\"></div>");
		
		OutputField(strBuilder, scoreboard.blueField, TeamEnum.YELLOW, TeamEnum.BLUE);
		OutputField(strBuilder, scoreboard.yellowField, TeamEnum.BLUE, TeamEnum.YELLOW);
	
		strBuilder.append("<div class=\"score blueDispenser1\"><input type=\"Number\" name=\"blueDispenser1\" value=\"" + scoreboard.blueDispenser1 + "\"></input></div>");
		strBuilder.append("<div class=\"score blueDispenser2\"><input type=\"Number\" name=\"blueDispenser2\" value=\"" + scoreboard.blueDispenser2 + "\"></input></div>");
		strBuilder.append("<div class=\"score yellowDispenser1\"><input type=\"Number\" name=\"yellowDispenser1\" value=\"" + scoreboard.yellowDispenser1 + "\"></input></div>");
		strBuilder.append("<div class=\"score yellowDispenser2\"><input type=\"Number\" name=\"yellowDispenser2\" value=\"" + scoreboard.yellowDispenser2 + "\"></input></div>");
		strBuilder.append("<div class=\"score blueTeamAllowedSpools\"><input type=\"Number\" name=\"blueTeamAllowedSpools\" value=\"" + scoreboard.blueTeamAllowedSpools + "\"></input></div>");
		strBuilder.append("<div class=\"score yellowTeamAllowedSpools\"><input type=\"Number\" name=\"yellowTeamAllowedSpools\" value=\"" + scoreboard.yellowTeamAllowedSpools + "\"></input></div>");
		
		strBuilder.append("<div class=\"teamMultiplier\">" + strMultiplier.toString() + " ");
		strBuilder.append("<select name=\"teamMultiplier\">");
		for(TeamEnum teamEnum : TeamEnum.values())
		{
			String isSelected = "";
			if(scoreboard.hasMultiplier.equals(teamEnum))
			{
				isSelected = " selected";
			}
			
			strBuilder.append("   <option name=\"" + teamEnum.name() + "\"" + isSelected + ">" + teamEnum.name() + "</option>");
		}
		strBuilder.append("</select></div>");

		strBuilder.append("<div class=\"allowedSpools\">" + strAllowedSpools.toString() + "</div>");

		strBuilder.append("	<br/>");
		strBuilder.append("	<div class=\"submitField\">");
		
		strBuilder.append(GameController.outputAddAfterForView(game, currentLocale));
		strBuilder.append("	<br/>");
		strBuilder.append("	<input type=\"submit\" value=\"" + strAdd + "\" />");
		strBuilder.append("</div>");
		strBuilder.append("</form>");
		
		return strBuilder.toString();
	}
	
	static private void OutputField(StringBuilder strBuilder, Area[] area, TeamEnum team, TeamEnum oppositeTeam)
	{
		AreaPoints[] areaPoints = AreaPoints.values();
		for(int i = 0; i < areaPoints.length; i++)
		{
			strBuilder.append("<div class=\"score " + areaPoints[i] + "_" + oppositeTeam.name() + "\"> <input type=\"Number\" name=\"" + areaPoints[i] + "_" + oppositeTeam.name() + "\" value=\"" + area[i].spoolCount + "\" /></div>\n");
		}
	}
}
