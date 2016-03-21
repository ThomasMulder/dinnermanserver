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
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            String username = String.valueOf(request.getAttributes().get("username"));
            this.returnResponse(response, getUser(account_id, username), new UserSerializer());
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
