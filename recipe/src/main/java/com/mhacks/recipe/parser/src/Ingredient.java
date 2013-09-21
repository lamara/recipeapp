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
    private String units;
    private double qOriginal; // In written units.
    private double qRemaining; // In kilograms/liters.
    private boolean consumed;


    // --- Constructors ---

    /**
     * Defines the all of the properties of an ingredient.
     * @param name  The name of the ingredient.
     * @param adj  Descriptive modifiers for the ingredient.
     * @param units  The units the ingredient is measured in.
     * @param qOriginal  The amount of ingredient there originally is for this recipe.
     */
    // &frac12 -> 1/2
    public Ingredient(String name, String adj, String units, double qOriginal)
    {
        this.name = name;
        this.adj = adj;
        this.units = units;
        this.qOriginal = qOriginal;
        this.qRemaining = qOriginal;
        this.consumed = false;
    }

    /**
     * Defines the most of the properties of an ingredient.
     * @param name  The name of the ingredient.
     * @param units  The units the ingredient is measured in.
     * @param qOriginal  The amount of ingredient there originally is for this recipe.
     */
    public Ingredient(String name, String units, double qOriginal)
    {
        this(name, "", units, qOriginal);
    }

    /**
     * Defines only the name of an ingredient.
     * @param name  The name of the ingredient.
     */
    public Ingredient(String name)
    {
        this(name, "", -1);
    }


    // --- Methods ---

    /**
     * @return  The name of the ingredient.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return  The adjectives of the ingredient.
     */
    public String getAdj()
    {
        return this.adj;
    }

    /**
     * @return  The units of the ingredient.
     */
    public String getUnits()
    {
        return this.units;
    }

    /**
     * @return  The original quantity of the ingredient.
     */
    public double getQOriginal()
    {
        return this.qOriginal;
    }

    /**
     * @return  The remianing quantity of the ingredient.
     */
    public double getQRemaining()
    {
        return this.qRemaining;
    }

    /**
     * "Expends" a certain amount of the ingredient
     * @param qUsed  The quantity of the ingredient to be used.
     */
    public void use(double qUsed)
    {
        if (qRemaining - qUsed < 0 && qRemaining != -1)
        {
            // TODO handle error.
        }
        else
        {
            qRemaining -= qUsed;
            if (qRemaining == 0)
                this.setConsumedTrue();
        }
    }

    /**
     * "Expends" all of the ingredient.
     */
    public void useAll()
    {
        qRemaining = 0;
        this.setConsumedTrue();
    }

    /**
     * @return   true if all of the ingredient has been used in the recipe,
     *			 false if otherwise
     */
    public boolean isConsumed()
    {
        return consumed;
    }

    /**
     * Sets consumed to true. Called from other methods
     * in which the ingredient may have been consumed.
     */
    private void setConsumedTrue()
    {
        consumed = true;
    }
}