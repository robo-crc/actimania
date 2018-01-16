package com.backend.views.yearly;

import java.util.Locale;

import com.backend.controllers.GameController;
import com.backend.models.Game;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
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

		LocalizedString strCylinder = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Cylinder", 
				Locale.FRENCH, 	"Cylindre"
				), currentLocale);

		LocalizedString strPrism = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Prism", 
				Locale.FRENCH, 	"Prisme"
				), currentLocale);
		
		LocalizedString strThreeLevels = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Three Level", 
				Locale.FRENCH, 	"Trois niveaux"
				), currentLocale);

		LocalizedString strvShape = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "V Shape", 
				Locale.FRENCH, 	"Forme en V"
				), currentLocale);
		
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<form method=\"post\" class=\"playingFieldForm\">");
		strBuilder.append("	<input type=\"hidden\" name=\"gameEvent\" value=\"" + GameEventEnum.SCOREBOARD_UPDATED.toString() + "\" />");
		strBuilder.append("	<input type=\"hidden\" name=\"id\" value=\"" + game._id + "\" />");
		strBuilder.append("	<h2>" + strScoreboardUpdate + "</h2>");
		
        ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;
		
		strBuilder.append("<table>");
		strBuilder.append("	<tr>");
		strBuilder.append("		<th></th>");
		strBuilder.append("		<th>" + TeamEnum.BLUE + "</th>");
		strBuilder.append("		<th>" + TeamEnum.YELLOW + "</th>");
		strBuilder.append("	</tr>");
		strBuilder.append("	<tr><td>" + strCylinder + "</td>");
		strBuilder.append(" <td><input type=\"Number\" name=\"cylinderBlue\" 		   value=\"" + scoreboard.cylinderBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"cylinderYellow\" 		   value=\"" + scoreboard.cylinderYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strPrism + "</td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"prismBlue\" 			   value=\"" + scoreboard.prismBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"prismYellow\" 		   value=\"" + scoreboard.prismYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strvShape + "</td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"vShapeBlue\"             value=\"" + scoreboard.vShapeBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"vShapeYellow\"           value=\"" + scoreboard.vShapeYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strThreeLevels + "</td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"threeLevelBlue\"         value=\"" + scoreboard.threeLevelBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"threeLevelYellow\"       value=\"" + scoreboard.threeLevelYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strMultiplier + "</td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"gameMultiplierBlue\"     value=\"" + scoreboard.gameMultiplierBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Number\" name=\"gameMultiplierYellow\"   value=\"" + scoreboard.gameMultiplierYellow + "\"></input></td>");
		strBuilder.append("	</tr>");
		strBuilder.append("</table>");

		strBuilder.append(GameController.outputAddAfterForView(game, currentLocale));
		strBuilder.append("	<br/>");
		strBuilder.append("	<input type=\"submit\" value=\"" + strAdd + "\" />");
		strBuilder.append("</div>");
		strBuilder.append("</form>");

		return strBuilder.toString();
	}
}
