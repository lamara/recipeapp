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
        System.out.println(WikiEndpoint.getPageText("egg fried rice", "http://www.cookipedia.co.uk/wiki/"));
    }

    @Test
    public void testSearch() throws IOException, JSONException {
        String search = "fried rice";
        String wikiUrl = "http://www.cookipedia.co.uk/wiki/";
        WikiEndpoint.search(search, wikiUrl);
    }
}
