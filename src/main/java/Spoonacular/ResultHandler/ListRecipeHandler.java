package Spoonacular.ResultHandler;

import ApiServer.Resource.ApiResource;
import ApiServer.Serializer.RecipeListSerializer;
import ApiServer.Serializer.TestSerializer;
import Model.Test;
import Spoonacular.Model.Recipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.restlet.Response;

import java.util.List;

/**
 * Created by s124392 on 16-3-2016.
 */
public class ListRecipeHandler extends ResultHandlerAbstract {

    public ListRecipeHandler(ApiResource resource, Response response) {
        super(resource, response);
    }

    @Override
    public void handle(Object result) {
        List<Recipe> recipeList = (List<Recipe>) result;
        resource.returnResponse(response, recipeList, new RecipeListSerializer());
    }
}
