package ApiServer.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by s124392 on 23-2-2016.
 */
public class TokenSerializer implements JsonSerializer<String> {
    private int mode;
    public static final String[] MODELIST = {"authToken"};

    public TokenSerializer(int mode) {
        this.mode = mode;
    }

    @Override
    public JsonElement serialize(String s, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty(MODELIST[this.mode], s);
        return object;
    }
}
