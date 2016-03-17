package Spoonacular;

import Spoonacular.CompletionHandler.CompletionHandlerAbstract;
import Spoonacular.CompletionHandler.ListRecipeCompletionHandler;
import Spoonacular.CompletionHandler.RecipeCompletionHandler;
import Spoonacular.Model.Recipe;
import Spoonacular.ResultHandler.ResultHandlerAbstract;
import com.ning.http.client.AsyncHttpClient;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by s124392 on 16-3-2016.
 */
public class Spoonacular {
    private static Spoonacular instance = null;

    private static final String KEY_IDENTIFIER = "X-Mashape-Key";
    private static final String KEY = "W4KJVUe5hAmsh81Ta7Pa8lyGzkqGp1T46XPjsnrHNi8oUnUOqu";

    private Spoonacular() {
    }

    public static Spoonacular getInstance() {
        if (instance == null) {
            instance = new Spoonacular();
        }
        return instance;
    }

    public void queryByCuisine(String cuisine, ResultHandlerAbstract resultHandler) {
        Future<List<Recipe>> f = makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?cuisine=" + cuisine + "&includeIngredients=salt&limitLicense=false&maxCalories=3000&maxCarbs=3000&maxFat=3000&maxProtein=3000&minCalories=0&minCarbs=0&minFat=0&minProtein=0&number=10&offset=0&query=&ranking=1&type=main+course", new ListRecipeCompletionHandler());
        try {
            resultHandler.handle(f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void queryById(String id, ResultHandlerAbstract resultHandler) {
        Future<Recipe> f = makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information", new RecipeCompletionHandler(id));
        try {
            resultHandler.handle(f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Future makeRequest(String url, CompletionHandlerAbstract completionHandler) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder brb = asyncHttpClient.prepareGet(url);
        brb = brb.addHeader(KEY_IDENTIFIER, KEY);
        return brb.execute(completionHandler);
    }
}
