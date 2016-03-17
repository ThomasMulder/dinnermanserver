package Spoonacular.CompletionHandler;

import Spoonacular.Model.Information;
import Spoonacular.Model.Recipe;
import Spoonacular.Spoonacular;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Thomas on 17-3-2016.
 */
public class ListRecipeCompletionHandler extends CompletionHandlerAbstract<List<Recipe>> {

    @Override
    public List<Recipe> onCompleted(Response response) throws Exception {
        JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
        JsonArray resultsArray = jsonObject.getAsJsonArray("results");
        List<Recipe> results = new ArrayList();
        for (JsonElement e : resultsArray) {
            JsonObject o = e.getAsJsonObject();
            int id = getIntProperty(o, "id");
            Future<String> summaryResp = Spoonacular.getInstance().makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/summary", new StringCompletionHandler());
            Future<Information> informationResp = Spoonacular.getInstance().makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information", new InformationCompletionHandler());
            results.add(new Recipe(summaryResp.get(), informationResp.get()));
        }
        return results;
    }
}
