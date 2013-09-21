package com.mhacks.recipe.parser.src;

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
    public CPIngredient(String bullet)
    {
        this.setName(this.getName(bullet));
        //this.setAdj(this.getAdj(bullet));
        this.setQuantity(this.getQuantity(bullet));
    }

    // --- Methods ---
    protected String getQuantity(String input)
    {
        String thisUnit = "";
        this.initUnitList();
        for (String unit : unitList)
            if (input.contains(unit) || input.contains(unit+"s"))
            {
                thisUnit = unit;
                break;
            }
        if (thisUnit == "")
            return "";
        int pos = input.toLowerCase().indexOf(thisUnit);
        return input.substring(0, pos + thisUnit.length());
    }

    protected String getName(String input)
    {
        if (input.contains("^"))
        {
            return input.substring(input.indexOf("^")+1, input.lastIndexOf("^"));
        }
        else
        {
            // TODO handle caret-less situation
            return "";
        }
    }

    //protected String getAdj(String input);
}
