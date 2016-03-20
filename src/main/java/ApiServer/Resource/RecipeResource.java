package ApiServer.Resource;

import org.restlet.Request;
import org.restlet.Response;

/**
 * Created by s124392 on 2-3-2016.
 */
public class RecipeResource extends ApiResource {

    @Override
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            int recipe_id = Integer.parseInt(String.valueOf(request.getAttributes().get("id")));
            makeRecipeResponse(response, recipe_id);
        }
    }
}
