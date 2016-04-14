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
    /* The length of an authentication token (e.g. the number of characters). */
    private static final int TOKEN_LENGTH = 16;

    @Override
    /**
     * Handles a HTTP GET request. This implements obtaining an authentication token from the server.
     */
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        /* Get attributes from the request. */
        String username = String.valueOf(request.getAttributes().get("username"));
        String password = String.valueOf(request.getAttributes().get("password"));
        /* Construct MySQL Query. */
        String query = "SELECT COUNT(id) AS `CNT`, `authToken` FROM accounts WHERE `username` = '" + username + "' AND " +
                "`password` = '" + password + "' LIMIT 0,1;";
        /* Obtain the account count and existing token pair, as object array. */
        Object[] authenticationArray = dataHandler.handleAuthentication(database.ExecuteQuery(query,
                new ArrayList<String>()));
        int cnt = (int) authenticationArray[0];
        String existingToken = (String) authenticationArray[1];
        String authenticationToken = null;
        if (cnt == 1) { // There is exactly one account matching the parameterised username and password.
            if (existingToken == null) { // No token yet existed, generate one.
                 authenticationToken = TokenGenerator.getInstance().generateAuthenticationToken(TOKEN_LENGTH);
            } else {
                 authenticationToken = existingToken;
            }
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
        if (!(authenticationToken == null || authenticationToken == "")) { // Update the database with the new token.
            Database.getInstance().ExecuteUpdate("UPDATE `accounts` SET `authToken` = '" + authenticationToken +
                    "', `authTokenCreated` = CURRENT_TIMESTAMP WHERE `username` = '" + username + "';",
                    new ArrayList<String>());
            this.returnResponse(response, authenticationToken, new TokenSerializer());
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
