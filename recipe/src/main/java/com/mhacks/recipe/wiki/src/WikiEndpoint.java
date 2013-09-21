package com.mhacks.recipe.wiki.src;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Alex on 9/21/13.
 */
public class WikiEndpoint {

    private static final String INGREDIENT_HEADER = "Ingredients";
    private static final String MISE_EN_PLACE_HEADER = "Mise_en_place";
    private static final String METHOD_HEADER = "Method";

    private static final String INGREDIENT_TAG = "ingredients";
    private static final String INSTRUCTION_TAG = "recipeInstructions";

    public static void main(String[] args) {
        System.out.println("hey");
    }
    /**
     * Searches
     * @param search keyword(s) to search.
     * @param wikiUrl the URL to the wiki api endpoint
     * @return a list of all of the titles relevant to the search
     */
    public static String[] search(String search, String wikiUrl) throws IOException, JSONException {
        //TODO decide whether to normalize input (atm unicode characters will just get dropped), an issue
        //because some unicode doesn't normalize that well
        search = sanitizeInput(search);
        search = URLEncoder.encode(search, "UTF-8");

        URL url = new URL(wikiUrl + "api.php?action=query&list=search&srsearch=" + search + "&srprop=timestamp&format=json");
        URLConnection connection = url.openConnection();

        String json = readFromInput(connection.getInputStream());
        JSONObject jsonObject = new JSONObject(json);

        JSONArray results = jsonObject.optJSONObject("query").optJSONArray("search");

        if (results == null) {
            return new String[0];
        }

        String[] titles = new String[results.length()];
        for (int i = 0; i < results.length(); i++) {
            JSONObject element = results.optJSONObject(i);
            titles[i] = element.optString("title");
        }

        return titles;
    }

    public static Document getPageHtml(String title, String wikiUrl) throws IOException, JSONException {
        title = URLEncoder.encode(title, "UTF-8");
        URL url = new URL(wikiUrl + "api.php?format=json&action=query&prop=revisions&titles=" + title + "&rvprop=content&rvparse&redirects");
        URLConnection connection = url.openConnection();

        JSONObject json = new JSONObject(readFromInput(connection.getInputStream()));

        JSONObject pages = json.optJSONObject("query").optJSONObject("pages");

        System.out.println(pages);

        Iterator keys = pages.keys();

        //we only want the first entry under pages.
        JSONObject page;
        if (keys.hasNext()) {
            page = pages.optJSONObject(keys.next().toString());
        }
        else {
            page = null;
            //TODO handle page not being found
        }
        String html = page.optJSONArray("revisions").optJSONObject(0).optString("*");

        Document document = Jsoup.parse(html);
        System.out.println(document);

        return document;
    }


    public static ArrayList<String> getIngredientList(Document document) {

        //The html structure is a little wonky but the gist of it is that we are going to capture
        //all of the text bodies in between the "ingredients" and "mis en place" or "method" headers,
        //depending on whether or not mis en place exists in the recipe
        Elements ingredients = document.select("span[itemprop$=" + INGREDIENT_TAG + "]");
        System.out.println("size: " + ingredients.size());


        ArrayList<String> results = new ArrayList<String>(ingredients.size());
        for (Element element : ingredients) {
            //we are making the assumption that every ingredient has a hyperlink associated with it
            //(this is true like 95% of the time)
            String line = element.text();
            String ingredient = element.select("a[href]").text();
            if (ingredient != null && ingredient.length() > 0) {
                //denoting the ingredient with the ^ character for later parsing.
                line = line.replace(ingredient, "^" + ingredient + "^");
            }

            results.add(line);
            System.out.println(line);
        }


        return results;

    }

    public static ArrayList<String> getInstructionList(Document document) {
        ArrayList<String> results = getMiseEnPlaceInstructions(document);


        Elements ingredients = document.select("span[itemprop$=" + INSTRUCTION_TAG + "]");
        //sometimes recipes have multiple sub recipes, but that's hard to parse so lets only take the
        //first recipe in the document. This can horribly break though on some recipe types.
        /*
        Element first = ingredients.first();
        System.out.println("First: " + first);
        System.out.println("Parent: " + first.parent());
        ingredients = first.parent().siblingElements();
        ingredients.add(0, first); //siblings don't include the original element
        */

        for (Element element : ingredients) {
            results.add(element.text());
            System.out.println(element.text());
        }
        return results;
    }

    public static ArrayList<String> getMiseEnPlaceInstructions(Document document) {
        //we have to parse these the old fashioned way because cookipedia's document heirarchy is awful
        String html = document.toString();
        String[] lines = html.split("\n");
        int miseEnPlaceIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.contains(MISE_EN_PLACE_HEADER)) {
                miseEnPlaceIndex = i;
                break;
            }
        }
        if (miseEnPlaceIndex == -1) {
            return new ArrayList<String>();
        }

        ArrayList<String> results = new ArrayList<String>();
        String line = lines[miseEnPlaceIndex];
        while (!line.contains(METHOD_HEADER) && miseEnPlaceIndex < lines.length) {
            if (line.contains("<li>")) {
                String instruction = Jsoup.parse(line).text();
                results.add(instruction);
                System.out.println(instruction);
            }
            miseEnPlaceIndex++;
            line = lines[miseEnPlaceIndex];
        }
        return results;
    }

    /**
     * get all of the links from a document page
     * @return
     */
    public static String[] getLinks() {
        return null;
    }

    /**
     * Makes sure input for api calls are alphanumeric
     */
    public static String sanitizeInput(String input) {
        //Normalizer will take out any unicode characters.
        //String normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFC);
        String sanitized = input.replaceAll("[^A-Za-z0-9]", " ");
        return sanitized;
    }

    private static String readFromInput(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));

        String inputLine;
        StringBuilder strBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            strBuilder.append(inputLine);
        in.close();

        return strBuilder.toString();
    }
}
