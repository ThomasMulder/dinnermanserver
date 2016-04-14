package ApiServer.Resource;

import ApiServer.Serializer.UserSerializer;
import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.SuccessStatus;
import Configuration.Database;
import Model.User;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 2-3-2016.
 */
public class UserResource extends ApiResource {

    @Override
    /**
     * Handles a HTTP GET request. This implements obtaining a user profile from the server.
     */
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // The account is valid.
            updateTokenExpiration(account_id);
            this.returnResponse(response, Database.getInstance().getUserById(account_id), new UserSerializer());
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
