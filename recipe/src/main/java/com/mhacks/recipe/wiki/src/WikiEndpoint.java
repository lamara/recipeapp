package com.mhacks.recipe.wiki.src;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.*;

/**
 * Created by Alex on 9/21/13.
 */
public class WikiEndpoint {

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

    public static String getPageText(String title, String wikiUrl) throws IOException, JSONException {
        title = URLEncoder.encode(title, "UTF-8");
        URL url = new URL(wikiUrl + "api.php?format=json&action=query&prop=revisions&titles=" + title + "&rvprop=timestamp&rvparse");
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
        String html = page.optJSONArray("revisions").optJSONObject(0).optString("timestamp");
        System.out.println(html);


        //String html = json.optJSONObject("query").optJSONObject("pages").optJSONObject
        return html;
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
