package Spoonacular.CompletionHandler;

import Spoonacular.Model.ExtendedIngredient;
import Spoonacular.Model.Information;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.Response;

/**
 * Created by Thomas on 17-3-2016.
 */
public class SourceCompletionHandler extends CompletionHandlerAbstract<Information> {

    @Override
    public Information onCompleted(Response response) throws Exception {
        JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
        //System.out.println(response.getResponseBody());
        boolean vegetarian = getBooleanProperty(jsonObject, "vegetarian");
        boolean vegan = getBooleanProperty(jsonObject, "vegan");
        boolean glutenFree = getBooleanProperty(jsonObject, "glutenFree");
        boolean dairyFree = getBooleanProperty(jsonObject, "dairyFree");
        boolean veryHealthy = getBooleanProperty(jsonObject, "veryHealthy");
        boolean cheap = getBooleanProperty(jsonObject, "cheap");
        boolean veryPopular = getBooleanProperty(jsonObject, "veryPopular");
        boolean sustainable = getBooleanProperty(jsonObject, "sustainable");
        int weightWatcherSmartPoints = getIntProperty(jsonObject, "weightWatcherSmartPoints");
        String gaps = getStringProperty(jsonObject, "gaps");
        boolean lowFodmap = getBooleanProperty(jsonObject, "lowFodmap");
        boolean ketogenic = getBooleanProperty(jsonObject, "ketogenic");
        boolean whole30 = getBooleanProperty(jsonObject, "whole30");
        int servings = getIntProperty(jsonObject, "servings");
        int preparationMinutes = getIntProperty(jsonObject, "preparationMinutes");
        int cookingMinutes = getIntProperty(jsonObject, "cookingMinutes");
        String sourceUrl = getStringProperty(jsonObject, "sourceUrl");
        String spoonacularSourceUrl = getStringProperty(jsonObject, "spoonacularSourceUrl");
        int aggregateLikes = getIntProperty(jsonObject, "aggregateLikes");
        JsonArray auxExtendedIngredients = jsonObject.get("extendedIngredients").getAsJsonArray();
        ExtendedIngredient[] extendedIngredients = new ExtendedIngredient[auxExtendedIngredients.size()];
        int i = 0;
        for (JsonElement e : auxExtendedIngredients) {
            JsonObject o = e.getAsJsonObject();
            String aisle = getStringProperty(o, "aisle");
            String name = getStringProperty(o, "name");
            double amount = getDoubleProperty(o, "amount");
            String unit = getStringProperty(o, "unit");
            String unitShort = getStringProperty(o, "unitShort");
            String unitLong = getStringProperty(o, "unitLong");
            String originalString = getStringProperty(o, "originalString");
            extendedIngredients[i] = new ExtendedIngredient(aisle, name, amount, unit,
                    unitShort, unitLong, originalString);
            i++;
        }
        int id = getIntProperty(jsonObject, "id");
        String title = getStringProperty(jsonObject, "title");
        int readyInMinutes = getIntProperty(jsonObject, "readyInMinutes");
        String image = getStringProperty(jsonObject, "image");
        String text = getStringProperty(jsonObject, "text");
        String instructions = getStringProperty(jsonObject, "instructions");
        return new Information(vegetarian, vegan, glutenFree, dairyFree, veryHealthy, cheap,
                veryPopular, sustainable, lowFodmap, ketogenic, whole30,
                weightWatcherSmartPoints, servings, preparationMinutes, cookingMinutes,
                aggregateLikes, id, readyInMinutes, gaps, sourceUrl, spoonacularSourceUrl,
                title, image, text, instructions, extendedIngredients);
    }
}
