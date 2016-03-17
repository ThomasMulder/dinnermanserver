package Spoonacular.ResultHandler;

import ApiServer.Resource.ApiResource;
import ApiServer.Serializer.AuxiliarySerializer;
import Spoonacular.Model.Recipe;
import com.google.gson.JsonObject;
import org.restlet.Response;

/**
 * Created by Thomas on 17-3-2016.
 */
public class RecipeHandler extends ResultHandlerAbstract {

    public RecipeHandler(Response response, ApiResource resource, String[] requiredAttributes) {
        super(response, resource, requiredAttributes);
    }

    @Override
    public void handle(Object result) {
        Recipe recipe = (Recipe) result;
        JsonObject object = new JsonObject();
        if (recipe.isValidRecipe(requiredAttributes)) {
            object = recipeToJson(recipe);
        }
        resource.returnResponse(response, object, new AuxiliarySerializer());
    }
}
