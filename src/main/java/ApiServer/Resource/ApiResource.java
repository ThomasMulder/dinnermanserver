package ApiServer.Resource;

import ApiServer.Serializer.AuxiliarySerializer;
import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.IllegalStateStatus;
import ApiServer.Status.NotFoundStatus;
import ApiServer.Status.StatusAbstract;
import ApiServer.Serializer.Serializer;
import Configuration.Database;
import Model.Recipe;
import Model.User;
import Processing.Data.DataHandler;
import Processing.Utils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Abstract class implementing some common functionality for API end-points.
 */
public abstract class ApiResource extends Restlet {
    /* Singleton instance of Utils providing common functionality. */
    protected static Utils utils = Utils.getInstance();
    /* Singleton instance of DataHandler providing data handling functionality. */
    protected static DataHandler dataHandler = DataHandler.getInstance();
    /* Singleton instance of Database providing database interaction functionality. */
    protected static Database database = Database.getInstance();

    @Override
    public void handle(Request request, Response response) {
        String data = null;

        response.setAccessControlAllowOrigin("*");
        HashSet<String> headers = new HashSet<String>();
        headers.add("Origin");
        headers.add("X-Requested-With");
        headers.add("Content-Type");
        headers.add("Accept");
        response.setAccessControlAllowHeaders(headers);
        HashSet<Method> methods = new HashSet<Method>();
        methods.add(Method.GET);
        methods.add(Method.POST);
        methods.add(Method.PUT);
        methods.add(Method.DELETE);
        response.setAccessControlAllowMethods(methods);

        try {
            data = request.getEntity().getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (request.getMethod().equals(Method.POST)) {
                this.handlePost(request, response, data);
            } else if (request.getMethod().equals(Method.PUT)) {
                this.handlePut(request, response, data);
            }else if (request.getMethod().equals(Method.DELETE)) {
                this.handleDelete(request, response, data);
            } else {
                this.handleGet(request, response);
            }
        } catch (IllegalStateException e) {
            this.returnStatus(response, new IllegalStateStatus(null));
        } catch (IllegalArgumentException e) {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        } catch (ClassNotFoundException e) {
            this.returnStatus(response, new NotFoundStatus(null));
        }
    }

    /**
     * Handle GET-request for this resource
     *
     * @param request request received from the client
     * @param response response returned to the client
     * @throws ClassNotFoundException If a requested resource in the request in not found
     * @throws IllegalArgumentException If the request contains an illegal argument
     * @throws IllegalStateException If the request requests an illegal state
     */
    protected void handleGet(Request request, Response response) throws ClassNotFoundException,
            IllegalArgumentException, IllegalStateException
    {
        response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    }

    /**
     * Handle GET-request for this resource
     *
     * @param request request received from the client
     * @param response response returned to the client
     * @param data The POST data
     * @throws ClassNotFoundException If a requested resource in the request in not found
     * @throws IllegalArgumentException If the request contains an illegal argument
     * @throws IllegalStateException If the request requests an illegal state
     */
    protected void handlePost(Request request, Response response, String data) throws ClassNotFoundException,
            IllegalArgumentException, IllegalStateException
    {
        response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    }

    /**
     * Handle PUT-request for this resource
     *
     * @param request request received from the client
     * @param response response returned to the client
     * @param data The PUT data
     * @throws ClassNotFoundException If a requested resource in the request in not found
     * @throws IllegalArgumentException If the request contains an illegal argument
     * @throws IllegalStateException If the request requests an illegal state
     */
    protected void handlePut(Request request, Response response, String data) throws ClassNotFoundException,
            IllegalArgumentException, IllegalStateException
    {
        response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    }

    /**
     * Handle DELETE-request for this resource
     *
     * @param request request received from the client
     * @param response response returned to the client
     * @param data The PUT data
     * @throws ClassNotFoundException If a requested resource in the request in not found
     * @throws IllegalArgumentException If the request contains an illegal argument
     * @throws IllegalStateException If the request requests an illegal state
     */
    protected void handleDelete(Request request, Response response, String data) throws ClassNotFoundException,
            IllegalArgumentException, IllegalStateException
    {
        response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    }

    /**
     * Build an appropriate JSON response object
     *
     * @param response The response to apply the object to
     * @param obj The object to convert to JSON
     * @param serializer An custom serializer to use on this object
     */
    public void returnResponse(Response response, Object obj, JsonSerializer serializer)
    {
        GsonBuilder builder =  Serializer.getInstance();
        if(serializer != null) builder.registerTypeAdapter(obj.getClass(), serializer);

        response.setEntity(builder.create().toJson(obj), MediaType.APPLICATION_JSON);
    }

    /**
     * Build an appropriate Status response object in JSON
     *
     * @param response The response to apply the status to
     * @param status The status to respond
     */
    public void returnStatus(Response response, StatusAbstract status)
    {
        response.setStatus(status.getStatus());
        this.returnResponse(response, status, null);
    }

    /**
     * Returns the account identifier for a request with parameters {@code username} and {@code authToken}.
     * Returns {@code -1} is the credentials are invalid.
     * @param request the request containing the parameters.
     * @param response the response to return.
     * @return
     */
    protected int getAccountId(Request request, Response response) {
        String username = String.valueOf(request.getAttributes().get("username"));
        String authToken = String.valueOf(request.getAttributes().get("authToken"));
        int account_id = -1; // Default value.
        // Query the database for given credentials.
        ResultSet results = Database.getInstance().ExecuteQuery("SELECT `id` FROM `accounts` WHERE BINARY `username` = '"
                + username + "' AND BINARY `authToken` = '" + authToken + "'", new ArrayList<String>());
        if (results != null) { // There is a result.
            try {
                if(results.first()) { // Look only at the first entry.
                    account_id = results.getInt(1); // Obtain the account identifier.
                } else { // There are no entries, something went wrong.
                    this.returnStatus(response, new IllegalArgumentStatus(null));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account_id;
    }

    /**
     * Prolongs the expiration date of an authentication token. This action is to be performed after every legal
     * request made by an account to the server.
     * @param account_id the account identifier of the account to prolong the authentication token validity for.
     */
    protected void updateTokenExpiration(int account_id) {
        Database.getInstance().ExecuteUpdate("UPDATE `accounts` SET `authTokenCreated` = CURRENT_TIMESTAMP WHERE `id` = '"
                + account_id + "';", new ArrayList<String>());
    }

    /**
     * Auxiliary method for splitting comma-separated data.
     * @param data the data to split.
     * @return {@code String[]}.
     */
    protected String[] getIdentifiersAsString(String data) {
        checkData(data); // Check for null-value.
        if (data.equals("")) { // Empty data.
            return new String[0];
        }
        return data.split(",");
    }

    /**
     * Auxiliary method for splitting comma-separated data.
     * @param data the data to split.
     * @return {@code int[]}.
     */
    protected int[] getIdentifiersAsInteger(String data) {
        checkData(data); // Check for null-value.
        if (data.equals("")) { // Empty data.
            return new int[0];
        }
        // Split into auxiliary String array, then parse each string to integer.
        String[] aux = data.split(",");
        int[] identifiers = new int[aux.length];
        int i = 0;
        for (String s : aux) {
            identifiers[i] = Integer.valueOf(s);
            i++;
        }
        return identifiers;
    }

    /**
     * Returns an instance of {@code Recipe} for a recipe with identifier {@code id}, if such a recipe exists.
     * Otherwise, return {@code null}.
     * @param id the identifier of the recipe to return a {@code Recipe} instance for.
     * @return {@code Recipe}.
     */
    protected Recipe getRecipeById(int id) {
        // Query the database for recipes by id.
        String query = "SELECT * FROM `recipes` WHERE `id` = '" + id + "';";
        // Auxiliary list, which should contain only one entry.
        List<Recipe> aux = dataHandler.handleListRecipe(database.ExecuteQuery(query, new ArrayList<String>()));
        if (!aux.isEmpty()) {
            return aux.get(0);
        }
        return null;
    }

    /**
     * Makes a HTTP response with a JSON-object describing a recipe identifier by {@code id}.
     * @param response the response to send.
     * @param id the identifier of the recipe to build a JSON-object for.
     */
    protected void makeRecipeResponse(Response response, int id) {
        Recipe recipe = getRecipeById(id); // Get the recipe object.
        if (recipe == null) {
            this.returnStatus(response, new IllegalStateStatus(null));
        } else {
            // Make the response.
            this.returnResponse(response, recipe.toJson(), new AuxiliarySerializer());
        }
    }

    /**
     * Checks for null-value in data string.
     * @param data the data to check.
     */
    private void checkData(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid data provided.");
        }
    }
}
