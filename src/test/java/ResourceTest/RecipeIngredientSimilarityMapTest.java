package ResourceTest;

import Model.RecipeIngredientSimilarityMap;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 22-3-2016.
 */
public class RecipeIngredientSimilarityMapTest {

    @Test
    public void testAdd() throws Exception {
        RecipeIngredientSimilarityMap map = new RecipeIngredientSimilarityMap();
        map.add(5);
        map.add(5);
        map.add(3);
        assertTrue(map.getSimilarities().get(0) == 2);
        assertTrue(map.getSimilarities().get(1) == 1);
        assertTrue(map.getRecipeIds().get(0) == 5);
        assertTrue(map.getRecipeIds().get(1) == 3);
    }

    @Test
    public void testSortDescending() throws Exception {
        RecipeIngredientSimilarityMap map = new RecipeIngredientSimilarityMap();
        map.add(5);
        map.add(3);
        map.add(3);
        map.add(3);
        map.add(4);
        map.add(4);
        map.sortDescending();
        assertTrue(map.getSimilarities().get(0) == 3);
        assertTrue(map.getSimilarities().get(1) == 2);
        assertTrue(map.getSimilarities().get(2) == 1);
        assertTrue(map.getRecipeIds().get(0) == 3);
        assertTrue(map.getRecipeIds().get(1) == 4);
        assertTrue(map.getRecipeIds().get(2) == 5);
    }
}