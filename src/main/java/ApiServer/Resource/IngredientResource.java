package ApiServer.Resource;

import ApiServer.Status.IllegalStateStatus;
import Configuration.Database;
import Model.RecipeIngredientSimilarityMap;
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
    private static final int MAX_RESULTS = 5;

    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            String ingredientString = String.valueOf(request.getAttributes().get("ingredients"));
            String[] ingredients = ingredientString.split(",");
            List<String> allowedIngredients = Database.getInstance().getSearchIngredients();
            List<String> ingredientsIntersection = getIntersection(ingredients, allowedIngredients);
            RecipeIngredientSimilarityMap similarity = new RecipeIngredientSimilarityMap();
            for (String s : ingredientsIntersection) {
                String recipeQuery = "SELECT `recipe_id` FROM `search_ingredients` WHERE `ingredient` = '" + s + "';";
                ResultSet recipeResults = Database.getInstance().ExecuteQuery(recipeQuery, new ArrayList<String>());
                try {
                    while (recipeResults.next()) {
                        similarity.add(recipeResults.getInt(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.returnStatus(response, new IllegalStateStatus(null));
                }
            }
            similarity.sortDescending();

        }
    }

    private List<String> getIntersection(String[] a, List<String> b) {
        List<String> result = new ArrayList();
        for (String s : a) {
            for (String t : b) {
                if (s.equals(t)) {
                    result.add(s);
                    break;
                }
            }
        }
        return result;
    }
}
