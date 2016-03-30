package ApiTest.ApiResourceTest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by s124392 on 30-3-2016.
 */
public abstract class ApiResourceTestAbstract {

    public abstract boolean test(String responseCode, String responseBody);

    protected JsonObject getJson(String string) {
        JsonParser parser = new JsonParser();
        return parser.parse(string).getAsJsonObject();
    }
}
