package ApiServer.Serializer;

import Model.Recipe;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by s124392 on 22-3-2016.
 * Serializes an instance of {@code List<Recipe>} to a JSON-object.
 */
public class RecipeListSerializer implements JsonSerializer<List<Recipe>> {

    @Override
    public JsonElement serialize(List<Recipe> recipes, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray array = new JsonArray(); // Build JSON-array.
        for (Recipe recipe : recipes) { // Serialize each recipe using its own method, add to array.
            array.add(recipe.toJson());
        }
        JsonObject object = new JsonObject();
        object.add("recipes", array); // Add array to object.
        return object;
    }
}
