package ApiServer.Resource;

import Model.Test;
import ApiServer.Serializer.TestSerializer;
import Spoonacular.ResultHandler.ListRecipeHandler;
import org.restlet.Request;
import org.restlet.Response;
import Spoonacular.Spoonacular;

import java.util.ArrayList;

/**
 * Created by Thomas on 17-2-2016.
 */
public class TestResource extends ApiResource {
    @Override
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        Spoonacular.getInstance().makeRequest("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?cuisine=african&includeIngredients=salt&limitLicense=false&maxCalories=3000&maxCarbs=3000&maxFat=3000&maxProtein=3000&minCalories=0&minCarbs=0&minFat=0&minProtein=0&number=10&offset=0&query=&ranking=1&type=main+course", new ListRecipeHandler(this, response));
    }
}
