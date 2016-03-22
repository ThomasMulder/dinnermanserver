package Model;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientSimilarityMap {
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

    private List<Integer> recipeIds;
    private List<Integer> similarities;

    public RecipeIngredientSimilarityMap() {
        this.recipeIds = new ArrayList();
        this.similarities = new ArrayList();
    }

    public void add(int recipeId) {
        int index = -1;
        for (int i = 0; i < this.recipeIds.size(); i++) {
            if (this.recipeIds.get(i) == recipeId) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.similarities.set(index, this.similarities.get(index) + 1);
        } else {
            this.recipeIds.add(recipeId);
            this.similarities.add(1);
        }
    }

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