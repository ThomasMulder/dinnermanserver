package ApiServer.Resource;

import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.IllegalStateStatus;
import ApiServer.Status.NotFoundStatus;
import ApiServer.Status.StatusAbstract;
import ApiServer.Serializer.Serializer;
import Configuration.Database;
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

public abstract class ApiResource extends Restlet {
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
    protected void handleGet(Request request, Response response) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException
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
    protected void handlePost(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException
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
    protected void handlePut(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException
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
    protected void handleDelete(Request request, Response response, String data) throws ClassNotFoundException, IllegalArgumentException, IllegalStateException
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
    protected void returnResponse(Response response, Object obj, JsonSerializer serializer)
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
    protected void returnStatus(Response response, StatusAbstract status)
    {
        response.setStatus(status.getStatus());
        this.returnResponse(response, status, null);
    }

    protected int getAccountId(Request request, Response response) {
        String username = String.valueOf(request.getAttributes().get("username"));
        String authToken = String.valueOf(request.getAttributes().get("authToken"));
        int account_id = -1;
        ResultSet results = Database.getInstance().ExecuteQuery("SELECT `id` FROM `accounts` WHERE `username` = '"
                + username + "' AND `authToken` = '" + authToken + "'", new ArrayList<String>());
        if (results != null) {
            try {
                if(results.first()) {
                    account_id = results.getInt(1);
                } else {
                    this.returnStatus(response, new IllegalArgumentStatus(null));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account_id;
    }

    protected void updateTokenExpiration(int account_id) {
        Database.getInstance().ExecuteUpdate("UPDATE `accounts` SET `authTokenCreated` = CURRENT_TIMESTAMP WHERE `id` = '" + account_id + "';", new ArrayList<String>());
    }

    protected String[] getIdentifiersAsString(String data) {
        checkData(data);
        if (data.equals("")) {
            return new String[0];
        }
        return data.split(",");
    }

    protected int[] getIdentifiersAsInteger(String data) {
        checkData(data);
        if (data.equals("")) {
            return new int[0];
        }
        String[] aux = data.split(",");
        int[] identifiers = new int[aux.length];
        int i = 0;
        for (String s : aux) {
            identifiers[i] = Integer.valueOf(s);
            i++;
        }
        return identifiers;
    }

    private void checkData(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid data provided.");
        }
    }
}
