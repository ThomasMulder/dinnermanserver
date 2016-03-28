package ApiServer;

import ApiServer.Resource.*;
import Configuration.Properties;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
/**
 * Created by Thomas on 17-2-2016.
 * Describes the ApiServer and attaches the necessary end-points.
 */
public class ApiServer {

    public static void startServer() throws Exception {
        final Router router = new Router();
        /* Attach end-points. */
        router.attach("/authenticate/{username}/{password}", new AuthenticateResource());
        router.attach("/user/{username}/{authToken}/addFavorites", new FavoritesResource());
        router.attach("/user/{username}/{authToken}/deleteFavorites", new FavoritesResource());
        router.attach("/user/{username}/{authToken}/addAllergens", new AllergensResource());
        router.attach("/user/{username}/{authToken}/deleteAllergens", new AllergensResource());
        router.attach("/user/{username}/{authToken}/addMeals", new MealsResource());
        router.attach("/user/{username}/{authToken}/deleteMeals", new MealsResource());
        router.attach("/user/{username}/{authToken}/profile", new UserResource());
        router.attach("/recipe/{username}/{authToken}/specific/{id}", new RecipeResource());
        router.attach("/recipe/{username}/{authToken}/random", new RandomResource());
        router.attach("/recipe/{username}/{authToken}/schedule/{days}", new ScheduleResource());
        router.attach("/recipe/{username}/{authToken}/recommendation", new RecommendationResource());
        router.attach("/recipe/{username}/{authToken}/schedule/reroll/{schedule}", new RerollResource());
        router.attach("/recipe/{username}/{authToken}/use/{ingredients}", new IngredientResource());
        router.attach("/recipe/{username}/{authToken}/nutrition/{minCalories}/{maxCalories}/{minFat}/{maxFat}/" +
                "{minProtein}/{maxProtein}/{minCarbs}/{maxCarbs}", new NutritionResource());
        router.attach("/test", new TestResource());

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
