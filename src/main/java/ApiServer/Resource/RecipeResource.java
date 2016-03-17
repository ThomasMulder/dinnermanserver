package ApiServer.Resource;

import Spoonacular.ResultHandler.RecipeHandler;
import Spoonacular.Spoonacular;
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
            String id = String.valueOf(request.getAttributes().get("id"));
            String attributeString = String.valueOf(request.getAttributes().get("requiredAttributes"));
            String[] requiredAttributes = attributeString.split(",");
            Spoonacular.getInstance().queryById(id, new RecipeHandler(response, this, requiredAttributes));
        }
    }
}
