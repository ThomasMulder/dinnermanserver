package Spoonacular.ResultHandler;

import ApiServer.Resource.ApiResource;
import Spoonacular.Model.Recipe;
import com.google.gson.JsonObject;
import org.restlet.Response;

/**
 * Created by s124392 on 16-3-2016.
 */
public abstract class ResultHandlerAbstract {
    protected ApiResource resource;
    protected Response response;

    public ResultHandlerAbstract(ApiResource resource, Response response) {
        this.resource = resource;
        this.response = response;
    }

    public abstract void handle(Object result);
}
