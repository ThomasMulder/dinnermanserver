package ApiServer.Resource;

import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.SuccessStatus;
import Configuration.Database;
import org.restlet.Request;
import org.restlet.Response;

import java.util.ArrayList;

/**
 * Created by s124392 on 2-3-2016.
 */
public class AllergensResource extends ApiResource {

    @Override
    protected void handlePost(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            for (String allergen : getIdentifiersAsString(data)) {
                Database.getInstance().ExecuteUpdate("INSERT INTO `allergens` (`account_id`, `allergen`) VALUES ('" + account_id + "', '" + allergen + "');", new ArrayList<String>());
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }

    @Override
    protected void handleDelete(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            for (String allergen : getIdentifiersAsString(data)) {
                Database.getInstance().ExecuteUpdate("DELETE FROM `allergens` WHERE `account_id` = '" + account_id + "' AND `allergen` = '" + allergen + "';", new ArrayList<String>());
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
