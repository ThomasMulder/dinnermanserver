package Configuration;

import Processing.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Database {
    /* Start Singleton */
    private static Database instance = null;
    private static Utils utils = Utils.getInstance();

    private static final int FAVORITE_WEIGHT = 5;
    private static final int MEAL_WEIGHT = 1;

    public static Database getInstance()
    {
        if(instance == null) {
            instance = new Database();
        }

        return instance;
    }
    /* End Singleton */

    private Connection connection;

    protected Database()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.makeConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void makeConnection() {
        try {
            this.connection = DriverManager.getConnection(Properties.getInstance().getProperty("databaseDriver"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute SQL query that results in a ResultSet of data
     *
     * @param query The SQL query
     * @param parameters Parameters that need to be injected in the query
     *
     * @return Result of the query when executed
     */
    public ResultSet ExecuteQuery(String query, ArrayList<String> parameters)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for(int i = 0;i<parameters.size();i++) {
                statement.setString(i+1, parameters.get(i));
            }
            return statement.executeQuery();
        } catch (Exception e) {
            this.makeConnection();
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Execute the SQL query that updates rows in the database and does not result in a ResultSet of data
     * @param query The SQL query
     * @param parameters Parameters that need to be injected in the query
     */
    public void ExecuteUpdate(String query, ArrayList<String> parameters)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for(int i = 0;i<parameters.size();i++) {
                statement.setString(i+1, parameters.get(i));
            }
            statement.executeUpdate();
        } catch (Exception e) {
            this.makeConnection();
            e.printStackTrace();
        }
    }

    /**
     * Init the database for the server. Executes queries in the databases.sql,
     * mainly for creating tables when they do not exist yet
     */
    public void init() {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/databases.sql")));
            String line;
            while ((line = br.readLine()) != null) {
                Database.getInstance().ExecuteUpdate(line, new ArrayList<String>());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all cuisines present in the database.
     * @return {@code List<String>}.
     */
    public List<String> getCuisines() {
        List<String> result = new ArrayList();
        Set<String> aux = new HashSet(); // Auxiliary set to take care of duplicates.
        ResultSet cuisineList = ExecuteQuery("SELECT `cuisine` FROM `recipes`;", new ArrayList<String>());
        try {
            while (cuisineList.next()) {
                String cuisine = cuisineList.getString(1);
                aux.add(cuisine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String cuisine : aux) {
            result.add(cuisine);
        }
        return result;
    }

    /**
     * Returns a list of all searchable ingredients in the database.
     * @return {@code List<String>}
     */
    public List<String> getSearchIngredients() {
        List<String> result = new ArrayList();
        Set<String> aux = new HashSet(); // Auxiliary set to take care of duplicates.
        ResultSet ingredientList = ExecuteQuery("SELECT `ingredient` FROM `search_ingredients`;", new ArrayList<String>());
        try {
            while (ingredientList.next()) {
                String ingredient = ingredientList.getString(1).trim();
                aux.add(capitalize(ingredient));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (String cuisine : aux) {
            result.add(cuisine);
        }
        return result;
    }

    /**
     * Returns a list of recipe ids of recipes that are allowed with respect to the user's allergen profile.
     * @param accountId
     * @return
     */
    public List<Integer> getAllowedRecipeIds(int accountId) {
        List<Integer> result = new ArrayList();
        String recipeQuery = "SELECT `id` FROM `recipes`;";
        List<Integer> recipeIds = new ArrayList();
        ResultSet recipeResults = Database.getInstance().ExecuteQuery(recipeQuery, new ArrayList<String>());
        try {
            while (recipeResults.next()) { // Obtain the ids of all recipes.
                recipeIds.add(recipeResults.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            List<String> allergens = getAllergens(accountId);
            for (int recipeId : recipeIds) { // Check every recipe...
                String ingredientQuery = "SELECT `ingredient` FROM `search_ingredients` WHERE `recipe_id` = '" + recipeId + "';";
                ResultSet ingredientResults = ExecuteQuery(ingredientQuery, new ArrayList<String>());
                List<String> ingredients = new ArrayList();
                try {
                    while (ingredientResults.next()) { // Obtain the list of ingredients for this recipe.
                        String ingredient = ingredientResults.getString(1);
                        ingredient = ingredient.trim();
                        ingredients.add(ingredient);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                boolean recipeIsAllowed = true;
                for (String ingredient : ingredients) { // Check whether or not the recipe contains an allergen.
                    if (utils.listContains(allergens, ingredient, false)) {
                        recipeIsAllowed = false;
                        break;
                    }
                }
                /* Additional 'soft' check in the plaintext description of ingredients. */
                if (recipeIsAllowed) {
                    String ingredientsQuery = "SELECT `ingredients` FROM `recipes` WHERE `id` = " + recipeId + ";";
                    ResultSet resultSet = ExecuteQuery(ingredientsQuery, new ArrayList<String>());
                    try {
                        while (resultSet.next()) {
                            String ingredientsText = resultSet.getString(1).toLowerCase();
                            for (String allergen : allergens) {
                                if (ingredientsText.contains(allergen)) {
                                    //System.out.println("Found: " + allergen + " in text.");
                                    recipeIsAllowed = false;
                                    break;
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (recipeIsAllowed) { // The recipe is allowed with respect to the user's allergens.
                    result.add(recipeId);
                }
            }
        return result;
    }

    /**
     * Returns the list of ingredients that are allowed for parametersied {@code int accountId}.
     * @param accountId the identifier of the account to get the allowed ingredients for.
     * @return a list of ingredient names.
     */
    public List<String> getAllowedIngredients(int accountId) {
        // Return all ingredients minus those set as allergens.
        return utils.getListStringDifference(getSearchIngredients(), getAllergens(accountId), false);
    }



    /**
     * Returns the list of ingredients that are set as allergens for the account with identifier {@code accountId}.
     * @param accountId the account identifier of the account to get the allergens for.
     * @return the list of all allergens for said account.
     */
    public List<String> getAllergens(int accountId) {
        List<String> result = new ArrayList();
        /* Query database for allergens belonging to this account identifier. */
        String allergenQuery = "SELECT `allergen` FROM `allergens` WHERE `account_id` = '" + accountId + "';";
        ResultSet allergenResults = ExecuteQuery(allergenQuery, new ArrayList<String>());
        try {
            while (allergenResults.next()) { // Obtain the user's allergen list.
                String allergen = allergenResults.getString(1);
                result.add(allergen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns a list of recipe identifiers, sorted in descending order by their popularity.
     * Popularity is defined as the follows:
     * popularity(recipe) = FAVORITE_WEIGHT * favoriteCount(recipe) + MEAL_WEIGHT * mealCount(recipe),
     * where favoriteCount() and mealCount() denote the number of times a recipe occurs as a favorite- and meal in the
     * user profiles, respectively.
     * @return {@code List<Integer>}.
     */
    public List<Integer> getRecipeIdsByPopularity() {
        /* Make a hideously verbose query to the database, taking care of counting and sorting. */
        String popularQuery = "SELECT `recipe_id` FROM (SELECT `recipe_id` FROM (SELECT `recipe_id`, SUM(`CNT`) AS `CNT`" +
                " FROM (SELECT `recipe_id`, 5 * COUNT(`recipe_id`) AS `CNT` FROM `favorites` GROUP BY `recipe_id`" +
                " UNION ALL SELECT `meal_id` AS `recipe_id`, 1 * COUNT(`meal_id`) AS `CNT` FROM `meals` GROUP BY" +
                " `meal_id`) AS X GROUP BY `recipe_id` ORDER BY `CNT` DESC) AS Y) AS X UNION ALL (SELECT `id` AS" +
                " `recipe_id` FROM `recipes` WHERE `id` NOT IN (SELECT `recipe_id` AS `id` FROM (SELECT `recipe_id`," +
                " SUM(`CNT`) AS `CNT` FROM (SELECT `recipe_id`, " + FAVORITE_WEIGHT + " * COUNT(`recipe_id`) AS `CNT`" +
                " FROM `favorites` GROUP BY `recipe_id` UNION ALL SELECT `meal_id` AS `recipe_id`, " + MEAL_WEIGHT +
                " * COUNT(`meal_id`) AS `CNT` FROM `meals` GROUP BY `meal_id`) AS X GROUP BY `recipe_id` ORDER BY" +
                " `CNT` DESC) AS Y));";
        List<Integer> result = new ArrayList();
        ResultSet popularResults = ExecuteQuery(popularQuery, new ArrayList<String>());
        try {
            while (popularResults.next()) { // Add the results of the query.
                result.add(popularResults.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks whether or not parameterised {@code String ingredient} is a valid ingredient identifier.
     * An identifier is said to be valid if and only if it occurs in the `ingredient` column of the `search_ingredients`
     * table.
     * @param ingredient the ingredient to check validity for.
     * @return
     */
    public boolean isValidIngredient(String ingredient) {
        List<String> allowedIngredients = getSearchIngredients();
        if (utils.listContains(allowedIngredients, ingredient, false)) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether or not parameterised {@code int id} is a valid recipe identifier.
     * An identifier is said to be valid if and only if it occurs in the `id` column of the `recipes` table.
     * @param id the identifier to check validity for.
     * @return
     */
    public boolean isValidRecipeId(int id) {
        List<Integer> allowedIds = new ArrayList();
        String idQuery = "SELECT `id` FROM `recipes`;";
        ResultSet resultSet = ExecuteQuery(idQuery, new ArrayList<String>());
        try { // Get all recipe ids.
            while (resultSet.next()) {
                allowedIds.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (utils.listContains(allowedIds, id)) {
            return true;
        }
        return false;
    }

    /**
     * Turn the first letter of parameterised {@code String} into an uppercase character.
     * @param str the string to capatilize.
     * @return {@code String}
     */
    private String capitalize(String str) {
        return (str.substring(0, 1).toUpperCase() + str.substring(1));
    }
}
