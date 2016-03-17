package Spoonacular.ResultHandler;

import ApiServer.Resource.ApiResource;
import Configuration.Properties;
import Spoonacular.Model.Recipe;
import com.google.gson.JsonObject;
import org.restlet.Response;

/**
 * Created by s124392 on 16-3-2016.
 */
public abstract class ResultHandlerAbstract {
    private static final String[] allowedAttributes = Properties.getInstance().getProperty("allowedAttributes").split(",");

    protected String[] requiredAttributes;
    protected Response response;
    protected ApiResource resource;

    public ResultHandlerAbstract(Response response, ApiResource resource, String[] requiredAttributes) {
        this.response = response;
        this.resource = resource;
        this.requiredAttributes = getAttributeIntersection(requiredAttributes);
    }

    public abstract void handle(Object result);

    protected JsonObject recipeToJson(Recipe recipe) {
        JsonObject object = new JsonObject();
        for (String requiredAttribute : requiredAttributes) {
            if (requiredAttribute.equals("extendedIngredients")) {
                object.add("extendedIngredients", recipe.getInformation().getExtenededIngredientsAsJson());
            } else {
                object.addProperty(requiredAttribute, recipe.getAttribute(requiredAttribute));
            }
        }
        return object;
    }

    private String[] getAttributeIntersection(String[] requiredAttributes) {
        String[] intersection = new String[requiredAttributes.length];
        int i = 0;
        for (String required : requiredAttributes) {
            for (String allowed : allowedAttributes) {
                if (required.equals(allowed)) {
                    intersection[i] = required;
                    break;
                }
            }
            i++;
        }
        return trim(intersection);
    }

    private String[] trim(String[] array) {
        int actualSize = 0;
        for (String s : array) {
            if (!(s == null || s.equals(""))) {
                actualSize++;
            }
        }
        String[] result = new String[actualSize];
        int i = 0;
        for (String s : array) {
            if (!(s == null || s.equals(""))) {
                result[i] = s;
                i++;
            }
        }
        return result;
    }
}
