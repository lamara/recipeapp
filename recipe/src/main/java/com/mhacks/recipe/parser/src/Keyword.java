package com.mhacks.recipe.parser.src;

import com.mhacks.recipe.alchemy.api.AlchemyAPI;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by Alex on 9/21/13.
 */
public class Keyword {

    private String keyword;
    private double relevance;

    /**
     * this is a temporary api key for mhacks
     */
    private static final String API_KEY = "2094dd01fd7cbceb7e1bb916840e40e81f25d16f";

    public Keyword(String keyword, double relevance) {
        this.keyword = keyword;
        this.relevance = relevance;
    }


    public String getKeyword() {
        return keyword;
    }

    public double getRelevance() {
        return relevance;
    }

    /**
     * Returns a list of keywords associated with an inputted sentence/paragraph/whatever you want to feed into it
     * Each keyword has a relevancy associated with it.
     *
     * @param text
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws XPathExpressionException
     */
    public static Set<String> getKeywordsFromText(String text) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("2094dd01fd7cbceb7e1bb916840e40e81f25d16f");

        Document doc = alchemyObj.TextGetRankedKeywords(text);

        String[] keywords = getKeywordsFromDocument(doc);
        Set<String> set = new HashSet<String>();
        for (String str : keywords) {
            set.add(str);
        }
        return set;
    }

    private static String[] getKeywordsFromDocument(Document document) {
        NodeList words = document.getElementsByTagName("text");
        ArrayList<String> keywords = new ArrayList<String>(words.getLength());
        for (int i = 0; i < words.getLength(); i++) {
            String keyword = words.item(i).getTextContent();
            for (String innerKeyword : keyword.split(" ")) {
                if (Ingredient.UNITS.contains(innerKeyword)) {
                    //one of the keywords is a unit, so we don't want it.
                    keyword = keyword.replace(innerKeyword, "");
                }
            }
            keyword = keyword.trim();
            if (keyword.length() != 0) {
                keywords.add(keyword);
            }
        }
        return keywords.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return "Keyword: " + keyword + ", Relevance: " + relevance;
    }
}

