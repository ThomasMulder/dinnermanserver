package ApiServer.Resource;

import ApiServer.Serializer.RecipeListSerializer;
import ApiServer.Status.IllegalStateStatus;
import Configuration.Database;
import Model.Recipe;
import Model.RecipeIngredientSimilarityMap;
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
            List<String> ingredientsIntersection = getIntersection(ingredients, allowedIngredients);
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
            ResultSet resultSet = Database.getInstance().ExecuteQuery(nutritionQuery, new ArrayList<String>());
            List<Integer> recipeIds = new ArrayList();
            try {
                while (resultSet.next()) {
                    recipeIds.add(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<Integer> allowedRecipeIds = getListIntegerIntersection(allowedIds, recipeIds);
            for (String s : ingredientsIntersection) {
                String recipeQuery = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = '" + s + "';";
                ResultSet recipeResults = Database.getInstance().ExecuteQuery(recipeQuery, new ArrayList<String>());
                try {
                    while (recipeResults.next()) {
                        int i = recipeResults.getInt(1);
                        if (Database.getInstance().listContains(allowedRecipeIds, i)) {
                            similarity.add(i);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.returnStatus(response, new IllegalStateStatus(null));
                }
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
