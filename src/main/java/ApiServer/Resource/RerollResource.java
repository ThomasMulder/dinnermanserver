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
    /**
     * Handles a HTTP GET request. This implements rerolling (e.g. obtaining one new recipe in a schedule).
     */
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // The account is valid.
            updateTokenExpiration(account_id);

            /* Obtain the schedule string. This is a comma separated string describing the context of the reroll.
            That is, it describes the adjacent cuisines to the day that has to be rerolled, in order to maintain the
            requirement that no two consecutive days in the schedule have a recipe of the same cuisine.

            Example schedule strings are (1) dutch,reroll and (2) reroll,dutch and (3) dutch,reroll,german.
            Case (1) and (2) are equivalent, a reroll is requested such that the cuisine is not dutch.
            Case (3) imposes two constraints, namely that the cuisine may not be dutch nor german. The order of the
            terms is important, hence german,dutch,reroll does not mean the same as (3), but rather means the same as
            (1) and (2).
             */
            String scheduleString = String.valueOf(request.getAttributes().get("schedule"));
            String[] cuisineSchedule = scheduleString.split(",");

            int rerollIndex = -1; // the index of the day to be rerolled.
            for (int i = 0; i < cuisineSchedule.length; i++) {
                String left = null;
                String instance = cuisineSchedule[i];
                String right = null;
                if (instance.equals("reroll")) { // The reroll day has been found.
                    rerollIndex = i;
                    if (i > 0) { // The reroll day has a left-hand neighbour.
                        left = cuisineSchedule[i - 1];
                    }
                    if (i < (cuisineSchedule.length - 1)) { // The reroll day has a right-hand neighbour.
                        right = cuisineSchedule[i + 1];
                    }
                    /* Attempt the find a complete cuisine schedule (e.g. one where a recipe is available for each
                    cuisine in the schedule). Make at most SCHEDULE_MAX_ATTEMPTS attempts.
                     */
                    int attempts = 1;
                    cuisineSchedule[i] = findNewCuisine(left, right);
                    while (!isCompleteSchedule(account_id, cuisineSchedule) && attempts < SCHEDULE_MAX_ATTEMPTS) {
                        cuisineSchedule[i] = findNewCuisine(left, right);
                    }
                }

            }
            if (rerollIndex == -1) { // The schedule string was not understood.
                this.returnStatus(response, new IllegalArgumentStatus(null));
            } else { // A new schedule was successfully generated.
                /* Find the recipes for the rerolled cuisine and intersect them with the recipes allowed with respect
                to the user's allergens. Pick a random recipe from the intersection.
                 */
                List<Integer> allowedByCuisine = getAllowedCuisineIds(account_id, cuisineSchedule[rerollIndex]);
                List<Integer> allowedByAllergens = Database.getInstance().getAllowedRecipeIds(account_id);
                List<Integer> allowedIds = utils.getListIntegerIntersection(allowedByCuisine, allowedByAllergens);
                int id = allowedIds.get(utils.getRandomIndexFromList(allowedIds));
                makeRecipeResponse(response, id);
            }
        }
    }

    /**
     * Finds a new cuisine given one or two disallowed cuisines.
     * @param left the left-hand neighbour e.g. disallowed cuisine number one.
     * @param right the right-hand neighbour e.g. disallowed cuisine number two.
     * @return {@code String}
     */
    private String findNewCuisine(String left, String right) {
        List<String> cuisines = Database.getInstance().getCuisines();
        String result = cuisines.get(utils.getRandomIndexFromList(cuisines));
        while ((left != null && left.equals(result) || (right != null && right.equals(result)))) {
            result = cuisines.get(utils.getRandomIndexFromList(cuisines));
        }
        return result;
    }
}
