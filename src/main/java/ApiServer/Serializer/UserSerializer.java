package ApiServer.Serializer;

import Model.User;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Thomas on 17-2-2016.
 * Serializes an instance of {@code User} to a JSON-object.
 */
public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("username", user.getUsername()); // Add username property.
        JsonArray favorites = new JsonArray();
        for (int id : user.getFavorites()) { // Separately serialize the favorites list and add as array.
            favorites.add(jsonSerializationContext.serialize(id));
        }
        object.add("favorites", favorites);
        JsonArray allergens = new JsonArray();
        for (String allergen : user.getAllergens()) { // Separately serialize the allergens list and add as array.
            JsonObject obj = new JsonObject();
            obj.addProperty("allergen", allergen);
            allergens.add(obj);
        }
        object.add("allergens", allergens);
        JsonArray meals = new JsonArray();
        for (int id : user.getMeals()) { // Separately serialize the meals list and add as array.
            meals.add(jsonSerializationContext.serialize(id));
        }
        object.add("meals", meals);
        return object;
    }
}
