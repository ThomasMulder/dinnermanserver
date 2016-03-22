package ApiServer.Serializer;

import Model.Recipe;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by s124392 on 22-3-2016.
 */
public class RecipeListSerializer implements JsonSerializer<List<Recipe>> {

    @Override
    public JsonElement serialize(List<Recipe> recipes, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray array = new JsonArray();
        for (Recipe recipe : recipes) {
            array.add(recipe.toJson());
        }
        JsonObject object = new JsonObject();
        object.add("recipes", array);
        return object;
    }
}
