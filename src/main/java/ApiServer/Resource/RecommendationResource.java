package ApiServer.Resource;

import ApiServer.Serializer.RecipeListSerializer;
import ApiServer.Status.IllegalStateStatus;
import Configuration.Database;
import Model.Recipe;
import Model.User;
import org.restlet.Request;
import org.restlet.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by s124392 on 2-3-2016.
 */
public class RecommendationResource extends ApiResource {
    /* The maximum number of recipes to be returned. */
    private static final int NUM_RECIPES = 5;

    @Override
    /**
     * Handles a HTTP GET request. This implements obtaining a recommendation based on user similarity from the server.
     */
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) { // The account is valid.
            updateTokenExpiration(account_id);

            /* Obtain additional user information for the user making the requests, and all other users in the database. */
            User user = Database.getInstance().getUserById(account_id);
            String userQuery = "SELECT `id`, `username` FROM `accounts` WHERE `id` != '" + account_id + "';";
            List<User> otherUsers = dataHandler.handleListUser(Database.getInstance().ExecuteQuery(userQuery,
                    new ArrayList<String>()));

            /* For every user in the database other than the user making the request, calculate the similarity with
            the user making the request.
             */
            Map<User, Integer> similarityMap = new HashMap();
            for (User other : otherUsers) {
                similarityMap.put(other, user.computeSimilarity(other));
            }
            similarityMap = sortByValue(similarityMap); // Sort the list of other uses by decreasing similarity.
            List<User> usersBySimilarity = new ArrayList();
            usersBySimilarity.addAll(similarityMap.keySet());
            List<Integer> recommendationIds = new ArrayList();
            int i = 0;
            /* Iterate over the list of other users until enough recipes have been found. */
            while (recommendationIds.size() < NUM_RECIPES && i < usersBySimilarity.size()) {
                User u = usersBySimilarity.get(i);
                List<Integer> diff = getFavoriteDifference(user, u); // Obtain the list of recipes the other user
                // has favorited, but the user making this request has not.
                for (int j = 0; j < Math.min(NUM_RECIPES - recommendationIds.size(), diff.size()); j++) {
                    /* Add every recipe in the difference to the result, stop if the number of required recipes
                    is reached.
                     */
                    utils.insertUniqueInteger(recommendationIds, diff.get(j));
                }
                i++;
            }
            /* Get a list of recipe identifiers of all recipes in the database sorted by popularity
            (e.g. aggregated favorite count).
             */
            List<Integer> recipesByPopularity = Database.getInstance().getRecipeIdsByPopularity();
            i = 0;

            /*
            Make up the deficit in recipes obtained from other users by the most popular recipes.
             */
            while(recommendationIds.size() < NUM_RECIPES) {
                utils.insertUniqueInteger(recommendationIds, recipesByPopularity.get(i));
                i++;
            }
            List<Recipe> recipes = new ArrayList();
            for (int j : recommendationIds) {
                recipes.add(getRecipeById(j));
            }
            this.returnResponse(response, recipes, new RecipeListSerializer());
        }
    }

    private List<Integer> getFavoriteDifference(User u, User v) {
        List<Integer> result = new ArrayList();
        Set<Integer> aux = new HashSet();
        for (int x : v.getFavorites()) {
            boolean notContained = true;
            for (int y : u.getFavorites()) {
                if (x == y) {
                    notContained = false;
                    break;
                }
            }
            if (notContained) {
                aux.add(x);
            }
        }
        result.addAll(aux);
        return result;
    }

    /**
     * Sorts an instance of {@code Map} on its value set, in descending order.
     * @param map the map to sort.
     * @param <K> the key set of the map to sort.
     * @param <V> the value set of the map to sort.
     * @return {@code Map}.
     */
    private <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );



        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
