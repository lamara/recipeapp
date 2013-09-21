package com.mhacks.recipe.parser.src;

import java.util.*;


/**
 * Hold important information about recipe ingredients,
 * like its name, quantity, and units of that quantity.
 *
 * @author Mogab Elleithy
 * @version 9/21/13
 */

public abstract class Ingredient
{
    // --- Fields ---

    protected String ingredient;
    protected String quantity;



    protected static final Set<String> UNITS = initUnits();
    // --- Methods ---

    /**
     * @return  The name of the ingredient.
     */
    public String getIngredient()
    {
        return this.ingredient;
    }

    // protected abstract String getAdj(String input);

    /**
     * @return  The units of the ingredient.
     */
    public String getQuantity()
    {
        return this.quantity;
    }

    protected static Set<String> initUnits()
    {
        Set<String> set = new HashSet<String>();

        set.add("ounce"); // PL
        set.add("oz");
        set.add("gram");// PL
        set.add("g");
        set.add("cup");// PL
        set.add("c");
        set.add("fl oz");
        set.add("fl ounce");// PL
        set.add("fluid oz");
        set.add("fluid ounce");// PL
        set.add("pint");// PL
        set.add("pt");
        set.add("p");
        set.add("milliliter"); // PL
        set.add("millilitre"); // PL
        set.add("ml");
        set.add("liter"); // PL
        set.add("litre"); // PL
        set.add("L");
        set.add("kilogram"); // PL
        set.add("kg");
        set.add("teaspoon"); // PL
        set.add("tsp"); // PL
        set.add("dessert spoon"); // PL
        set.add("tablespoon"); // PL
        set.add("tbsp"); // PL
        set.add("\"");
        set.add("inch");
        set.add("inches");
        set.add("in");
        set.add("centimeter"); // PL
        set.add("cm");

        return set;
    }
}