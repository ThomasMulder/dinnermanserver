package ModelTest;

import Model.RecipeIngredientSimilarityMap;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 28-3-2016.
 */
public class RecipeIngredientSimilarityMapTest {

    @Test
    /**
     * Tests the {@code add()} method of the {@code RecipeIngredientSimilarityMap} class.
     */
    public void testAdd() throws Exception {
        RecipeIngredientSimilarityMap m = new RecipeIngredientSimilarityMap();
        m.add(1);
        assertTrue(m.getRecipeIds().get(0) == 1);
        assertTrue(m.getSimilarities().get(0) == 1);
        m.add(1);
        assertTrue(m.getRecipeIds().get(0) == 1);
        assertTrue(m.getSimilarities().get(0) == 2);
    }

    @Test
    /**
     * Tests the {@code sortDescending()} method of the {@code RecipeIngredientSimilarityMap} class.
     */
    public void testSortDescending() throws Exception {
        RecipeIngredientSimilarityMap m = new RecipeIngredientSimilarityMap();
        m.add(1);
        m.add(2);
        m.add(2);
        m.sortDescending();
        assertTrue(m.getRecipeIds().get(0) == 2);
        assertTrue(m.getRecipeIds().get(1) == 1);
        assertTrue(m.getSimilarities().get(0) == 2);
        assertTrue(m.getSimilarities().get(1) == 1);
    }
}