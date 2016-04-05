package ApiServer.Resource;

import ApiServer.Serializer.RecipeListSerializer;
import ApiServer.Status.IllegalStateStatus;
import Configuration.Database;
import Model.Recipe;
import Model.RecipeIngredientSimilarityMap;
import Processing.Utils;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 28-3-2016.
 */
public class ComboResource extends ApiResource {
    private static final int MAX_RESULTS = 5;

    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            String ingredientString = String.valueOf(request.getAttributes().get("ingredients"));
            String[] ingredients = ingredientString.split(",");
            List<String> allowedIngredients = Database.getInstance().getAllowedIngredients(account_id);
            List<String> ingredientsIntersection = utils.getArrayAndListIntersection(ingredients, allowedIngredients, false);
            RecipeIngredientSimilarityMap similarity = new RecipeIngredientSimilarityMap();
            List<Integer> allowedIds = Database.getInstance().getAllowedRecipeIds(account_id);
            int minCalories = Integer.parseInt(String.valueOf(request.getAttributes().get("minCalories")));
            int maxCalories = Integer.parseInt(String.valueOf(request.getAttributes().get("maxCalories")));
            int minFat = Integer.parseInt(String.valueOf(request.getAttributes().get("minFat")));
            int maxFat = Integer.parseInt(String.valueOf(request.getAttributes().get("maxFat")));
            int minProtein = Integer.parseInt(String.valueOf(request.getAttributes().get("minProtein")));
            int maxProtein = Integer.parseInt(String.valueOf(request.getAttributes().get("maxProtein")));
            int minCarbs = Integer.parseInt(String.valueOf(request.getAttributes().get("minCarbs")));
            int maxCarbs = Integer.parseInt(String.valueOf(request.getAttributes().get("maxCarbs")));
            String nutritionQuery = "SELECT `id` FROM `recipes` WHERE `calories` >= " + minCalories + " AND " +
                    "`calories` <= " + maxCalories + " AND `fat` >= " + minFat + " AND " +
                    "`fat` <= " + maxFat + " AND `protein` >= " + minProtein + " AND " +
                    "`protein` <= " + maxProtein + " AND `carbs` >= " + minCarbs + " AND `carbs` <= " + maxCarbs + ";";
            List<Integer> recipeIds = dataHandler.handleListSingleInteger(database.ExecuteQuery(nutritionQuery, new ArrayList<String>()), 1);
            List<Integer> allowedRecipeIds = utils.getListIntegerIntersection(allowedIds, recipeIds);
            for (String s : ingredientsIntersection) {
                String recipeQuery = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = '" + s + "';";
                dataHandler.handleSimilarityMapInsertion(database.ExecuteQuery(recipeQuery, new ArrayList<String>()), similarity, allowedRecipeIds);
            }
            similarity.sortDescending();
            List<Recipe> recipes = new ArrayList();
            for (int i = 0; i < Math.min(MAX_RESULTS, similarity.getSimilarities().size()); i++) {
                recipes.add(getRecipeById(similarity.getRecipeIds().get(i)));
            }
            this.returnResponse(response, recipes, new RecipeListSerializer());
        }
    }
}
