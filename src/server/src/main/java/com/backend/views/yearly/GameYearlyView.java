package com.backend.views.yearly;

import java.util.Locale;

import com.backend.controllers.GameController;
import com.backend.models.Game;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;

public class GameYearlyView 
{
	// Called by the view
	public static String getScripts()
	{
		StringBuilder strBuilder = new StringBuilder();		
		
		strBuilder.append("$('.selectColor').change(function()");
		strBuilder.append("		{");
		strBuilder.append("			if(this.value == \"EMPTY\")");
		strBuilder.append("			{");
		strBuilder.append("				$(this).css(\"background-color\", \"white\");	");
		strBuilder.append("			}");
		strBuilder.append("			else if(this.value == \"BLUE\")");
		strBuilder.append("			{");
		strBuilder.append("				$(this).css(\"background-color\", \"lightblue\");");
		strBuilder.append("			}");
		strBuilder.append("			else if(this.value == \"YELLOW\")");
		strBuilder.append("			{");
		strBuilder.append("				$(this).css(\"background-color\", \"yellow\");");
		strBuilder.append("			}");
		strBuilder.append("		})");
		
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

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<form method=\"post\">");
		strBuilder.append("	<input type=\"hidden\" name=\"gameEvent\" value=\"" + GameEventEnum.SCOREBOARD_UPDATED.toString() + "\" />");
		strBuilder.append("	<input type=\"hidden\" name=\"id\" value=\"" + game._id + "\" />");
		strBuilder.append("	<h2>" + strScoreboardUpdate + "</h2>");

//		Hole[] triangleLeft = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).triangleLeft;
//		Hole[] triangleRight = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).triangleRight;

//		outputTriangle(triangleLeft, SideEnum.SIDE1, strBuilder);
//		outputTriangle(triangleRight, SideEnum.SIDE2, strBuilder);
		
		strBuilder.append("	<div class=\"clear\"></div>");
		strBuilder.append("	<br/>");
		strBuilder.append(GameController.outputAddAfterForView(game, currentLocale));
		strBuilder.append("	<br/>");
		strBuilder.append("	<input type=\"submit\" value=\"" + strAdd + "\" />");
		strBuilder.append("</form>");
		
		return strBuilder.toString();
	}
}
