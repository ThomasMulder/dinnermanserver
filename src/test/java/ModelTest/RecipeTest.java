package ModelTest;

import Model.Recipe;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 28-3-2016.
 */
public class RecipeTest {

    @Test
    /**
     * Tests the {@code equals()} method of the {@code Recipe} class.
     */
    public void testEquals() throws Exception {
        Recipe r = new Recipe(1, 1, 1, 1, 1, "", "", "", 1, 1, 1, 1, "", "", "");
        Recipe q = new Recipe(1, 1, 1, 1, 1, "", "", "", 1, 1, 1, 1, "", "", "");
        Recipe s = new Recipe(1, 1, 1, 1, 1, "", "", "", 1, 2, 1, 1, "", "", "");
        assertTrue(r.equals(r));
        assertFalse(r == q);
        assertTrue(r.equals(q));
        assertFalse(r.equals(s));
    }

    @Test
    /**
     * Tests the {@code toJson()} method of the {@code Recipe} class.
     */
    public void testToJson() throws Exception {
        Recipe r = new Recipe(1, 1, 1, 1, 1, "", "", "", 1, 1, 1, 1, "", "", "");
        String expectedResult = "{\"id\":1,\"title\":\"\",\"image\":\"\",\"cuisine\":\"\",\"calories\":1,\"fat\":1," +
                "\"protein\":1,\"carbs\":1,\"servings\":1,\"preparationMinutes\":1,\"cookingMinutes\":1," +
                "\"readyInMinutes\":1,\"summary\":\"\",\"instructions\":\"\",\"ingredients\":\"\"}";
        assertTrue(r.toJson().toString().equals(expectedResult));
    }
}