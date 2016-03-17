package Spoonacular.CompletionHandler;

import Spoonacular.Model.Information;
import Spoonacular.Model.Recipe;
import Spoonacular.Spoonacular;
import com.ning.http.client.Response;

import java.util.concurrent.Future;

/**
 * Created by Thomas on 17-3-2016.
 */
public class RecipeCompletionHandler extends CompletionHandlerAbstract<Recipe> {
    private String id;

    public RecipeCompletionHandler(String id) {
        this.id = id;
    }

    @Override
    public Recipe onCompleted(Response response) throws Exception {
        Future<String> summaryResp = Spoonacular.getInstance().makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/summary", new StringCompletionHandler());
        Future<Information> informationResp = Spoonacular.getInstance().makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information", new InformationCompletionHandler());
        return new Recipe(summaryResp.get(), informationResp.get());
    }
}
