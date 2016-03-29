package ConfigurationTest;

import Configuration.Database;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

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
        expected.add("Potatoes");
        expected.add("Pepper");
        expected.add("Vanilla");
        expected.add("Coconut");
        expected.add("Cream");
        expected.add("Cheese");
        expected.add("Leeks");
        expected.add("Ginger");
        expected.add("Eggs");
        expected.add("Salt");
        expected.add("Paprika");
        expected.add("Fish");
        expected.add("Beef");
        expected.add("Tomatoes");
        expected.add("Cabbage");
        expected.add("Spinach");
        expected.add("Sugar");
        expected.add("Shrimp");
        expected.add("Milk");
        expected.add("Rice");
        expected.add("Peanut");
        expected.add("Onions");
        expected.add("Mushrooms");
        expected.add("Soy sauce");
        expected.add("Chocolate");
        expected.add("Mutton");
        expected.add("Apples");
        expected.add("Honey");
        expected.add("Lemons");
        expected.add("Broccoli");
        expected.add("Carrots");
        expected.add("Chicken");
        expected.add("Garlic");
        expected.add("Pasta");
        expected.add("Mustard");
        expected.add("Cucumber");
        expected.add("Pork");
        expected.add("Limes");
        expected.add("Noodles");
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
        expected.add(374273);
        expected.add(239068);
        expected.add(384740);
        expected.add(470413);
        expected.add(150074);
        expected.add(474640);
        expected.add(540890);
        expected.add(471817);
        expected.add(112272);
        expected.add(427600);
        expected.add(433338);
        expected.add(204018);
        expected.add(470869);
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
        expected.add("potatoes");
        expected.add("pepper");
        expected.add("vanilla");
        expected.add("coconut");
        expected.add("cream");
        expected.add("cheese");
        expected.add("leeks");
        expected.add("ginger");
        expected.add("salt");
        expected.add("paprika");
        expected.add("fish");
        expected.add("beef");
        expected.add("tomatoes");
        expected.add("cabbage");
        expected.add("spinach");
        expected.add("sugar");
        expected.add("shrimp");
        expected.add("rice");
        expected.add("onions");
        expected.add("mushrooms");
        expected.add("soy sauce");
        expected.add("chocolate");
        expected.add("mutton");
        expected.add("apples");
        expected.add("honey");
        expected.add("lemons");
        expected.add("broccoli");
        expected.add("carrots");
        expected.add("chicken");
        expected.add("garlic");
        expected.add("pasta");
        expected.add("mustard");
        expected.add("cucumber");
        expected.add("pork");
        expected.add("limes");
        expected.add("noodles");
        assertTrue(ingredients.size() == expected.size());
        for (int i = 0; i < ingredients.size(); i++) {
            assertTrue(ingredients.get(i).equals(expected.get(i)));
        }
    }

    @Test
    /**
     * Tests the {@code getListDifference()} method of the {@code Database} class.
     */
    public void testGetListDifference() throws Exception {
        List<String> a = new ArrayList();
        List<String> b = new ArrayList();
        a.add("a");
        a.add("b");
        b.add("b");
        List<String> diff = Database.getInstance().getListDifference(a, b);
        assertTrue(diff.size() == 1);
        assertTrue(diff.get(0).equals("a"));
    }

    @Test
    /**
     * Tests the {@code getAllergens()} method of the {@code Database} class.
     */
    public void testGetAllergens() throws Exception {
        List<String> allergens = Database.getInstance().getAllergens(1);
        List<String> expected = new ArrayList();
        expected.add("eggs");
        expected.add("milk");
        expected.add("peanut");
        assertTrue(allergens.size() == expected.size());
        for (int i = 0; i < allergens.size(); i++) {
            assertTrue(allergens.get(i).equals(expected.get(i)));
        }
    }

    @Test
    /**
     * Tests the {@code getRecipeIdsByPopularity()} method of the {@code Database} class.
     */
    public void testGetRecipeIdsByPopularity() throws Exception {
        List<Integer> ids = Database.getInstance().getRecipeIdsByPopularity();
        String idString = "";
        for (int id : ids) {
            idString = idString.concat(String.valueOf(id).trim() + "\n");
        }
        String expected = "528051\n" +
                "536703\n" +
                "433338\n" +
                "549277\n" +
                "621105\n" +
                "464832\n" +
                "449585\n" +
                "78238\n" +
                "552811\n" +
                "40023\n" +
                "67207\n" +
                "374273\n" +
                "715038\n" +
                "239068\n" +
                "384740\n" +
                "470413\n" +
                "701803\n" +
                "666239\n" +
                "150074\n" +
                "474640\n" +
                "519356\n" +
                "540890\n" +
                "471817\n" +
                "112272\n" +
                "427600\n" +
                "204018\n" +
                "470869\n" +
                "233128\n" +
                "559401\n" +
                "584911\n" +
                "266072\n" +
                "537481\n" +
                "29176\n" +
                "297516\n" +
                "562219\n" +
                "3339\n" +
                "576257\n" +
                "374990\n" +
                "226742\n" +
                "241186\n" +
                "247117\n" +
                "558752\n" +
                "599167\n" +
                "202492\n" +
                "521781\n" +
                "315576\n" +
                "320701\n" +
                "168766\n" +
                "166373\n" +
                "434151\n" +
                "391293\n" +
                "269186\n" +
                "535108\n" +
                "548826\n" +
                "598787\n" +
                "349362\n";
        assertTrue(idString.equals(expected));
    }

    @Test
    /**
     * Tests the {@code listContains()} method of the {@code Database} class.
     */
    public void testListContains() throws Exception {
        List<Integer> aux = new ArrayList();
        aux.add(1);
        assertTrue(Database.getInstance().listContains(aux, 1));
        assertFalse(Database.getInstance().listContains(aux, 2));
    }

    @Test
    /**
     * Tests the {@code listContains()} method of the {@code Database} class.
     */
    public void testListContains1() throws Exception {
        List<String> aux = new ArrayList();
        aux.add("a");
        assertTrue(Database.getInstance().listContains(aux, "a"));
        assertFalse(Database.getInstance().listContains(aux, "b"));
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