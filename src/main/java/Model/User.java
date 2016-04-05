package Model;

import java.util.List;

/**
 * Created by Thomas on 17-2-2016.
 * An enumeration describing a User.
 */
public class User {
    private static final double FAVORITE_WEIGHT = 5.0;

    /* The user's name. */
    private String username;
    /* A list of integer identifiers of recipes that are favorites of the user. */
    private List<Integer> favorites;
    /* A list of textual identifiers of allergens that are to be excluded for the user. */
    private List<String> allergens;

    /**
     * Constructor.
     * @param username the user's name.
     * @param favorites the list of favorite recipe identifiers.
     * @param allergens the list of allergen identifiers.
     */
    public User(String username, List<Integer> favorites, List<String> allergens) {
        this.username = username;
        this.favorites = favorites;
        this.allergens = allergens;
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

    /**
     * Computes the similarity of {@code User this} to parameterised {@code User that}, based on the following formula:
     * similarity(this, that) = FAVORITE_WEIGHT * sharedFavorites(this, that),
     * where sharedFavorites() denotes the number of ids found in the favorite lists
     * of {@code this} and {@code that}, respectively.
     * @param that the {@code User} to calculate similarity with.
     * @return the similarity score between two users.
     */
    public int computeSimilarity(User that) {
        int sharedFavoriteCount = 0;
        for (int thisFavorite : this.favorites) { // Count the number of shared favorites.
            for (int thatFavorite : that.getFavorites()) {
                if (thisFavorite == thatFavorite) {
                    sharedFavoriteCount++;
                }
            }
        }
        /* Apply formula and return result. */
        return (int) (FAVORITE_WEIGHT * sharedFavoriteCount);
    }
}
