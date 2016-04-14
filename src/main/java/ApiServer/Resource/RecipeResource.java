package ApiServer.Resource;

import org.restlet.Request;
import org.restlet.Response;

/**
 * Created by s124392 on 2-3-2016.
 */
public class RecipeResource extends ApiResource {

    @Override
    /**
     * Handles a HTTP GET request. This implements obtaining a recipe by its identifier from the server.
     */
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // The account is valid.
            updateTokenExpiration(account_id);
            int recipe_id = Integer.parseInt(String.valueOf(request.getAttributes().get("id"))); // Obtain the identifier.
            makeRecipeResponse(response, recipe_id); // Respond with a JSON-object describing the recipe.
        }
    }
}
