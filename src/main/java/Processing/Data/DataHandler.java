package Processing.Data;

import Configuration.Database;
import Model.Recipe;
import Model.RecipeIngredientSimilarityMap;
import Model.User;
import Processing.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 1-4-2016.
 * A singleton that handles the various {@code ResultSet}s obtain from making database queries.
 *
 * The public methods in this class serve to return various data-types obtain from their parameterised
 * resultsets, which in turn are obtain from various MySQL queries. Each public method either handles
 * a single entry of the resultset, or handles all of them as a list.
 *
 * Additionally, various instances of {@code DataProcessorInterface} are used to handle the resultset entries.
 * These instances are implemented as private, internal classes.
 */
public class DataHandler {
    /* The singleton instance. */
    private static DataHandler instance = null;

    /**
     * Constructor.
     */
    private DataHandler() {}

    /**
     * Returns the instance of this singleton.
     * @return {@code DataHandler}.
     */
    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }

    /**
     * Handles a {@code ResultSet} from which a single entry has to be retrieved as an {@code int}.
     * @param resultSet the result set to obtain data from.
     * @param index the index of the attribute to be obtained.
     * @return {@code Integer}.
     */
    public Integer handleSingleInteger(ResultSet resultSet, int index) {
        return handleResultSetAsFirstInstance(resultSet, new SingleIntegerProcessor(index), Integer.class);
    }

    /**
     * Handles a {@code ResultSet} from which a single entry has to be retrieved as a {@code String}.
     * @param resultSet the result set to obtain data from.
     * @param index the index of the attribute to be obtained.
     * @return {@code String}.
     */
    public String handleSingleString(ResultSet resultSet, int index) {
        return handleResultSetAsFirstInstance(resultSet, new SingleStringProcessor(index), String.class);
    }

    /**
     * Handles a {@code ResultSet} from which a list of entries has to be retrieved as {@code Integer}s.
     * @param resultSet the result set to obtain data from.
     * @param index the index of the attribute to be obtained.
     * @return {@code List<Integer>}.
     */
    public List<Integer> handleListSingleInteger(ResultSet resultSet, int index) {
        return handleResultSetAsList(resultSet, new SingleIntegerProcessor(index), Integer.class);
    }

    /**
     * Handles a {@code ResultSet} from which a list of entries has to be retrieved as {@code String}s.
     * @param resultSet the result set to obtain data from.
     * @param index the index of the attribute to be obtained.
     * @return {@code List<String>}.
     */
    public List<String> handleListSingleString(ResultSet resultSet, int index) {
        return handleResultSetAsList(resultSet, new SingleStringProcessor(index), String.class);
    }

    /**
     * Handles a {@code ResultSet} from which an {@code int} and {@code String} have to be retrieved as indices 0 and 1
     * of an {@code Object} array, respectively.
     * @param resultSet the result set to obtain data from.
     * @return {@code Object[]}.
     */
    public Object[] handleAuthentication(ResultSet resultSet) {
        return handleResultSetAsFirstInstance(resultSet, new AuthenticationProcessor(), Object[].class);
    }

    /**
     * Handles a {@code ResultSet} from which a list of entries has to be retrieved as {@code Recipe}s.
     * @param resultSet the result set to obtain data from.
     * @return {@code List<Recipe>}.
     */
    public List<Recipe> handleListRecipe(ResultSet resultSet) {
        return handleResultSetAsList(resultSet, new RecipeProcessor(), Recipe.class);
    }

    /**
     * Handles a {@code ResultSet} based on which entries must be added to- or incremented in
     * {@code RecipeIngredientSimilarityMap recipeIngredientSimilarityMap}.
     * @param resultSet the result set to obtain data from.
     */
    public void handleSimilarityMapInsertion(ResultSet resultSet,
                                             RecipeIngredientSimilarityMap recipeIngredientSimilarityMap,
                                             List<Integer> allowedIds) {
        handleResultSetAsList(resultSet, new SimilarityMapInsertionProcessor(recipeIngredientSimilarityMap, allowedIds), Void.class);
    }

    /**
     * Handles a {@code ResultSet} from which a list of entries has to be retrieved as {@code User}s.
     * @param resultSet the result set to obtain data from.
     * @return {@code List<User>}.
     */
    public List<User> handleListUser(ResultSet resultSet) {
        return handleResultSetAsList(resultSet, new UserProcessor(), User.class);
    }

    /**
     * Attempts to process only the first result in the {@code resultSet}.
     * @param resultSet the {@code ResultSet} to process.
     * @param processor the {@code DataProcessorInterface} to do the processing.
     * @param clazz the class to cast to, to obtain the right return type.
     * @param <T> return type placeholder.
     * @return
     */
    private <T> T handleResultSetAsFirstInstance(ResultSet resultSet, DataProcessorInterface processor, Class<T> clazz) {
        T result = null;
        try {
            if (resultSet.first()) { // resultSet has a first entry.
                return clazz.cast(processor.process(resultSet)); // Process the entry and cast to appropriate class.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not find first result in ResultSet where one was expected.");
        }
        return result;
    }

    /**
     * Attempts to process all entries in the {@code resultSet}.
     * @param resultSet the {@code ResultSet} to process.
     * @param processor the {@code DataProcessorInterface} to do the processing.
     * @param clazz the class to cast to, to obtain the right return type.
     * @param <T> return type placeholder.
     * @return
     */
    private <T> List<T> handleResultSetAsList(ResultSet resultSet, DataProcessorInterface processor, Class<T> clazz) {
        List<T> result = new ArrayList();
        try {
            while (resultSet.next()) { // resultSet has another entry.
                result.add(clazz.cast(processor.process(resultSet))); // Process the entry and cast to appropriate class.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not find next result in ResultSet where one was expected.");
        }
        return result;
    }

    /* Start of the internal classes implementing DataProcessorInterface. */

    /**
     * An abstract for processing a single attribute located at a certain index the resultset.
     * @param <T>
     */
    private abstract class SingleProcessor<T> implements DataProcessorInterface {
        protected int index;

        public SingleProcessor(int index) {
            this.index = index;
        }
    }

    /**
     * A concrete DataProcessor that returns the value of a single attribute at a given index as {@code Integer}.
     */
    private class SingleIntegerProcessor extends SingleProcessor<Integer> {

        public SingleIntegerProcessor(int index) {
            super(index);
        }

        @Override
        public Integer process(ResultSet entry) {
            try {
                return entry.getInt(index);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain integer from ResultSet entry, but failed.");
            }
        }
    }

    /**
     * A concrete DataProcessor that returns the value of a single attribute at a given index as {@code String}.
     */
    private class SingleStringProcessor extends SingleProcessor<String> {

        public SingleStringProcessor(int index) {
            super(index);
        }

        @Override
        public String process(ResultSet entry) {
            try {
                return entry.getString(index);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain string from ResultSet entry, but failed.");
            }
        }
    }

    /**
     * A concrete DataProcessor that returns two attributes in an {@code Object} array.
     */
    private class AuthenticationProcessor implements DataProcessorInterface<Object[]> {

        @Override
        public Object[] process(ResultSet entry) {
            Object[] result = new Object[2];
            try {
                result[0] = entry.getInt("CNT");
                result[1] = entry.getString("authToken");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain authentication properties from ResultSet entry, but failed.");
            }
            return result;
        }
    }

    /**
     * A concrete DataProcessor that attempts to obtain all attributes required to instantiate a {@code Recipe} object.
     */
    private class RecipeProcessor implements DataProcessorInterface<Recipe> {

        @Override
        public Recipe process(ResultSet entry) {
            try {
                return new Recipe(entry.getInt("id"), entry.getInt("servings"), entry.getInt("preparationMinutes"),
                        entry.getInt("cookingMinutes"), entry.getInt("readyInMinutes"), entry.getString("title"), entry.getString("image"),
                        entry.getString("cuisine"), entry.getInt("calories"), entry.getInt("fat"), entry.getInt("protein"),
                        entry.getInt("carbs"), entry.getString("summary"), entry.getString("instructions"), entry.getString("ingredients"));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain recipe properties from ResultSet entry, but failed.");
            }
        }
    }

    /**
     * A concrete DataProcessor that either adds a new entry, or increments the similarity value of an existing entry
     * for a given {@code RecipeIngredientSimilarityMap recipeIngredientSimilarityMap} and {@code List<Integer> allowedRecipeIds}.
     */
    private class SimilarityMapInsertionProcessor implements DataProcessorInterface<Void> {
        private RecipeIngredientSimilarityMap recipeIngredientSimilarityMap;
        private List<Integer> allowedRecipeIds;

        public SimilarityMapInsertionProcessor(RecipeIngredientSimilarityMap recipeIngredientSimilarityMap,
                                               List<Integer> allowedRecipeIds) {
            this.recipeIngredientSimilarityMap = recipeIngredientSimilarityMap;
            this.allowedRecipeIds = allowedRecipeIds;
        }

        @Override
        public Void process(ResultSet entry) {
            int i;
            try {
                i = entry.getInt("recipe_id");
                if (Utils.getInstance().listContains(allowedRecipeIds, i)) { // Recipe has given ingredient, and is allowed.
                    recipeIngredientSimilarityMap.add(i); // Add to- or increment value.
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain recipe_id from ResultSet entry, but failed.");
            }
            return null;
        }
    }

    /**
     * A concrete DataProcessor that attempts to obtain all attributes required to instantiate a {@code User} object.
     */
    private class UserProcessor implements DataProcessorInterface<User> {

        @Override
        public User process(ResultSet entry) {
            try {
                int id = entry.getInt("id");
                return Database.getInstance().getUserById(id);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain account id from ResultSet entry, but failed.");
            }
        }
    }
}
