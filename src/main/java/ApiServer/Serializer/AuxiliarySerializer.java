package ApiServer.Serializer;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by s124392 on 16-3-2016.
 */
public class AuxiliarySerializer implements JsonSerializer<JsonObject> {

    @Override
    public JsonElement serialize(JsonObject jsonObject, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonObject;
    }
}
