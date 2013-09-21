package com.mhacks.recipe.parser.src;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Hold important information about recipe ingredients,
 * like its name, quantity, and units of that quantity.
 *
 * @author Mogab Elleithy
 * @version 9/21/13
 */

public class CPIngredient extends Ingredient
{
    // --- Constructors ---

    /**
     * Constructs an ingredient from a single ingredient line
     * @param line
     */
    public CPIngredient(String line) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        Map.Entry<String, Integer> ingredientAndIndex = getIngredient(line);
        this.ingredient = ingredientAndIndex.getKey();
        int indexOfIngredient = ingredientAndIndex.getValue();
        this.quantity = getQuantity(line, indexOfIngredient);
    }

    /**
     * returns the ingredient and its position inside of the string
     * @param input
     * @return
     */
    public static Map.Entry<String, Integer> getIngredient(String input) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        //check to see if the ingredient has been previously marked, if not we rely on AlchemistAPIs keyword search
        if (input.contains("^"))
        {
            Integer index = -1;
            boolean capturingIngredient = false;

            String ingredient = "";
            String[] strings = input.split(" ");
            //carats preceed and end the ingredient, for ex. "2 loaves of ^italian bread^"
            for (int i = 0; i < strings.length; i++) {
                String word = strings[i];
                if (word.endsWith("^")) {
                    if (index == -1) {
                        //if index hasn't been set yet then set it
                        index = i;
                    }
                    ingredient += word + " ";
                    break;
                }
                else if (capturingIngredient) {
                    ingredient += word + " ";
                }
                else if (word.startsWith("^")) {
                    index = i;
                    capturingIngredient = true;
                    ingredient += word + " ";
                }

            }
            return new AbstractMap.SimpleEntry<String, Integer>(ingredient.trim().replaceAll("\\^", ""), index);
        }
        else
        {
            Set<String> keys = Keyword.getKeywordsFromText(input);
            return null;
        }
    }

    //protected String getAdj(String input);

    // --- Methods ---
    public static String getQuantity(String input, int indexOfIngredientOnLine)
    {
        String quantity = "";


        String[] delimtedInput = input.split(" ");

        boolean capturingQuantity = false;
        for (int i = 0; i < delimtedInput.length; i++) {
            String word = delimtedInput[i];

            if (i == indexOfIngredientOnLine) {
                break;
            }
            else if (capturingQuantity || UNITS.contains(word)) {
                quantity += word + " ";
                capturingQuantity = true;
            }
            else if (isNumeric(word)) {
                quantity += word + " ";
                capturingQuantity = true;
            }
        }

        return quantity.trim();
    }

    private static boolean isNumeric(String string) {
        string = string.toLowerCase();
        //&frac12
        //test to see if string is a markup fraction
        if (string.startsWith("&") && string.contains("frac") && string.length() == 7) {
            return true;
        }
        else if (string.equals("one") || string.equals("two") || string.equals("three") || string.equals("four")
                || string.equals("five") || string.equals("six") || string.equals("seven") || string.equals("eight")
                || string.equals("nine") || string.equals("ten") || string.equals("eleven") || string.equals("twelve")) {
            return true;
        }
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
