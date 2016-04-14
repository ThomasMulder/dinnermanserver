package ApiServer.Resource;

import Configuration.Database;
import org.restlet.Request;
import org.restlet.Response;

import java.util.List;

/**
 * Created by s124392 on 2-3-2016.
 * Handles a HTTP GET request and attempt to return a random recipe.
 */
public class RandomResource extends ApiResource {

    @Override
    /**
     * Handles a HTTP GET request. This implements obtaining a random recipe from the server.
     */
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // The account is valid.
            updateTokenExpiration(account_id);
            /* Obtain the list of recipe identifiers of recipes allowed with respect to the allergen constraints of
            the user making this request.
             */
            List<Integer> idList = Database.getInstance().getAllowedRecipeIds(account_id);
            /* Respond with a JSON-object describing the recipe found at a random index in the list of results. */
            makeRecipeResponse(response, idList.get(utils.getRandomIndexFromList(idList)));
        }
    }
}
