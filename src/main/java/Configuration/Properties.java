package Configuration;

import java.io.IOException;
import java.io.InputStreamReader;

public class Properties {
    /* Start Singleton */
    private static Properties instance = null;

    public static Properties getInstance() {
        if(instance == null) {
            instance = new Properties();
        }

        return instance;
    }
    /* End Singleton */

    private java.util.Properties prop;

    private Properties() {
        prop = new java.util.Properties();
        InputStreamReader input = null;

        try {
            input = new InputStreamReader(this.getClass().getResourceAsStream("/config.properties"));

            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get a property from the config.properties file.
     *
     * @param name Name of the requested property
     * @return The value of the requested property
     */
    public String getProperty(String name) {
        String result;
        try {
            result = prop.getProperty(name);
        }catch (NullPointerException e) {
            throw new IllegalStateException("Missing property in config.properties: "+name);
        }

        if(result != null && result.length() > 0) return result;
        throw new IllegalStateException("Missing property in config.properties: "+name);
    }
}
