package ApiServer.Resource;

import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.SuccessStatus;
import Configuration.Database;
import org.restlet.Request;
import org.restlet.Response;

import java.util.ArrayList;

/**
 * Created by s124392 on 2-3-2016.
 * Implements the API end-point for added- and deleting allergens.
 */
public class AllergensResource extends ApiResource {

    @Override
    /**
     * Handles a HTTP POST request. This implements adding an allergen to the database for a specific user.
     */
    protected void handlePost(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);
            for (String allergen : getIdentifiersAsString(data)) { // Add new data.
                if (Database.getInstance().isValidIngredient(allergen)) { // Is a valid allergen identifier.
                    Database.getInstance().ExecuteUpdate("INSERT INTO `allergens` (`account_id`, `allergen`) VALUES" +
                            " ('" + account_id + "', '" + allergen + "');", new ArrayList<String>());
                }
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }

    @Override
    /**
     * Handles a HTTP POST request. This implements deleting an allergen from the database for a specific user.
     */
    protected void handleDelete(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // Account is valid.
            updateTokenExpiration(account_id);
            for (String allergen : getIdentifiersAsString(data)) { // Delete data.
                Database.getInstance().ExecuteUpdate("DELETE FROM `allergens` WHERE `account_id` = '" + account_id + "' AND `allergen` = '" + allergen + "';", new ArrayList<String>());
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
