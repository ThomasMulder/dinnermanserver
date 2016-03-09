package ApiServer.Resource;

import ApiServer.Serializer.UserSerializer;
import ApiServer.Status.IllegalArgumentStatus;
import ApiServer.Status.SuccessStatus;
import Configuration.Database;
import Model.User;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 2-3-2016.
 */
public class UserResource extends ApiResource {
    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            String username = String.valueOf(request.getAttributes().get("username"));
            List<Integer> favorites = new ArrayList();
            List<Integer> allergens = new ArrayList();
            List<Integer> meals = new ArrayList();
            ResultSet result = Database.getInstance().ExecuteQuery("SELECT `recipe_id` FROM `favorites` WHERE `account_id` = '" + account_id + "';", new ArrayList<String>());
            try {
                while (result.next()) {
                    favorites.add(result.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            result = Database.getInstance().ExecuteQuery("SELECT `allergen_id` FROM `allergens` WHERE `account_id` = '" + account_id + "';", new ArrayList<String>());
            try {
                while (result.next()) {
                    allergens.add(result.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            result = Database.getInstance().ExecuteQuery("SELECT `meal_id` FROM `meals` WHERE `account_id` = '" + account_id + "';", new ArrayList<String>());
            try {
                while (result.next()) {
                    meals.add(result.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.returnResponse(response, new User(username, favorites, allergens, meals), new UserSerializer());
        } else {
            this.returnStatus(response, new IllegalArgumentStatus(null));
        }
    }
}
