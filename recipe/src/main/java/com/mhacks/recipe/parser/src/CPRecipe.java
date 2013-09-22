package com.mhacks.recipe.parser.src;

import com.mhacks.recipe.wiki.src.WikiEndpoint;

import org.json.JSONException;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by Alex on 9/21/13.
 */
public class CPRecipe extends Recipe {

    private static final String WIKI_URL = "http://www.cookipedia.co.uk/wiki/";


    private String title;

    /**
     * Creates a recipe object given the title of a wiki page on cookipedia
     * @param title
     */
    public CPRecipe(String title) throws IOException, JSONException {
        this.title = title;
        Document document = WikiEndpoint.getPageHtml(title, WIKI_URL);
        this.ingredients = createIngredientList(document);
        this.instructions = createInstructionList(document);

        System.out.println("======================INGREDIENTS==========================");
        for (Ingredient ingredient : ingredients) {
            System.out.println("Ingredient: " + ingredient);
        }
        System.out.println("=======================INSTRUCTIONS========================");
        for (String string : instructions) {
            System.out.println(string);
        }
    }


    @Override
    protected ArrayList<Ingredient> createIngredientList(Document document) {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ArrayList<String> ingredientLines = WikiEndpoint.getIngredientList(document);
        this.ingredientStrings = ingredientLines; //these are used in the 1st section of the app.
        for (String line : ingredientLines) {
            try {
                ingredients.add(new CPIngredient(line));
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ingredients;
    }

    @Override
    protected ArrayList<String> createInstructionList(Document document) {
        ArrayList<String> instructions = WikiEndpoint.getInstructionList(document);
        //sort ingredient list
        Collections.sort(ingredients);

        //the idea here is to go into the main body of the recipe list and insert quantities that
        //would not otherwise be there.
        //also we are going to go do an n^2 algorithm and its going to be awesome
        int i = 0;
        for (String instruction : instructions) {
            for (Ingredient element : ingredients) {
                String ingredient = element.getIngredient();
                int index = instruction.indexOf(ingredient);
                if (index != -1) {
                    System.out.println("Found a match! With ingredient " + ingredient + " at index " + i);
                    String firstHalf = instruction.substring(0, index);
                    String secondHalf = instruction.substring(index, instruction.length());
                    String verboseInstruction = firstHalf + element.getQuantity() + " " + secondHalf;
                    instructions.set(i, verboseInstruction);
                }
            }
            i++;
        }
        return instructions;
    }
}
