package ApiServer.Resource;

import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.IllegalStateStatus;
import ApiServer.Status.NotFoundStatus;
import ApiServer.Status.StatusAbstract;
import ApiServer.Serializer.Serializer;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.*;

import java.io.IOException;
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
}
