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
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            List<Integer> idList = Database.getInstance().getAllowedRecipeIds(account_id);
            makeRecipeResponse(response, idList.get(utils.getRandomIndexFromList(idList)));
        }
    }
}
