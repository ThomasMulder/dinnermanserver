package Spoonacular.CompletionHandler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.Response;

/**
 * Created by Thomas on 17-3-2016.
 */
public class StringCompletionHandler extends CompletionHandlerAbstract<String> {

    @Override
    public String onCompleted(Response response) throws Exception {
        JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
        return getStringProperty(jsonObject, "summary");
    }
}
