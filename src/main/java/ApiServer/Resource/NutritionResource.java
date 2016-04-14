package ApiServer.Resource;

import ApiServer.Serializer.RecipeListSerializer;
import Configuration.Database;
import Model.Recipe;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 28-3-2016.
 */
public class NutritionResource extends ApiResource {

    @Override
    /**
     * Handles a HTTP GET request. This implements obtaining a recipe based on input nutrition value intervals from the
     * server.
     */
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);

            /* Obtain the min- and max values for each nutritional unit. */
            int minCalories = Integer.parseInt(String.valueOf(request.getAttributes().get("minCalories")));
            int maxCalories = Integer.parseInt(String.valueOf(request.getAttributes().get("maxCalories")));
            int minFat = Integer.parseInt(String.valueOf(request.getAttributes().get("minFat")));
            int maxFat = Integer.parseInt(String.valueOf(request.getAttributes().get("maxFat")));
            int minProtein = Integer.parseInt(String.valueOf(request.getAttributes().get("minProtein")));
            int maxProtein = Integer.parseInt(String.valueOf(request.getAttributes().get("maxProtein")));
            int minCarbs = Integer.parseInt(String.valueOf(request.getAttributes().get("minCarbs")));
            int maxCarbs = Integer.parseInt(String.valueOf(request.getAttributes().get("maxCarbs")));

            /* Query the database for recipes satisfying the nutrition value constraints. */
            String nutritionQuery = "SELECT `id` FROM `recipes` WHERE `calories` >= " + minCalories + " AND " +
                    "`calories` <= " + maxCalories + " AND `fat` >= " + minFat + " AND " +
                    "`fat` <= " + maxFat + " AND `protein` >= " + minProtein + " AND " +
                    "`protein` <= " + maxProtein + " AND `carbs` >= " + minCarbs + " AND `carbs` <= " + maxCarbs + ";";

            /* Find the intersection between recipes satisfying the nutrition value constraints and the allergy
            constraints of the user making this request.
             */
            List<Integer> recipeIds = dataHandler.handleListSingleInteger(database.ExecuteQuery(nutritionQuery,
                    new ArrayList<String>()), 1);
            List<Integer> allowedRecipeIds = Database.getInstance().getAllowedRecipeIds(account_id);
            List<Integer> intersection = utils.getListIntegerIntersection(recipeIds, allowedRecipeIds);
            List<Recipe> recipes = new ArrayList();
            for (int i : intersection) { // The recipe identifier is in the intersection.
                recipes.add(getRecipeById(i)); // Find the recipe data by its identifier, add it to the response.
            }
            this.returnResponse(response, recipes, new RecipeListSerializer());
        }
    }
}
