package com.mhacks.recipe.parser.test;

import org.json.JSONException;
import org.junit.Test;
import com.mhacks.recipe.parser.src.CPRecipe;

import java.io.IOException;

/**
 * Created by Alex on 9/21/13.
 */
public class CPRecipeTest {

    @Test
    public void testConstructor() throws IOException, JSONException {
        new CPRecipe("Escalopes of lamb with garlic and mustard");
    }
}
