package ApiTest.ApiResourceTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by s124392 on 30-3-2016.
 */
public class RecipeListTest extends ApiResourceTestAbstract {
    private RecipeTest recipeTest = new RecipeTest();

    @Override
    public boolean test(String responseCode, String responseBody) {
        JsonObject object = getJson(responseBody);
        if (!responseCode.equals("200")) {
            return false;
        }
        if (!object.has("recipes")) {
            return false;
        } else {
            JsonArray array = object.getAsJsonArray("recipes");
            for (JsonElement element : array) {
                JsonObject aux = element.getAsJsonObject();
                if (!this.recipeTest.test(responseCode, aux.toString())) {
                    return false;
                }
            }
            return true;
        }
    }
}
