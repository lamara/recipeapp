package com.mhacks.recipe.parser.src;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Alex on 9/21/13.
 */
public abstract class Recipe {

    protected ArrayList<Ingredient> ingredients;

    protected ArrayList<String> ingredientStrings;

    protected ArrayList<String> instructions;

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public ArrayList<String> getIngredientStrings() {
        return ingredientStrings;
    }


    protected abstract ArrayList<Ingredient> createIngredientList(Document document) throws IOException;
    protected abstract ArrayList<String> createInstructionList(Document document) throws IOException;

}
