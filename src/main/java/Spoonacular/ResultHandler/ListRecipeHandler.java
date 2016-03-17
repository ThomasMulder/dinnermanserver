package Spoonacular.ResultHandler;

import Spoonacular.Model.Recipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by s124392 on 16-3-2016.
 */
public class ListRecipeHandler extends ResultHandlerAbstract {

    @Override
    public void handle(Object result) {
        List<Recipe> recipeList = (List<Recipe>) result;
//        for (Recipe r : recipeList) {
//            System.out.println(r.toString());
//        }
        String[] requiredAttributes = {"title", "id", "preparationMinutes"};
        System.out.println(listToJson(recipeList, requiredAttributes));
    }

    private JsonObject listToJson(List<Recipe> recipes, String[] requiredAttributes) {
        JsonArray jsonRecipes = new JsonArray();
        for (Recipe recipe : recipes) {
            if (recipe.isValidRecipe(requiredAttributes)) {
                jsonRecipes.add(recipeToJson(recipe, requiredAttributes));
            }
        }
        JsonObject object = new JsonObject();
        object.add("recipes", jsonRecipes);
        return object;
    }
}
