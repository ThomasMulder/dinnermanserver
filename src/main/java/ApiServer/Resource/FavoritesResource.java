package ApiServer.Resource;

import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.SuccessStatus;
import Configuration.Database;
import org.restlet.Request;
import org.restlet.Response;

import java.util.ArrayList;

/**
 * Created by s124392 on 24-2-2016.
 */
public class FavoritesResource extends ApiResource {

    @Override
    /**
     * Handles a HTTP POST request. This implements adding a favorite to the database for a specific user.
     */
    protected void handlePost(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);
            for (int id : getIdentifiersAsInteger(data)) { // Add new data.
                if (Database.getInstance().isValidRecipeId(id)) { // Is an allowed recipe identifier.
                    Database.getInstance().ExecuteUpdate("INSERT INTO `favorites` (`account_id`, `recipe_id`) VALUES" +
                            " ('" + account_id + "', '" + id + "');", new ArrayList<String>());
                }
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }

    @Override
    /**
     * Handles a HTTP POST request. This implements deleting a favorite from the database for a specific user.
     */
    protected void handleDelete(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);
            for (int id : getIdentifiersAsInteger(data)) { // Remove data.
                Database.getInstance().ExecuteUpdate("DELETE FROM `favorites` WHERE `account_id` = '" + account_id + "' AND `recipe_id` = '" + id + "';", new ArrayList<String>());
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
