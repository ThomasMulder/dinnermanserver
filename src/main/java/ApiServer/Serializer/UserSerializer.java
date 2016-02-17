package ApiServer.Serializer;

import Model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Thomas on 17-2-2016.
 */
public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("id", user.getId());
        object.addProperty("name", user.getName());
        object.addProperty("email", user.getEmail());
        return object;
    }
}
