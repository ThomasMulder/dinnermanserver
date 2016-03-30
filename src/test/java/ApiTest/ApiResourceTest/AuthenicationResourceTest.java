package ApiTest.ApiResourceTest;

import com.google.gson.JsonObject;

/**
 * Created by s124392 on 30-3-2016.
 */
public class AuthenicationResourceTest extends ApiResourceTestAbstract {

    @Override
    public boolean test(String responseCode, String responseBody) {
        if (!responseCode.equals("200")) {
            return false;
        }
        JsonObject object = getJson(responseBody);
        return (object.has("authToken") && object.get("authToken").getAsString().length() == 16);
    }
}
