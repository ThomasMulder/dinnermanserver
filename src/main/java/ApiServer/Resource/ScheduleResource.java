package ApiServer.Resource;

import ApiServer.Serializer.ScheduleSerializer;
import Configuration.Database;
import Model.Recipe;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 2-3-2016.
 */
public class ScheduleResource extends ApiResource {
    private static final int SCHEDULE_MAX_ATTEMPTS = 10;

    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            int days = Integer.parseInt(String.valueOf(request.getAttributes().get("days")));
            String[] cuisineSchedule = findCuisineSchedule(account_id, days);
            List<Recipe> schedule = new ArrayList();
            for (String cuisine : cuisineSchedule) {
                List<Integer> ids = getAllowedCuisineIds(account_id, cuisine);
                int index = (int) Math.round(Math.random() * (ids.size() - 1));
                int id = ids.get(index);
                schedule.add(getRecipeById(id));
            }
            this.returnResponse(response, schedule, new ScheduleSerializer());
        }
    }

    private String[] findCuisineSchedule(int accountId, int days) {
        int attempts = 1;
        String[] schedule = generateCuisineSchedule(days);
        while (!isCompleteSchedule(accountId, schedule) && attempts < SCHEDULE_MAX_ATTEMPTS) {
            schedule = generateCuisineSchedule(days);
            attempts++;
        }
        return schedule;
    }

    private String[] generateCuisineSchedule(int days) {
        String lastCuisine = "";
        String[] result = new String[days];
        for (int i = 0; i < days; i++) {
            String newCuisine = generateRandomCuisine();
            while (newCuisine.equals(lastCuisine)) {
                newCuisine = generateRandomCuisine();
            }
            lastCuisine = newCuisine;
            result[i] = newCuisine;
        }
        return result;
    }

    private String generateRandomCuisine() {
        List<String> cuisines = Database.getInstance().getCuisines();
        int index = (int) Math.round(Math.random() * (cuisines.size() - 1));
        return cuisines.get(index).toLowerCase();
    }

    private boolean isCompleteSchedule(int accountId, String[] schedule) {
        for (String cuisine : schedule) {
            List<Integer> cuisineIntersection = getAllowedCuisineIds(accountId, cuisine);
            if (cuisineIntersection.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> getAllowedCuisineIds(int accountId, String cuisine) {
        List<Integer> allowedIds = Database.getInstance().getAllowedRecipeIds(accountId);
        List<Integer> cuisineIds = new ArrayList();
        String cuisineQuery = "SELECT `id` FROM `recipes` WHERE `cuisine` = '" + cuisine + "';";
        ResultSet cuisineResult = Database.getInstance().ExecuteQuery(cuisineQuery, new ArrayList<String>());
        try {
            while (cuisineResult.next()) {
                cuisineIds.add(cuisineResult.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getIntegerListIntersection(cuisineIds, allowedIds);
    }

    private List<Integer> getIntegerListIntersection(List<Integer> a, List<Integer> b) {
        List<Integer> result = new ArrayList();
        for (int x : a) {
            for (int y : b) {
                if (x == y) {
                    result.add(x);
                    break;
                }
            }
        }
        return result;
    }
}
