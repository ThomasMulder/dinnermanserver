package Spoonacular.CompletionHandler;

import Spoonacular.Model.Information;
import Spoonacular.Spoonacular;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.Response;

import java.util.concurrent.Future;

/**
 * Created by Thomas on 17-3-2016.
 */
public class InformationCompletionHandler extends CompletionHandlerAbstract<Information> {

    @Override
    public Information onCompleted(Response response) throws Exception {
        JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
        String sourceUrl = getStringProperty(jsonObject, "sourceUrl");
        Future<Information> sourceResp = Spoonacular.getInstance().makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/extract?forceExtraction=false&url=" + sourceUrl, new SourceCompletionHandler());
        return sourceResp.get();
    }
}
