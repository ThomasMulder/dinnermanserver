package ApiServer.Serializer;

import com.google.gson.GsonBuilder;

public class Serializer {
    private static GsonBuilder builder = null;

    protected Serializer() {}

    public static GsonBuilder getInstance()
    {
        if(builder == null) {
            builder = new GsonBuilder();
            //Example of adding a custom SerializerClass
            //builder.registerTypeAdapter(InteractivityAbstract.class, new InteractivitySerializer());
        }

        return builder;
    }
}
