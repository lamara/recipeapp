package com.mhacks.recipe.wiki.test;

import com.mhacks.recipe.wiki.src.WikiEndpoint;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Alex on 9/21/13.
 */
public class WikiEndpointTest {

    @Test
    public void testSanitizeInput() {
        String sanitized = WikiEndpoint.sanitizeInput("query=\"eggs\"");
        assertEquals(sanitized, "query  eggs ");
    }


    @Test
    public void testGetPageText() throws IOException, JSONException {
        System.out.println(WikiEndpoint.getPageHtml("Plain_buttered_couscous", "http://www.cookipedia.co.uk/wiki/"));
    }

    @Test
    public void testGetIngredients() throws IOException, JSONException {
        WikiEndpoint.getIngredientList(WikiEndpoint.getPageHtml("Plain buttered couscous", "http://www.cookipedia.co.uk/wiki/"));
    }

    @Test
    public void testGetMiseEnPlaceInstructions() throws IOException, JSONException {
        WikiEndpoint.getMiseEnPlaceInstructions(WikiEndpoint.getPageHtml("Leek_and_mince_casserole", "http://www.cookipedia.co.uk/wiki/"));
    }

    @Test
    public void testGetInstructionList() throws IOException, JSONException {
        WikiEndpoint.getInstructionList(WikiEndpoint.getPageHtml("Leek_and_mince_casserole", "http://www.cookipedia.co.uk/wiki/"));
    }

    @Test
    public void testSearch() throws IOException, JSONException {
        String search = "fried rice";
        String wikiUrl = "http://www.cookipedia.co.uk/wiki/";
        WikiEndpoint.search(search, wikiUrl);
    }
}
