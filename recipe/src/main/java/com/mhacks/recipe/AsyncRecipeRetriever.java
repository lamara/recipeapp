package com.mhacks.recipe;

import android.os.AsyncTask;

import com.mhacks.recipe.parser.src.CPRecipe;
import com.mhacks.recipe.parser.src.Recipe;

import org.json.JSONException;

import java.io.IOException;

/**
 * Params for execute are (MainActivity mainActivity, String recipeTitle).
 * MainActiviy is used to call back the retrieved recipe.
 *
 * Created by Alex on 9/22/13.
 */
public class AsyncRecipeRetriever extends AsyncTask<Object, Void, Recipe> {

    private InstructionActivity instructionActivity;

    @Override
    protected Recipe doInBackground(Object... params) {
        instructionActivity = (InstructionActivity) params[0];
        String title = (String) params[1];

        Recipe recipe = null;
        try {
            recipe = new CPRecipe(title);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe result) {
        instructionActivity.recipeCallBack(result);
    }

}
