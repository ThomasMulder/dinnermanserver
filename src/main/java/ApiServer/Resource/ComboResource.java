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
    /**
     * Handles a HTTP GET request. This implements obtaining a recipe list based on parameterised ingredients and
     * min- and max values for calories, fat, protein and carbs.
     */
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);
            /* Obtain parameterised ingredients. */
            String ingredientString = String.valueOf(request.getAttributes().get("ingredients"));
            String[] ingredients = ingredientString.split(",");

            /* Get allowed ingredients for this user w.r.t. their allergens. */
            List<String> allowedIngredients = Database.getInstance().getAllowedIngredients(account_id);

            /* Get the intersection between requested- and allowed ingredients. */
            List<String> ingredientsIntersection = utils.getArrayAndListIntersection(ingredients, allowedIngredients,
                    false);
            RecipeIngredientSimilarityMap similarity = new RecipeIngredientSimilarityMap();

            /* Get identifiers of allowed recipes wr.t. user allergens. */
            List<Integer> allowedIds = Database.getInstance().getAllowedRecipeIds(account_id);

            /* Obtain the min- and max value parameters for calories, fat, protein and carbs. */
            int minCalories = Integer.parseInt(String.valueOf(request.getAttributes().get("minCalories")));
            int maxCalories = Integer.parseInt(String.valueOf(request.getAttributes().get("maxCalories")));
            int minFat = Integer.parseInt(String.valueOf(request.getAttributes().get("minFat")));
            int maxFat = Integer.parseInt(String.valueOf(request.getAttributes().get("maxFat")));
            int minProtein = Integer.parseInt(String.valueOf(request.getAttributes().get("minProtein")));
            int maxProtein = Integer.parseInt(String.valueOf(request.getAttributes().get("maxProtein")));
            int minCarbs = Integer.parseInt(String.valueOf(request.getAttributes().get("minCarbs")));
            int maxCarbs = Integer.parseInt(String.valueOf(request.getAttributes().get("maxCarbs")));
            /* Construct a query which finds identifiers for those recipes with values for calories, fat, protein and
            carbs that are within the parameterised range.
             */
            String nutritionQuery = "SELECT `id` FROM `recipes` WHERE `calories` >= " + minCalories + " AND " +
                    "`calories` <= " + maxCalories + " AND `fat` >= " + minFat + " AND " +
                    "`fat` <= " + maxFat + " AND `protein` >= " + minProtein + " AND " +
                    "`protein` <= " + maxProtein + " AND `carbs` >= " + minCarbs + " AND `carbs` <= " + maxCarbs + ";";

            /* Get the identifiers of recipes which satisfy the nutrition constraints. */
            List<Integer> recipeIds = dataHandler.handleListSingleInteger(database.ExecuteQuery(nutritionQuery,
                    new ArrayList<String>()), 1);

            /* Get the intersection between the allowed recipe identifiers and the recipe identifiers of those recipes
            satisfying nutrition constraints.
             */
            List<Integer> allowedRecipeIds = utils.getListIntegerIntersection(allowedIds, recipeIds);

            /* For all those ingredients in the allowed/requested intersection, find the recipe id. */
            for (String s : ingredientsIntersection) {
                String recipeQuery = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = '" + s + "';";
                /* Add an entry to the similarity map if recipe identifier was not yet present, otherwise increment
                 the similarity value. This amounts to counting the number of ingredients that occur in both the request
                 and the recipe, for each recipe. */
                dataHandler.handleSimilarityMapInsertion(database.ExecuteQuery(recipeQuery, new ArrayList<String>()),
                        similarity, allowedRecipeIds);
            }
            similarity.sortDescending(); // Sort from most ingredients in common, to least ingredients in common.
            List<Recipe> recipes = new ArrayList();
            // Get instances of Recipe for each of the identifiers.
            for (int i = 0; i < Math.min(MAX_RESULTS, similarity.getSimilarities().size()); i++) {
                recipes.add(getRecipeById(similarity.getRecipeIds().get(i)));
            }
            this.returnResponse(response, recipes, new RecipeListSerializer());
        }
    }
}
