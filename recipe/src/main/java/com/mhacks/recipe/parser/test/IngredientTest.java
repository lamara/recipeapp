package com.mhacks.recipe.parser.test;

import com.mhacks.recipe.parser.src.Ingredient;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the
 *
 * @author Mogab Elleithy
 * @version 9/21/13
 */
public class IngredientTest
{
    // --- Fields ---

    Ingredient ingt;


    // --- Methods ---

    /**
     * Initialize the ingt before each test.
     */
    @Before
    public void setUp()
    {
        ingt = new Ingredient("Steak", "marinated", "ounces", 8);
    }

    /**
     * Ensure the getName() method is working properly.
     */
    @Test
    public void testGetName()
    {
        assertEquals("Steak", ingt.getName());
    }

    /**
     * Ensure the getAdj() method is working properly.
     */
    @Test
    public void testGetAdj()
    {
        assertEquals("marinated", ingt.getAdj());
    }

    /**
     * Ensure the getUnits() method is working properly.
     */
    @Test
    public void testGetUnits()
    {
        assertEquals("ounces", ingt.getUnits());
    }

    /**
     * Ensure the getQOriginal() method is working properly.
     */
    @Test
    public void testGetQOriginal()
    {
        assertEquals(8.0, ingt.getQOriginal(), 0.01);
    }

    /**
     * Ensure the getQRemaining() method is working properly.
     */
    @Test
    public void testGetQRemaining()
    {
        ingt.use(4.0);
        assertEquals(4.0, ingt.getQRemaining(), 0.01);
    }

    /**
     * Ensure the use() method is working properly.
     */
    @Test
    public void testUse()
    {
        ingt.use(10.0);
        assertEquals(8.0, ingt.getQRemaining(), 0.01);
        ingt.use(5.0);
        assertEquals(3.0, ingt.getQRemaining(), 0.01);
    }

    /**
     * Ensure the useAll() method is working properly.
     */
    @Test
    public void testUseAll()
    {
        ingt.useAll();
        assertEquals(0.0, ingt.getQRemaining(), 0.01);
    }

    @Test
    public void testIsConsumed()
    {
        assertFalse(ingt.isConsumed());
        ingt.useAll();
        assertTrue(ingt.isConsumed());
    }
}