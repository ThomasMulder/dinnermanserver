package ApiServer.Resource;

import ApiServer.Status.IllegalStateStatus;
import Configuration.Database;
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

    @Override
    protected void handleGet(Request request, Response response) throws  IllegalArgumentException {
        int account_id = getAccountId(request, response);
        if (account_id >= 0) {
            updateTokenExpiration(account_id);
            String username = String.valueOf(request.getAttributes().get("username"));
            User user = getUser(account_id, username);
            List<User> otherUsers = new ArrayList();
            String userQuery = "SELECT `id`, `username` FROM `accounts` WHERE `id` != '" + account_id + "';";
            ResultSet userResults = Database.getInstance().ExecuteQuery(userQuery, new ArrayList<String>());
            try {
                while (userResults.next()) {
                    int id = userResults.getInt(1);
                    String name = userResults.getString(2);
                    otherUsers.add(getUser(id, name));
                }
            } catch (SQLException e) {
                this.returnStatus(response, new IllegalStateStatus(null));
                e.printStackTrace();
            }
            Map<User, Integer> similarityMap = new HashMap();
            for (User other : otherUsers) {
                similarityMap.put(other, user.computeSimilarity(other));
            }
            //similarityMap = sortByValue(similarityMap);
            User mostSimilar = null;
            int highestScore = Integer.MIN_VALUE;
            for (Map.Entry<User, Integer> entry : similarityMap.entrySet()) {
                if (highestScore < entry.getValue() && !getFavoriteMealDifference(user, entry.getKey()).isEmpty()) {
                    mostSimilar = entry.getKey();
                    highestScore = entry.getValue();
                }
            }
            if (mostSimilar == null) {
                String recipeQuery = "SELECT `id` FROM `recipes`;";
                ResultSet recipeResults = Database.getInstance().ExecuteQuery(recipeQuery, new ArrayList<String>());
                List<Integer> recipeIds = new ArrayList();
                try {
                    while (recipeResults.next()) {
                        recipeIds.add(recipeResults.getInt(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.returnStatus(response, new IllegalStateStatus(null));
                }
                int index = (int) Math.round(Math.random() * (recipeIds.size() - 1));
                makeRecipeResponse(response, recipeIds.get(index));
            } else {
                List<Integer> mealDifference = getFavoriteMealDifference(user, mostSimilar);
                int index = (int) Math.round(Math.random() * (mealDifference.size() - 1));
                int mealIndex = mealDifference.get(index);
                makeRecipeResponse(response, mealIndex);
            }
        }
    }

    private List<Integer> getFavoriteMealDifference(User u, User v) {
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
        for (int x : v.getMeals()) {
            boolean notContained = true;
            for (int y : u.getMeals()) {
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
