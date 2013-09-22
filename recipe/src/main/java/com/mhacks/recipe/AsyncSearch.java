package com.mhacks.recipe;

import android.os.AsyncTask;

import com.mhacks.recipe.parser.src.Recipe;
import com.mhacks.recipe.wiki.src.WikiEndpoint;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Alex on 9/22/13.
 */
public class AsyncSearch extends AsyncTask<Object, Void, String[]> {

    private static final String WIKI_URL = "http://www.cookipedia.co.uk/wiki/";

    private MainActivity mainActivity;

    @Override
    protected String[] doInBackground(Object... params) {
        mainActivity = (MainActivity) params[0];
        String search = params[1].toString();

        try {
            return WikiEndpoint.search(search, WIKI_URL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        mainActivity.searchCallBack(result);
    }
}
