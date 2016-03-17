package Spoonacular.CompletionHandler;

import com.google.gson.JsonObject;
import com.ning.http.client.AsyncCompletionHandler;

/**
 * Created by Thomas on 17-3-2016.
 */
public abstract class CompletionHandlerAbstract<T> extends AsyncCompletionHandler<T> {

    protected int getIntProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsInt();
            } catch (UnsupportedOperationException u) {}
        }
        return -1;
    }

    protected double getDoubleProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsDouble();
            } catch (UnsupportedOperationException u) {}
        }
        return -1.0;
    }

    protected boolean getBooleanProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsBoolean();
            } catch (UnsupportedOperationException u) {}
        }
        return false;
    }

    protected String getStringProperty(JsonObject object, String name) {
        if (object.has(name)) {
            try {
                return object.get(name).getAsString();
            } catch (UnsupportedOperationException u) {}
        }
        return "";
    }
}
