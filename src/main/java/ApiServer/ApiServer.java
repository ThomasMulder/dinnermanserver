package ApiServer;

import ApiServer.Resource.TestResource;
import ApiServer.Resource.UsersResource;
import Configuration.Properties;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
/**
 * Created by Thomas on 17-2-2016.
 */
public class ApiServer {

    public static void startServer() throws Exception {
        final Router router = new Router();
        router.attach("/test", new TestResource());
        router.attach("/users", new UsersResource());

        Application restRouter = new Application() {
            @Override
            public org.restlet.Restlet createInboundRoot() {
                router.setContext(getContext());
                return router;
            };
        };

        Component component = new Component();
        component.getDefaultHost().attach("/api", restRouter);

        new Server(Protocol.HTTP, Integer.parseInt(Properties.getInstance().getProperty("restApiPort")), component).start();
    }
}
