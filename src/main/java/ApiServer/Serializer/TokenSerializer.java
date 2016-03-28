package ApiServer.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by s124392 on 23-2-2016.
 * Serializes an instance of {@code String} to a JSON-object. The string is assumed to be a valid authentication token.
 */
public class TokenSerializer implements JsonSerializer<String> {

    @Override
    public JsonElement serialize(String s, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("authToken", s); // Add the token under property name "authToken".
        return object;
    }
}
