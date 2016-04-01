package ApiServer.Resource;

import ApiServer.Serializer.ScheduleSerializer;
import ApiServer.Status.IllegalArgumentStatus;
import Configuration.Database;
import Model.Recipe;
import org.restlet.Request;
import org.restlet.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 2-3-2016.
 */
public class RerollResource extends AbstractScheduleResource {

    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            String scheduleString = String.valueOf(request.getAttributes().get("schedule"));
            String[] cuisineSchedule = scheduleString.split(",");
            int rerollIndex = -1;
            for (int i = 0; i < cuisineSchedule.length; i++) {
                String left = null;
                String instance = cuisineSchedule[i];
                String right = null;
                if (instance.equals("reroll")) {
                    rerollIndex = i;
                    if (i > 0) {
                        left = cuisineSchedule[i - 1];
                    }
                    if (i < (cuisineSchedule.length - 1)) {
                        right = cuisineSchedule[i + 1];
                    }
                    int attempts = 1;
                    cuisineSchedule[i] = findNewCuisine(left, right);
                    while (!isCompleteSchedule(account_id, cuisineSchedule) && attempts < SCHEDULE_MAX_ATTEMPTS) {
                        cuisineSchedule[i] = findNewCuisine(left, right);
                    }
                }

            }
            if (rerollIndex == -1) {
                this.returnStatus(response, new IllegalArgumentStatus(null));
            } else {
                List<Integer> allowedByCuisine = getAllowedCuisineIds(account_id, cuisineSchedule[rerollIndex]);
                List<Integer> allowedByAllergens = Database.getInstance().getAllowedRecipeIds(account_id);
                List<Integer> allowedIds = utils.getListIntegerIntersection(allowedByCuisine, allowedByAllergens);
                int id = allowedIds.get(utils.getRandomIndexFromList(allowedIds));
                makeRecipeResponse(response, id);
            }
        }
    }

    private String findNewCuisine(String left, String right) {
        List<String> cuisines = Database.getInstance().getCuisines();
        String result = cuisines.get(utils.getRandomIndexFromList(cuisines));
        while ((left != null && left.equals(result) || (right != null && right.equals(result)))) {
            result = cuisines.get(utils.getRandomIndexFromList(cuisines));
        }
        return result;
    }
}
