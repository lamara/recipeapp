package com.mhacks.recipe.parser.test;

import com.mhacks.recipe.parser.src.CPIngredient;
import com.mhacks.recipe.wiki.src.WikiEndpoint;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import static org.junit.Assert.*;

/**
 * Hold important information about recipe ingredients,
 * like its name, quantity, and units of that quantity.
 *
 * @author Mogab Elleithy
 * @version 9/21/13
 */
public class CPIngredientTest {

    // --- Fields --
    ArrayList<String> currentIngtList;
    CPIngredient currentIngt;

    public void setUp1() throws IOException, JSONException
    {
        currentIngtList = WikiEndpoint.getIngredientList(WikiEndpoint.getPageHtml(
                "Noodles with ginger and spring onions Accompaniments",
                "http://www.cookipedia.co.uk/wiki/"));
    }

    @Test
    public void test1A() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(0));
        assertEquals("900 ml", currentIngt.getQuantity());
        assertEquals("chicken stock", currentIngt.getIngredient());
    }

    @Test
    public void test1B() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(1));
        assertEquals("1 tablespoon", currentIngt.getQuantity());
        assertEquals("groundnut oil", currentIngt.getIngredient());
    }

    @Test
    public void test1C() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(2));
        assertEquals("225 g (8oz)", currentIngt.getQuantity());
        assertEquals("dried egg noodles", currentIngt.getIngredient());
    }

    @Test
    public void test1D() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(3));
        assertEquals("1/2 teaspoon", currentIngt.getQuantity());
        assertEquals("sesame oil", currentIngt.getIngredient());
    }

    @Test
    public void test1E() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(4));
        assertEquals("4", currentIngt.getQuantity());
        assertEquals("spring onions", currentIngt.getIngredient());
    }

    @Test
    public void test1F() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(5));
        assertEquals("2 slices", currentIngt.getQuantity());
        assertEquals("ginger", currentIngt.getIngredient());
    }

    @Test
    public void test1G() throws IOException, JSONException, SAXException,
            ParserConfigurationException, XPathException
    {
        this.setUp1();
        currentIngt = new CPIngredient(currentIngtList.get(6));
        assertEquals("1 tablespoon", currentIngt.getQuantity());
        assertEquals("oyster sauce", currentIngt.getIngredient());
    }

    @Test
    public void testExtractQuantity() {
        String string = "3 tablespoons ^vegetable oil^";
        System.out.println("parsed: " + CPIngredient.getQuantity(string, 2));
        assertEquals(CPIngredient.getQuantity(string, 2), "3 tablespoons");

    }

    @Test
    public void testExtractQuantity2() {
        String string = "around four tablespoons of ^vegetable oil^";
        System.out.println("parsed: " + CPIngredient.getQuantity(string, 4));
        assertEquals(CPIngredient.getQuantity(string, 4), "four tablespoons of");
    }

    @Test
    public void testExtractQuantity3() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException{
        String string = "a clove of ^garlic^";
        System.out.println("parsed: " + CPIngredient.getQuantity(string, 3));
        assertEquals(CPIngredient.getQuantity(string, 3), "clove of");
    }

    @Test
    public void testExtractQuantity4() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException{
        String string = "1 medium ^onion^ ";
        System.out.println("parsed: " + CPIngredient.getQuantity(string, 2));
        assertEquals(CPIngredient.getQuantity(string, 2), "1 medium");
    }

    @Test
    public void testExtractQuantity5() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "a cup of tea";
        System.out.println("parsed: " + CPIngredient.getQuantity(string, 3));
        assertEquals(CPIngredient.getQuantity(string, 3), "cup of");
    }

    @Test
    public void testGetIngredient() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "3 tablespoons ^vegetable oil^";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "vegetable oil");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(2));
    }

    @Test
    public void testGetIngredient2() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "around four tablespoons of ^vegetable oil^";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "vegetable oil");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(4));
    }

    @Test
    public void testGetIngredient3() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "a clove of ^garlic^";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "garlic");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(3));
    }

    @Test
    public void testGetIngredient4() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "1 medium ^onion^";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "onion");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(2));

    }

    @Test
    public void testGetIngredient5() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "a cup of ^steaming hot tea^";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "steaming hot tea");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(3));
    }

    @Test
    public void testGetIngredientNoCarat() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "3 tablespoons vegetable oil";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "vegetable oil");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(2));
    }

    @Test
    public void testGetIngredientNoCarat2() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "around four tablespoons of vegetable oil";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "vegetable oil");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(4));
    }

    @Test
    public void testGetIngredientNoCarat3() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "a clove of garlic";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(-1));
    }

    @Test
    public void testGetIngredientNoCarat4() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "1 medium onion";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "medium onion");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(1));

    }

    @Test
    public void testGetIngredientNoCarat5() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String string = "a cup of steaming hot tea";
        System.out.println("parsed: " + CPIngredient.getIngredient(string).getKey());
        assertEquals(CPIngredient.getIngredient(string).getKey(), "hot tea");
        assertEquals(CPIngredient.getIngredient(string).getValue(), new Integer(4));
    }
}
