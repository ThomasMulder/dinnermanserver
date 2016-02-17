package ApiServer.Resource;

import ApiServer.Serializer.UsersSerializer;
import Configuration.Database;
import Model.User;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 17-2-2016.
 */
public class UsersResource extends ApiResource {
    @Override
    protected void handleGet(Request request, Response response) throws IllegalStateException {
        ResultSet result = Database.getInstance().ExecuteQuery("SELECT * FROM `users` LIMIT 0,1000;", new ArrayList<String>());
        List<User> users = new ArrayList();
        if (result != null) {
            try {
                while (result.next()) {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String email = result.getString(3);
                    users.add(new User(id, name, email));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.returnResponse(response, users, new UsersSerializer());
        }
    }
}
