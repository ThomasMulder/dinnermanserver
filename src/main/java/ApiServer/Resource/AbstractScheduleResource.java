package ApiServer.Resource;

import Configuration.Database;
import Processing.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 21-3-2016.
 * An abstract resource that is extended by both {@code ScheduleResource} and {@code RerollResource}.
 * It provides some shared functionality.
 */
public abstract class AbstractScheduleResource extends ApiResource {
    /* The maximum number of attempts at creating a complete schedule,
    before the last schedule is accepted regardless of completeness. */
    protected static final int SCHEDULE_MAX_ATTEMPTS = 10;

    /**
     * Attempts to create an array of cuisines (e.g. dutch, french, italian) for parameterised number of {@code days}.
     * The array should be such that no two consecutive entries are the same.
     *
     * The cuisine schedule should also be complete with respect to the user parameterised by its identifier
     * {@code accountId}.
     *
     * A schedule is complete with respect to a user when the user has a recipe available for each of the cuisines in
     * the schedule. A recipe may not be available due to the user's allergy settings.
     *
     * @param accountId the account identifier of the account to create a schedule for.
     * @param days the number of days to create a schedule for.
     * @return {@code String[]}.
     */
    protected String[] findCuisineSchedule(int accountId, int days) {
        int attempts = 1;
        String[] schedule = generateCuisineSchedule(days); // Generate a list of cuisines for the given number of days.
        while (!isCompleteSchedule(accountId, schedule) && attempts < SCHEDULE_MAX_ATTEMPTS) { // Schedule is incomplete
            // and there are still attempts available.
            schedule = generateCuisineSchedule(days); // Generate a new list of cuisines.
            attempts++;
        }
        return schedule;
    }

    /**
     * Generates a random cuisine array (e.g. dutch, french, italian) for parameterised number of {@code days}.
     * The array should be such that no two consecutive entries (e.g. cuisines) are the same.
     * @param days the number of days to create a schedule for.
     * @return {@code String[]}.
     */
    private String[] generateCuisineSchedule(int days) {
        String lastCuisine = "";
        String[] result = new String[days];
        for (int i = 0; i < days; i++) {
            String newCuisine = generateRandomCuisine(); // Generate a random cuisine from the available cuisines.
            while (newCuisine.equals(lastCuisine)) { // The new cuisine is equal to the last cuisine.
                newCuisine = generateRandomCuisine(); // Re-generate.
            }
            lastCuisine = newCuisine;
            result[i] = newCuisine;
        }
        return result;
    }

    /**
     * Generates a random cuisine from the available cuisines in the database.
     * @return {@code String}.
     */
    private String generateRandomCuisine() {
        List<String> cuisines = database.getCuisines(); // Get available cuisines from database.
        return cuisines.get(utils.getRandomIndexFromList(cuisines)).toLowerCase(); // Get random entry.
    }

    /**
     * Checks whether or not a given {@code String[] schedule} of cuisines is complete or not, with respect
     * to a user identifier by {@code accountId}.
     * @param accountId the identifier of the account to check schedule completeness for.
     * @param schedule the schedule to check completeness for.
     * @return {@code boolean}.
     */
    protected boolean isCompleteSchedule(int accountId, String[] schedule) {
        for (String cuisine : schedule) { // Iterate over all cuisines in the schedule.
            List<Integer> cuisineIntersection = getAllowedCuisineIds(accountId, cuisine); // Find the intersection with
            // the cuisines allowed for the user, and the current cuisine.
            if (cuisineIntersection.isEmpty()) { // There are no recipes available in this cuisine.
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a {@code List} of recipe identifiers for a given {@code String cuisine} which are allowed
     * with respect to the user's allergen settings.
     * @param accountId the identifier of the user account to find cuisines for.
     * @param cuisine the cuisine to check for recipes for.
     * @return
     */
    protected List<Integer> getAllowedCuisineIds(int accountId, String cuisine) {
        // Get the ids of allowed recipes for the user.
        List<Integer> allowedIds = Database.getInstance().getAllowedRecipeIds(accountId);
        String cuisineQuery = "SELECT `id` FROM `recipes` WHERE `cuisine` = '" + cuisine + "';";
        // Get the ids of recipes for specified cuisine.
        List<Integer> cuisineIds = dataHandler.handleListSingleInteger(database.ExecuteQuery(cuisineQuery,
                new ArrayList<String>()), 1);
        // Return the intersection of the two allowed id lists.
        return Utils.getInstance().getListIntegerIntersection(cuisineIds, allowedIds);
    }


}
