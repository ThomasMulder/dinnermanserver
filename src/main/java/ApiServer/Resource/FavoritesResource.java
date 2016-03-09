package ApiServer.Resource;

import ApiServer.Serializer.Serializer;
import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.SuccessStatus;
import Configuration.Database;
import Model.Test;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by s124392 on 24-2-2016.
 */
public class FavoritesResource extends ApiResource {

    @Override
    protected void handlePost(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            for (int id : getIdentifiersAsInteger(data)) {
                Database.getInstance().ExecuteUpdate("INSERT INTO `favorites` (`account_id`, `recipe_id`) VALUES ('" + account_id + "', '" + id + "');", new ArrayList<String>());
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
            for (int id : getIdentifiersAsInteger(data)) {
                Database.getInstance().ExecuteUpdate("DELETE FROM `favorites` WHERE `account_id` = '" + account_id + "' AND `recipe_id` = '" + id + "';", new ArrayList<String>());
            }
            this.returnStatus(response, new SuccessStatus(null));
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}