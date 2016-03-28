package ApiServer.Serializer;

import Model.Recipe;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by s124392 on 20-3-2016.
 * Serializes an instance of {@code List<Recipe>} to a JSON-object.
 * Almost identical to {@code RecipeListSerializer}, differs only in attribute name ("schedule" vs. "recipes").
 */
public class ScheduleSerializer implements JsonSerializer<List<Recipe>> {


    @Override
    public JsonElement serialize(List<Recipe> recipes, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray array = new JsonArray();
        for (Recipe recipe : recipes) {
            array.add(recipe.toJson());
        }
        JsonObject object = new JsonObject();
        object.add("schedule", array);
        return object;
    }
}
