package ApiTest.ApiResourceTest;

import com.google.gson.JsonObject;

/**
 * Created by s124392 on 30-3-2016.
 */
public class RecipeTest extends ApiResourceTestAbstract {
    private static final String[] REQUIRED_ATTRIBUTES = {"id", "title", "image", "cuisine", "calories", "fat", "protein",
            "carbs", "servings", "preparationMinutes", "cookingMinutes", "readyInMinutes", "summary", "instructions",
            "ingredients"};

    @Override
    public boolean test(String responseCode, String responseBody) {
        if (!responseCode.equals("200")) {
            return false;
        }
        JsonObject object = getJson(responseBody);
        for (String requiredAttribute : REQUIRED_ATTRIBUTES) {
            if (!object.has(requiredAttribute)) {
                return false;
            }
            if (object.get(requiredAttribute).getAsString().length() < 1) {
                return false;
                // May be extended to test constraints on the attributes more rigorously.
            }
        }
        return true;
    }

}
