package Spoonacular.ResultHandler;


import ApiServer.Resource.ApiResource;
import ApiServer.Serializer.AuxiliarySerializer;
import Spoonacular.Model.Recipe;
import org.restlet.Response;

import java.util.List;

/**
 * Created by s124392 on 18-3-2016.
 */
public class RandomHandler extends ResultHandlerAbstract {

    public RandomHandler(Response response, ApiResource resource, String[] requiredAttributes) {
        super(response, resource, requiredAttributes);
    }

    @Override
    public void handle(Object result) {
        List<Recipe> recipeList = (List<Recipe>) result;
        int index = (int) Math.round(Math.random() * recipeList.size());
        Recipe recipe = recipeList.get(index);
        resource.returnResponse(response, recipeToJson(recipe), new AuxiliarySerializer());
    }
}
