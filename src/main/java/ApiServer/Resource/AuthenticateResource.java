package ApiServer.Resource;

import ApiServer.Serializer.TokenSerializer;
import ApiServer.Status.IllegalArgumentStatus;
import Configuration.Database;
import Processing.TokenGenerator;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by s124392 on 23-2-2016.
 */
public class AuthenticateResource extends ApiResource {

    @Override
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        String username = String.valueOf(request.getAttributes().get("username"));
        String password = String.valueOf(request.getAttributes().get("password"));
        String query = "SELECT COUNT(id) AS `CNT`, `authToken` FROM accounts WHERE `username` = '" + username + "' AND " +
                "`password` = '" + password + "' LIMIT 0,1;";
        Object[] authenticationArray = dataHandler.handleAuthentication(database.ExecuteQuery(query, new ArrayList<String>()));
        int cnt = (int) authenticationArray[0];
        String existingToken = (String) authenticationArray[1];
        String authenticationToken = null;
        if (cnt == 1) {
            if (existingToken == null) {
                 authenticationToken = TokenGenerator.getInstance().generateAuthenticationToken(16);
            } else {
                 authenticationToken = existingToken;
            }
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
        if (!(authenticationToken == null || authenticationToken == "")) {
            Database.getInstance().ExecuteUpdate("UPDATE `accounts` SET `authToken` = '" + authenticationToken +
                    "', `authTokenCreated` = CURRENT_TIMESTAMP WHERE `username` = '" + username + "';", new ArrayList<String>());
            this.returnResponse(response, authenticationToken, new TokenSerializer());
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
