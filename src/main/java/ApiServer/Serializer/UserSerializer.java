package ApiServer.Serializer;

import Model.User;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Thomas on 17-2-2016.
 */
public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("username", user.getUsername());
        JsonArray favorites = new JsonArray();
        for (int id : user.getFavorites()) {
            favorites.add(jsonSerializationContext.serialize(id));
        }
        object.add("favorites", favorites);
        JsonArray allergens = new JsonArray();
        for (int id : user.getAllergens()) {
            allergens.add(jsonSerializationContext.serialize(id));
        }
        object.add("allergens", allergens);
        JsonArray meals = new JsonArray();
        for (int id : user.getMeals()) {
            meals.add(jsonSerializationContext.serialize(id));
        }
        object.add("meals", meals);
        return object;
    }
}
