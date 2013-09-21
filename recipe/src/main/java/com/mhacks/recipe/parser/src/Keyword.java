package com.mhacks.recipe.parser.src;

import com.mhacks.recipe.alchemy.api.AlchemyAPI;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

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
    public static Keyword[] getKeywordsFromText(String text) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("2094dd01fd7cbceb7e1bb916840e40e81f25d16f");

        Document doc = alchemyObj.TextGetRankedKeywords(text);

        return getKeywordsFromDocument(doc);
    }

    private static Keyword[] getKeywordsFromDocument(Document document) {
        NodeList words = document.getElementsByTagName("text");
        NodeList relevances = document.getElementsByTagName("relevance");
        Keyword[] keywords = new Keyword[words.getLength()];
        for (int i = 0; i < words.getLength(); i++) {
            String word = words.item(i).getTextContent();
            String relevance = relevances.item(i).getTextContent();
            Keyword keyword = new Keyword(word, Double.parseDouble(relevance));
            keywords[i] = keyword;
        }

        return keywords;
    }

    @Override
    public String toString() {
        return "Keyword: " + keyword + ", Relevance: " + relevance;
    }
}

