/**
 * Created by Thomas on 17-2-2016.
 */

import ApiServer.ApiServer;
import Configuration.Database;
import Configuration.Properties;

public class Main {
    public static void main(String[] args) {
        Main.loadProperties();
        Main.initDatabases();
        Main.startServices();
    }

    private static void loadProperties() { Properties.getInstance(); }

    private static void initDatabases() { Database.getInstance().init(); }

    private static void startServices() {
        try {
            ApiServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
