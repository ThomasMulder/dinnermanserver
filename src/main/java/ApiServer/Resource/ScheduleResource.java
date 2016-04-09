package ApiServer.Resource;

import ApiServer.Serializer.ScheduleSerializer;

import Configuration.Database;
import Model.Recipe;
import org.restlet.Request;
import org.restlet.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 2-3-2016.
 */
public class ScheduleResource extends AbstractScheduleResource {


    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            int days = Integer.parseInt(String.valueOf(request.getAttributes().get("days")));
            String[] cuisineSchedule = findCuisineSchedule(account_id, days);
            List<Recipe> schedule = new ArrayList();
            for (String cuisine : cuisineSchedule) {
                List<Integer> allowedByCuisine = getAllowedCuisineIds(account_id, cuisine);
                List<Integer> allowedByAllergen = Database.getInstance().getAllowedRecipeIds(account_id);
                List<Integer> allowedIds = utils.getListIntegerIntersection(allowedByCuisine, allowedByAllergen);
                if (!allowedIds.isEmpty()) {
                    int id = allowedIds.get(utils.getRandomIndexFromList(allowedIds));
                    schedule.add(getRecipeById(id));
                }
            }
            this.returnResponse(response, schedule, new ScheduleSerializer());
        }
    }
}
