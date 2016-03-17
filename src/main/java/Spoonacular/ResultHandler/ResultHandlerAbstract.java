package Spoonacular.ResultHandler;

import Spoonacular.Model.Recipe;
import com.google.gson.JsonObject;

/**
 * Created by s124392 on 16-3-2016.
 */
public abstract class ResultHandlerAbstract {

    public abstract void handle(Object result);

    protected JsonObject recipeToJson(Recipe recipe, String[] requiredAttributes) {
        JsonObject object = new JsonObject();
        for (String requiredAttribute : requiredAttributes) {
            object.addProperty(requiredAttribute, recipe.getAttribute(requiredAttribute));
        }
        return object;
    }
}
