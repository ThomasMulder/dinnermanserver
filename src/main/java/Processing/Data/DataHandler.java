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

    public Integer handleSingleInteger(ResultSet resultSet, int index) {
        return handleResultSetAsFirstInstance(resultSet, new SingleIntegerProcessor(index), Integer.class);
    }

    public String handleSingleString(ResultSet resultSet, int index) {
        return handleResultSetAsFirstInstance(resultSet, new SingleStringProcessor(index), String.class);
    }

    public List<Integer> handleListSingleInteger(ResultSet resultSet, int index) {
        return handleResultSetAsList(resultSet, new SingleIntegerProcessor(index), Integer.class);
    }

    public List<String> handleListSingleString(ResultSet resultSet, int index) {
        return handleResultSetAsList(resultSet, new SingleStringProcessor(index), String.class);
    }

    public Object[] handleAuthentication(ResultSet resultSet) {
        return handleResultSetAsFirstInstance(resultSet, new AuthenticationProcessor(), Object[].class);
    }

    public List<Recipe> handleListRecipe(ResultSet resultSet) {
        return handleResultSetAsList(resultSet, new RecipeProcessor(), Recipe.class);
    }

    public void handleSimilarityMapInsertion(ResultSet resultSet,
                                             RecipeIngredientSimilarityMap recipeIngredientSimilarityMap,
                                             List<Integer> allowedIds) {
        handleResultSetAsList(resultSet, new SimilarityMapInsertionProcessor(recipeIngredientSimilarityMap, allowedIds), Void.class);
    }

    public List<User> handleListUser(ResultSet resultSet) {
        return handleResultSetAsList(resultSet, new UserProcessor(), User.class);
    }

    private <T> T handleResultSetAsFirstInstance(ResultSet resultSet, DataProcessingInterface processor, Class<T> clazz) {
        T result = null;
        try {
            if (resultSet.first()) {
                return clazz.cast(processor.process(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not find first result in ResultSet where one was expected.");
        }
        return result;
    }

    private <T> List<T> handleResultSetAsList(ResultSet resultSet, DataProcessingInterface processor, Class<T> clazz) {
        List<T> result = new ArrayList();
        try {
            while (resultSet.next()) {
                result.add(clazz.cast(processor.process(resultSet)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not find next result in ResultSet where one was expected.");
        }
        return result;
    }

    private abstract class SingleProcessor<T> implements DataProcessingInterface {
        protected int index;

        public SingleProcessor(int index) {
            this.index = index;
        }
    }

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

    private class AuthenticationProcessor implements DataProcessingInterface<Object[]> {

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

    private class RecipeProcessor implements DataProcessingInterface<Recipe> {

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

    private class SimilarityMapInsertionProcessor implements DataProcessingInterface<Void> {
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
                if (Utils.getInstance().listContains(allowedRecipeIds, i)) {
                    recipeIngredientSimilarityMap.add(i);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalStateException("Tried to obtain recipe_id from ResultSet entry, but failed.");
            }
            return null;
        }
    }

    private class UserProcessor implements DataProcessingInterface<User> {

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
