package ApiServer.Serializer;

import Model.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Thomas on 17-2-2016.
 */
public class UsersSerializer implements JsonSerializer<List<User>> {
    private final UserSerializer userSerializer = new UserSerializer();

    @Override
    public JsonElement serialize(List<User> users, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        JsonArray userArray = new JsonArray();
        for (User u : users) {
            userArray.add(userSerializer.serialize(u, type, jsonSerializationContext));
        }
        object.add("users", userArray);
        return object;
    }
}
