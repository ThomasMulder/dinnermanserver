package ApiServer.Resource;

import Model.Test;
import ApiServer.Serializer.TestSerializer;
import Spoonacular.ResultHandler.ListRecipeHandler;
import Spoonacular.ResultHandler.RecipeHandler;
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
//        String attributeString = String.valueOf(request.getAttributes().get("requiredAttributes"));
//        String[] requiredAttributes = attributeString.split(",");
//        //Spoonacular.getInstance().queryByCuisine("dutch", new ListRecipeHandler(response, this, requiredAttributes));
//        Spoonacular.getInstance().queryById("112013", new RecipeHandler(response, this, requiredAttributes));
    }
}
