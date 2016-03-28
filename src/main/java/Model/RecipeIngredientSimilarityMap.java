package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A data-type that counts the number of ingredients found in a recipe, if any.
 */
public class RecipeIngredientSimilarityMap {
    /* Accessor Methods */
    public List<Integer> getRecipeIds() {
        return recipeIds;
    }

    public void setRecipeIds(List<Integer> recipeIds) {
        this.recipeIds = recipeIds;
    }

    public List<Integer> getSimilarities() {
        return similarities;
    }

    public void setSimilarities(List<Integer> similarities) {
        this.similarities = similarities;
    }

    /* A list of all recipe ids in this mapping. */
    private List<Integer> recipeIds;
    /* A list of similarity scores for the the recipe ids in this mapping. */
    private List<Integer> similarities;

    public RecipeIngredientSimilarityMap() {
        this.recipeIds = new ArrayList();
        this.similarities = new ArrayList();
    }

    /**
     * Adds a recipeId to the list of recipeIds, or increments its similarity value if the id is already present.
     * @param recipeId the recipeId to add.
     */
    public void add(int recipeId) {
        int index = -1; // Initialise index, -1 indicates the recipeId has not been found.
        for (int i = 0; i < this.recipeIds.size(); i++) { // Find out if the recipeId is already in the list.
            if (this.recipeIds.get(i) == recipeId) {
                index = i;
                break;
            }
        }
        if (index >= 0) { // The recipeId was already in the list, increment the similarity value.
            this.similarities.set(index, this.similarities.get(index) + 1);
        } else { // The recipeId was not yet in the list, add a new entry.
            this.recipeIds.add(recipeId);
            this.similarities.add(1);
        }
    }

    /**
     * Sort both {@code this.recipeIds} and {@code this.similarities} with respect to the the similarities, in
     * descending order. Based on <a href="https://en.wikipedia.org/wiki/Insertion_sort">Insertion-Sort.</a>
     */
    public void sortDescending() {
        int i;
        for (int j = 1; j < this.similarities.size(); j++) {
            int id = this.recipeIds.get(j);
            int sim = this.similarities.get(j);
            for (i = j - 1; (i >= 0) && (this.similarities.get(i) < sim); i--) {
                this.recipeIds.set(i + 1, this.recipeIds.get(i));
                this.similarities.set(i + 1, this.similarities.get(i));
            }
            this.recipeIds.set(i + 1, id);
            this.similarities.set(i + 1, sim);
        }
    }
}