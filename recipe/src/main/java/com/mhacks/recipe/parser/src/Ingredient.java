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

    private String name;
    private String adj;
    private String quantity;
    private boolean consumed;
    protected ArrayList<String> unitList;


    // --- Constructors ---

    /**
     * Defines the all of the properties of an ingredient.
     * @param name  The name of the ingredient.
     * @param adj  Descriptive modifiers for the ingredient.
     * @param quantity  The amount and units of the ingredient.
     */
    public Ingredient(String name, String adj, String quantity)
    {
        this.name = name;
        this.adj = adj;
        this.quantity = quantity;
        this.consumed = false;
    }

    public Ingredient()
    {

    }

    // --- Methods ---

    /**
     * @return  The name of the ingredient.
     */
    public String getName()
    {
        return this.name;
    }

    protected void setName(String name)
    {
        this.name = name;
    }

    protected abstract String getName(String input);

    /**
     * @return  The adjectives of the ingredient.
     */
    public String getAdj()
    {
        return this.adj;
    }

    protected void setAdj(String adj)
    {
        this.adj = adj;
    }

    // protected abstract String getAdj(String input);

    /**
     * @return  The units of the ingredient.
     */
    public String getQuantity()
    {
        return this.quantity;
    }

    protected void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    protected abstract String getQuantity(String input);

    /**
     * "Expends" all of the ingredient.
     */
    public void use()
    {
        this.consumed = true;
    }

    /**
     * @return   true if all of the ingredient has been used in the recipe,
     *			 false if otherwise
     */
    public boolean isConsumed()
    {
        return consumed;
    }

    protected void initUnitList()
    {
        unitList = new ArrayList<String>();
        unitList.add("ounce"); // PL
        unitList.add("oz");
        unitList.add("gram");// PL
        unitList.add("g");
        unitList.add("cup");// PL
        unitList.add("c");
        unitList.add("fl oz");
        unitList.add("fl ounce");// PL
        unitList.add("fluid oz");
        unitList.add("fluid ounce");// PL
        unitList.add("pint");// PL
        unitList.add("pt");
        unitList.add("p");
        unitList.add("milliliter"); // PL
        unitList.add("millilitre"); // PL
        unitList.add("ml");
        unitList.add("liter"); // PL
        unitList.add("litre"); // PL
        unitList.add("L");
        unitList.add("kilogram"); // PL
        unitList.add("kg");
        unitList.add("teaspoon"); // PL
        unitList.add("tsp"); // PL
        unitList.add("dessert spoon"); // PL
        unitList.add("tablespoon"); // PL
        unitList.add("tbsp"); // PL
        unitList.add("\"");
        unitList.add("inch");
        unitList.add("inches");
        unitList.add("in");
        unitList.add("centimeter"); // PL
        unitList.add("cm");
    }
}