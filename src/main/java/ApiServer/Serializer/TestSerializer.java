package ApiServer.Serializer;

import Model.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Thomas on 17-2-2016.
 */
public class TestSerializer implements JsonSerializer<Test> {
    @Override
    public JsonElement serialize(Test test, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.addProperty("value", test.value);
        return object;
    }
}
