/**
 * Created by Thomas on 17-2-2016.
 */

import ApiServer.ApiServer;
import Configuration.Database;
import Configuration.Properties;
import Processing.Maintenance;

/**
 * Main class of the application, used to initialise all services.
 */
public class Main {
    //private static Maintenance maintenance;

    /**
     * Starts services.
     * @param args
     */
    public static void main(String[] args) {
        Main.loadProperties();
        Main.initDatabases();
        Main.startServices();
    }

    /**
     * Instantiate the {@code Properties} singleton.
     */
    private static void loadProperties() { Properties.getInstance(); }

    /**
     * Instantiate the {@code Database} singleton.
     */
    private static void initDatabases() { Database.getInstance().init(); }

    /**
     * Start the ApiServer.
     */
    private static void startServices() {
        try {
            ApiServer.startServer();
            //maintenance = new Maintenance();
            //maintenance.startService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
