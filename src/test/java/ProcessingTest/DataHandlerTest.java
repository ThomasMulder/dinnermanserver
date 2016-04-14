package ProcessingTest;

import Configuration.Database;
import Model.Recipe;
import Model.RecipeIngredientSimilarityMap;
import Model.User;
import Processing.Data.DataHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 1-4-2016.
 */
public class DataHandlerTest {
    private static DataHandler dataHandler = DataHandler.getInstance();
    private static Database database = Database.getInstance();

    @Test
    public void testHandleSingleInteger() throws Exception {
        String query = "SELECT `id` FROM `accounts` WHERE BINARY `username` = 'test';";
        Integer result = dataHandler.handleSingleInteger(database.ExecuteQuery(query, new ArrayList<String>()), 1);
        assertTrue(result == 1);
    }

    @Test
    public void testHandleListSingleInteger() throws Exception {
        String query = "SELECT `id` FROM `accounts`;";
        List<Integer> result = dataHandler.handleListSingleInteger(database.ExecuteQuery(query, new ArrayList<String>()), 1);
        assertTrue(result.size() == 3);
        assertTrue(result.get(0) == 1);
        assertTrue(result.get(1) == 7);
        assertTrue(result.get(2) == 8);
    }

    @Test
    public void testHandleListSingleString() throws Exception {
        String query = "SELECT `username` FROM `accounts`;";
        List<String> result = dataHandler.handleListSingleString(database.ExecuteQuery(query, new ArrayList<String>()), 1);
        assertTrue(result.size() == 3);
        assertTrue(result.get(0).equals("test"));
        assertTrue(result.get(1).equals("test2"));
        assertTrue(result.get(2).equals("test3"));
    }

    @Test
    public void testHandleAuthentication() throws Exception {
       String query = "SELECT COUNT(id) AS `CNT`, `authToken` FROM accounts WHERE `username` = 'test' AND `password` = 'test123' LIMIT 0,1;";
        Object[] result = dataHandler.handleAuthentication(database.ExecuteQuery(query, new ArrayList<String>()));
        assertTrue(result.length == 2);
        int cnt = (int) result[0];
        String token = String.valueOf(result[1]);
        assertTrue(cnt == 1);
        assertTrue(token != null && token.length() == 16);
    }

    @Test
    public void testHandleListRecipe() throws Exception {
        String query = "SELECT * FROM `recipes`;";
        List<Recipe> result = dataHandler.handleListRecipe(database.ExecuteQuery(query, new ArrayList<String>()));
        assertTrue(result.size() == 56);
    }

    @Test
    public void testHandleSimilarityMapInsertion() throws Exception {
        RecipeIngredientSimilarityMap recipeIngredientSimilarityMap = new RecipeIngredientSimilarityMap();
        List<Integer> allowedIds = new ArrayList();
        allowedIds.add(528051);
        allowedIds.add(549277);
        allowedIds.add(621105);
        for (int i = 0; i < recipeIngredientSimilarityMap.getRecipeIds().size(); i++) {
            //System.out.println(recipeIngredientSimilarityMap.getRecipeIds().get(i) + " : "
                    //+ recipeIngredientSimilarityMap.getSimilarities().get(i));
        }
        //System.out.println("=====================");
        String query = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = 'salt';";
        dataHandler.handleSimilarityMapInsertion(database.ExecuteQuery(query, new ArrayList<String>()),
                recipeIngredientSimilarityMap, allowedIds);
        for (int i = 0; i < recipeIngredientSimilarityMap.getRecipeIds().size(); i++) {
            //System.out.println(recipeIngredientSimilarityMap.getRecipeIds().get(i) + " : "
                    //+ recipeIngredientSimilarityMap.getSimilarities().get(i));
        }
        //System.out.println("=====================");
        query = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = 'chicken';";
        dataHandler.handleSimilarityMapInsertion(database.ExecuteQuery(query, new ArrayList<String>()),
                recipeIngredientSimilarityMap, allowedIds);
        for (int i = 0; i < recipeIngredientSimilarityMap.getRecipeIds().size(); i++) {
            //System.out.println(recipeIngredientSimilarityMap.getRecipeIds().get(i) + " : "
                    //+ recipeIngredientSimilarityMap.getSimilarities().get(i));
        }
    }

    @Test
    public void testHandleListUser() throws Exception {
        String query = "SELECT `id` FROM `accounts`;";
        List<User> result = dataHandler.handleListUser(database.ExecuteQuery(query, new ArrayList<String>()));
        assertTrue(result.size() == 3);
    }
}