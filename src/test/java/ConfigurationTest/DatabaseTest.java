package ConfigurationTest;

import Configuration.Database;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 28-3-2016.
 */
public class DatabaseTest {

    @Test
    /**
     * Tests the {@code getCuisines()} method of the {@code Database} class.
     */
    public void testGetCuisines() throws Exception {
        List<String> cuisines = Database.getInstance().getCuisines();
        List<String> expected = new ArrayList();
        expected.add("dutch");
        expected.add("greek");
        expected.add("chinese");
        expected.add("spanish");
        expected.add("german");
        expected.add("japanese");
        expected.add("african");
        expected.add("thai");
        expected.add("italian");
        expected.add("mexican");
        expected.add("french");
        assertTrue(cuisines.size() == expected.size());
        for (int i = 0; i < cuisines.size(); i++) {
            assertTrue(cuisines.get(i).equals(expected.get(i)));
        }
    }

    @Test
    /**
     * Tests the {@code getSearchIngredients()} method of the {@code Database} class.
     */
    public void testGetSearchIngredients() throws Exception {
        List<String> ingredients = Database.getInstance().getSearchIngredients();
        List<String> expected = new ArrayList();
        expected.add("Pepper");
        expected.add("Chicken");
        expected.add("Spinach");
        expected.add("Potatoes");
        expected.add("Cabbage");
        expected.add("Shrimp");
        expected.add("Cucumber");
        expected.add("Broccoli");
        expected.add("Soy sauce");
        expected.add("Beef");
        expected.add("Pork");
        expected.add("Cheese");
        expected.add("Tomatoes");
        expected.add("Mustard");
        expected.add("Leeks");
        expected.add("Honey");
        expected.add("Pasta");
        expected.add("Carrots");
        expected.add("Peanut");
        expected.add("Limes");
        expected.add("Vanilla");
        expected.add("Mutton");
        expected.add("Eggs");
        expected.add("Garlic");
        expected.add("Salt");
        expected.add("Onions");
        expected.add("Noodles");
        expected.add("Milk");
        expected.add("Cream");
        expected.add("Rice");
        expected.add("Lemons");
        expected.add("Coconut");
        expected.add("Mushrooms");
        expected.add("Paprika");
        expected.add("Ginger");
        expected.add("Fish");
        expected.add("Apples");
        expected.add("Sugar");
        expected.add("Chocolate");
        assertTrue(ingredients.size() == expected.size());
        for (int i = 0; i < ingredients.size(); i++) {
            assertTrue(ingredients.get(i).equals(expected.get(i)));
        }
    }

    @Test
    /**
     * Tests the {@code getAllowedRecipeIds()} method of the {@code Database} class.
     */
    public void testGetAllowedRecipeIds() throws Exception {
        List<Integer> ids = Database.getInstance().getAllowedRecipeIds(1);
        List<Integer> expected = new ArrayList();
        expected.add(549277);
        expected.add(464832);
        expected.add(449585);
        expected.add(552811);
        expected.add(40023);
        expected.add(150074);
        expected.add(474640);
        expected.add(540890);
        expected.add(471817);
        expected.add(112272);
        expected.add(427600);
        expected.add(433338);
        expected.add(584911);
        expected.add(266072);
        expected.add(537481);
        expected.add(29176);
        expected.add(297516);
        expected.add(3339);
        expected.add(576257);
        expected.add(226742);
        expected.add(241186);
        expected.add(599167);
        expected.add(315576);
        expected.add(320701);
        expected.add(168766);
        expected.add(166373);
        expected.add(391293);
        expected.add(535108);
        expected.add(548826);
        expected.add(598787);
        assertTrue(ids.size() == expected.size());
        for (int i = 0; i < ids.size(); i++) {
            assertTrue(ids.get(i).intValue() == expected.get(i).intValue());
        }
    }

    @Test
    /**
     * Tests the {@code getAllowedIngredients()} method of the {@code Database} class.
     */
    public void testGetAllowedIngredients() throws Exception {
        List<String> ingredients = Database.getInstance().getAllowedIngredients(1);
        List<String> expected = new ArrayList();
        expected.add("pepper");
        expected.add("chicken");
        expected.add("spinach");
        expected.add("potatoes");
        expected.add("cabbage");
        expected.add("shrimp");
        expected.add("cucumber");
        expected.add("broccoli");
        expected.add("soy sauce");
        expected.add("beef");
        expected.add("pork");
        expected.add("cheese");
        expected.add("tomatoes");
        expected.add("mustard");
        expected.add("leeks");
        expected.add("honey");
        expected.add("pasta");
        expected.add("carrots");
        expected.add("limes");
        expected.add("vanilla");
        expected.add("mutton");
        expected.add("garlic");
        expected.add("salt");
        expected.add("onions");
        expected.add("noodles");
        expected.add("cream");
        expected.add("rice");
        expected.add("lemons");
        expected.add("coconut");
        expected.add("mushrooms");
        expected.add("paprika");
        expected.add("ginger");
        expected.add("fish");
        expected.add("apples");
        expected.add("sugar");
        expected.add("chocolate");
        assertTrue(ingredients.size() == expected.size());
        for (int i = 0; i < ingredients.size(); i++) {
            assertTrue(ingredients.get(i).equals(expected.get(i)));
        }
    }

    @Test
    /**
     * Tests the {@code getAllergens()} method of the {@code Database} class.
     */
    public void testGetAllergens() throws Exception {
        List<String> allergens = Database.getInstance().getAllergens(1);
        List<String> expected = new ArrayList();
        expected.add("milk");
        expected.add("peanut");
        expected.add("eggs");
        assertTrue(allergens.size() == expected.size());
        for (int i = 0; i < allergens.size(); i++) {
            assertTrue(allergens.get(i).equals(expected.get(i)));
        }
    }

    @Test
    /**
     * Tests the {@code isValidRecipeId()} method of the {@code Database} class.
     */
    public void testIsValidRecipeId() throws Exception {
        assertTrue(Database.getInstance().isValidIngredient("leeks"));
        assertFalse(Database.getInstance().isValidIngredient("chili"));
    }

    @Test
    /**
     * Tests the {@code isValidIngredient()} method of the {@code Database} class.
     */
    public void testIsValidIngredient() throws Exception {
        assertTrue(Database.getInstance().isValidRecipeId(528051));
        assertFalse(Database.getInstance().isValidRecipeId(1));
    }
}