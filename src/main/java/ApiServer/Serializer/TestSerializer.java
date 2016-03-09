package ApiServer.Serializer;

import Model.Test;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Thomas on 17-2-2016.
 */
public class TestSerializer implements JsonSerializer<Test> {
    @Override
    public JsonElement serialize(Test test, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
//        for (String s : test.getData()) {
//            array.add(jsonSerializationContext.serialize(s));
//        }
        object.add("data", array);
        return object;
    }
}
