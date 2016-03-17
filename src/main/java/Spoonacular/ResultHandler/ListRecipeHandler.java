package Spoonacular.ResultHandler;

import ApiServer.Resource.ApiResource;
import ApiServer.Serializer.AuxiliarySerializer;
import Spoonacular.Model.Recipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.restlet.Response;

import java.util.List;

/**
 * Created by s124392 on 16-3-2016.
 */
public class ListRecipeHandler extends ResultHandlerAbstract {


    public ListRecipeHandler(Response response, ApiResource resource, String[] requiredAttributes) {
        super(response, resource, requiredAttributes);
    }

    @Override
    public void handle(Object result) {
        List<Recipe> recipeList = (List<Recipe>) result;
        resource.returnResponse(response, listToJson(recipeList), new AuxiliarySerializer());
    }

    private JsonObject listToJson(List<Recipe> recipes) {
        JsonArray jsonRecipes = new JsonArray();
        for (Recipe recipe : recipes) {
            if (recipe.isValidRecipe(requiredAttributes)) {
                jsonRecipes.add(recipeToJson(recipe));
            }
        }
        JsonObject object = new JsonObject();
        object.add("recipes", jsonRecipes);
        return object;
    }
}
