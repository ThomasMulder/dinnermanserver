package Spoonacular;

import Spoonacular.Model.ExtendedIngredient;
import Spoonacular.Model.Information;
import Spoonacular.Model.Recipe;
import Spoonacular.ResultHandler.ResultHandlerAbstract;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import java.util.ArrayList;
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

    public void makeRequest(String request, ResultHandlerAbstract resultHandler) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder brb = asyncHttpClient.prepareGet(request);
        brb = brb.addHeader(KEY_IDENTIFIER, KEY);
        Future<List<Recipe>> f = brb.execute(new AsyncCompletionHandler<List<Recipe>>() {
            @Override
            public List<Recipe> onCompleted(Response response) throws Exception {
                JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
                JsonArray resultsArray = jsonObject.getAsJsonArray("results");
                List<Recipe> results = new ArrayList();
                for (JsonElement e : resultsArray) {
                    JsonObject o = e.getAsJsonObject();
                    int id = getIntProperty(o, "id");
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    AsyncHttpClient.BoundRequestBuilder brb = asyncHttpClient.prepareGet("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/summary");
                    brb = brb.addHeader(KEY_IDENTIFIER, KEY);

                    Future<String> summaryResp = brb.execute(new AsyncCompletionHandler<String>() {

                        @Override
                        public String onCompleted(Response response) throws Exception {
                            JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
                            return getStringProperty(jsonObject, "summary");
                        }
                    });

                    brb = asyncHttpClient.prepareGet("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information");
                    brb = brb.addHeader(KEY_IDENTIFIER, KEY);
                    Future<Information> informationResp = brb.execute(new AsyncCompletionHandler<Information>() {

                        @Override
                        public Information onCompleted(Response response) throws Exception {
                            JsonObject jsonObject = (new JsonParser()).parse(response.getResponseBody()).getAsJsonObject();
                            String sourceUrl = getStringProperty(jsonObject, "sourceUrl");
                            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                            AsyncHttpClient.BoundRequestBuilder brb = asyncHttpClient.prepareGet("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/extract?forceExtraction=false&url=" + sourceUrl);
                            brb = brb.addHeader(KEY_IDENTIFIER, KEY);

                            Future<Information> sourceResp = brb.execute(new AsyncCompletionHandler<Information>() {

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
                            });
                            return sourceResp.get();
                        }
                    });
                    results.add(new Recipe(summaryResp.get(), informationResp.get()));
                }
                return results;
            }
        });
        try {
            resultHandler.handle(f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static int getIntProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsInt();
            } catch (UnsupportedOperationException u) {}
        }
        return -1;
    }

    private static double getDoubleProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsDouble();
            } catch (UnsupportedOperationException u) {}
        }
        return -1.0;
    }

    private static boolean getBooleanProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsBoolean();
            } catch (UnsupportedOperationException u) {}
        }
        return false;
    }

    private static String getStringProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsString();
            } catch (UnsupportedOperationException u) {}
        }
        return "";
    }
}
