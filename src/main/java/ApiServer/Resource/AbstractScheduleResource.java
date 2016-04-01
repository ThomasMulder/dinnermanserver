package ApiServer.Resource;

import Configuration.Database;
import Processing.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 21-3-2016.
 */
public abstract class AbstractScheduleResource extends ApiResource {
    protected static final int SCHEDULE_MAX_ATTEMPTS = 10;

    protected String[] findCuisineSchedule(int accountId, int days) {
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
        return cuisines.get(utils.getRandomIndexFromList(cuisines)).toLowerCase();
    }

    protected boolean isCompleteSchedule(int accountId, String[] schedule) {
        for (String cuisine : schedule) {
            List<Integer> cuisineIntersection = getAllowedCuisineIds(accountId, cuisine);
            if (cuisineIntersection.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected List<Integer> getAllowedCuisineIds(int accountId, String cuisine) {
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
        return Utils.getInstance().getListIntegerIntersection(cuisineIds, allowedIds);
    }


}
