package Model;

import java.util.List;

/**
 * Created by Thomas on 17-2-2016.
 * An enumeration describing a User.
 */
public class User {
    private static final double FAVORITE_WEIGHT = 5.0;
    private static final double MEAL_WEIGHT = 1.0;

    /* The user's name. */
    private String username;
    /* A list of integer identifiers of recipes that are favorites of the user. */
    private List<Integer> favorites;
    /* A list of textual identifiers of allergens that are to be excluded for the user. */
    private List<String> allergens;
    /* A list of integer identifiers of recipes that the user has stated he/she made. */
    private List<Integer> meals;

    /**
     * Constructor.
     * @param username the user's name.
     * @param favorites the list of favorite recipe identifiers.
     * @param allergens the list of allergen identifiers.
     * @param meals the list of meal identifiers.
     */
    public User(String username, List<Integer> favorites, List<String> allergens, List<Integer> meals) {
        this.username = username;
        this.favorites = favorites;
        this.allergens = allergens;
        this.meals = meals;
    }

    /* Accessor Methods. */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Integer> favorites) {
        this.favorites = favorites;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }

    public List<Integer> getMeals() {
        return meals;
    }

    public void setMeals(List<Integer> meals) {
        this.meals = meals;
    }

    /**
     * Computes the similarity of {@code User this} to parameterised {@code User that}, based on the following formula:
     * similarity(this, that) = FAVORITE_WEIGHT * sharedFavorites(this, that) + MEAL_WEIGHT * sharedMeals(this, that),
     * where sharedFavorites() and sharedMeals() denote the number of ids found in both the favorite- and meal id lists
     * of {@code this} and {@code that}, respectively.
     * @param that the {@code User} to calculate similarity with.
     * @return the similarity score between two users.
     */
    public int computeSimilarity(User that) {
        int sharedFavoriteCount = 0;
        int sharedMealCount = 0;
        for (int thisFavorite : this.favorites) { // Count the number of shared favorites.
            for (int thatFavorite : that.getFavorites()) {
                if (thisFavorite == thatFavorite) {
                    sharedFavoriteCount++;
                }
            }
        }
        for (int thisMeal : this.meals) { // Count the number of shared meals.
            for (int thatMeal : that.getMeals()) {
                if (thisMeal == thatMeal) {
                    sharedMealCount++;
                }
            }
        }
        /* Apply formula and return result. */
        return (int) (FAVORITE_WEIGHT * sharedFavoriteCount + MEAL_WEIGHT * sharedMealCount);
    }
}
