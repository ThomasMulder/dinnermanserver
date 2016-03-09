package Model;

import java.util.List;

/**
 * Created by Thomas on 17-2-2016.
 * An enumeration describing a User.
 */
public class User {
    /* The user's name. */
    private String username;
    /* A list of integer identifiers of recipes that are favorites of the user. */
    private List<Integer> favorites;
    /* A list of integer identifiers of allergens that are to be excluded for the user. */
    private List<Integer> allergens;
    /* A list of integer identifiers of recipes that the user has stated he/she made. */
    private List<Integer> meals;

    /**
     * Constructor.
     * @param username the user's name.
     * @param favorites the list of favorite recipe identifiers.
     * @param allergens the list of allergen identifiers.
     * @param meals the list of meal identifiers.
     */
    public User(String username, List<Integer> favorites, List<Integer> allergens, List<Integer> meals) {
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

    public List<Integer> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Integer> allergens) {
        this.allergens = allergens;
    }

    public List<Integer> getMeals() {
        return meals;
    }

    public void setMeals(List<Integer> meals) {
        this.meals = meals;
    }
}
