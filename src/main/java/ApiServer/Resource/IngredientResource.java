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
 * Created by s124392 on 22-3-2016.
 */
public class IngredientResource extends ApiResource {
    /* The maximum number of recipes returned. */
    private static final int MAX_RESULTS = 5;

    /**
     * Handles a HTTP GET request. This implements obtaining a recipe based on input ingredients from the server.
     */
    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);

            /* Obtain the comma separated ingredients. */
            String ingredientString = String.valueOf(request.getAttributes().get("ingredients"));
            String[] ingredients = ingredientString.split(",");

            /* Obtain the allowed ingredients, and the intersection with the input ingredients. */
            List<String> allowedIngredients = Database.getInstance().getAllowedIngredients(account_id);
            List<String> ingredientsIntersection = utils.getArrayAndListIntersection(ingredients, allowedIngredients,
                    false);

            /* Calculate for each recipe in the database, how many ingredients it shares with the intersection
            and sort the recipes from highest number of shared ingredients to lowest.
             */
            RecipeIngredientSimilarityMap similarity = new RecipeIngredientSimilarityMap();
            List<Integer> allowedIds = Database.getInstance().getAllowedRecipeIds(account_id);
            for (String s : ingredientsIntersection) { // Find recipe with paramterised ingredient.
                String recipeQuery = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = '" + s + "';";
                dataHandler.handleSimilarityMapInsertion(database.ExecuteQuery(recipeQuery, new ArrayList<String>()),
                        similarity, allowedIds);
            }
            similarity.sortDescending();

            List<Recipe> recipes = new ArrayList();
            /* Add the most similar recipes (e.g. recipes with highest number of ingredients shared with input) to the
            response. Add at most MAX_RESULTS recipes.
             */
            for (int i = 0; i < Math.min(MAX_RESULTS, similarity.getSimilarities().size()); i++) {
                recipes.add(getRecipeById(similarity.getRecipeIds().get(i)));
            }
            this.returnResponse(response, recipes, new RecipeListSerializer());
        }
    }
}
