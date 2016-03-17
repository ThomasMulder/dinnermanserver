package ApiServer.Serializer;

import Spoonacular.Model.Recipe;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by s124392 on 16-3-2016.
 */
public class RecipeListSerializer implements JsonSerializer<List<Recipe>> {
    private static final String[] requiredAttributes = {"id", "title", "extendedIngredients"};

    @Override
    public JsonElement serialize(List<Recipe> recipes, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonRecipes = new JsonArray();
        for (Recipe recipe : recipes) {
            if (recipe.isValidRecipe(requiredAttributes)) {
                jsonRecipes.add(recipeToJson(recipe, requiredAttributes));
            }
        }
        JsonObject object = new JsonObject();
        object.add("recipes", jsonRecipes);
        return object;
    }

    private JsonObject recipeToJson(Recipe recipe, String[] requiredAttributes) {
        JsonObject object = new JsonObject();
        for (String requiredAttribute : requiredAttributes) {
            if (requiredAttribute.equals("extendedIngredients")) {
                object.add("extendedIngredients", recipe.getInformation().getExtenededIngredientsAsJson());
            } else {
                object.addProperty(requiredAttribute, recipe.getAttribute(requiredAttribute));
            }
        }
        return object;
    }
}
