package ApiServer.Resource;

import ApiServer.Status.IllegalArgumentStatus;
import Spoonacular.ResultHandler.RandomHandler;
import org.restlet.Request;
import org.restlet.Response;

import Spoonacular.Spoonacular;

/**
 * Created by s124392 on 2-3-2016.
 * Handles a HTTP GET request and attempt to return a random recipe.
 */
public class RandomResource extends ApiResource {
    private static final String[] cuisinePool = {"dutch", "french", "german", "italian"};

    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            int index = (int) Math.round(Math.random() * cuisinePool.length);
            String cuisine = cuisinePool[index];
            String[] requiredAttributes = {"all"};
            Spoonacular.getInstance().queryByCuisine(cuisine, new RandomHandler(response, this, requiredAttributes));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
