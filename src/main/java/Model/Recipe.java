package Model;

import com.google.gson.JsonObject;

/**
 * Created by s124392 on 20-3-2016.
 * A data-type representing a recipe.
 */
public class Recipe {
    /**
     * Instance variables.
     * {@code int id} the recipe's unique identifier.
     * {@code int servings} the number of people this recipe serves.
     * {@code int preparationMintes} the number of minutes spent on preparing this recipe.
     * {@code int cookingMinutes} the number of minutes spent on cooking this recipe.
     * {@code int readyInMinutes} the total number of minutes spent on preparing and cooking this recipe.
     * {@code int calories} the number of calories in this recipe.
     * {@code int fat} the amount of fat in this recipe, in grams.
     * {@code int protein} the amount of protein in this recipe, in grams.
     * {@code int carbs} the amount of carbohydrates in this recipe, in grams.
     * {@code String title} the title of this recipe.
     * {@code String image} a reference to an image of this recipe.
     * {@code String cuisine} the cuisine to which to recipe belongs (e.g. french, italian, thai etc...).
     * {@code String summary} a summary text describing this recipe.
     * {@code String instructions} a text providing step-by-step instructions to prepare this recipe.
     * {@code String ingredients} a new-line separated text detailing the ingredients used in this recipe.
     */
    private int id, servings, preparationMinutes, cookingMinutes, readyInMinutes, calories, fat, protein, carbs;
    private String title, image, cuisine, summary, instructions, ingredients;

    /**
     * Generated Constructor.
     */
    public Recipe(int id, int servings, int preparationMinutes, int cookingMinutes, int readyInMinutes, String title, String image, String cuisine, int calories, int fat, int protein, int carbs, String summary, String instructions, String ingredients) {
        this.id = id;
        this.servings = servings;
        this.preparationMinutes = preparationMinutes;
        this.cookingMinutes = cookingMinutes;
        this.readyInMinutes = readyInMinutes;
        this.title = title;
        this.image = image;
        this.cuisine = cuisine;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.summary = summary;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    /* Accessor Methods */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    public int getCookingMinutes() {
        return cookingMinutes;
    }

    public void setCookingMinutes(int cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (servings != recipe.servings) return false;
        if (preparationMinutes != recipe.preparationMinutes) return false;
        if (cookingMinutes != recipe.cookingMinutes) return false;
        if (readyInMinutes != recipe.readyInMinutes) return false;
        if (!title.equals(recipe.title)) return false;
        if (!image.equals(recipe.image)) return false;
        if (!cuisine.equals(recipe.cuisine)) return false;
        if (calories != recipe.calories) return false;
        if (fat != recipe.fat) return false;
        if (protein != recipe.protein) return false;
        if (carbs != recipe.carbs) return false;
        if (!summary.equals(recipe.summary)) return false;
        if (!instructions.equals(recipe.instructions)) return false;
        return ingredients.equals(recipe.ingredients);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", servings=" + servings +
                ", preparationMinutes=" + preparationMinutes +
                ", cookingMinutes=" + cookingMinutes +
                ", readyInMinutes=" + readyInMinutes +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", calories='" + calories + '\'' +
                ", fat='" + fat + '\'' +
                ", protein='" + protein + '\'' +
                ", carbs='" + carbs + '\'' +
                ", summary='" + summary + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }

    /**
     * Returns a JSON-object with properties for all instance variables.
     * @return {@code JsonObject}.
     */
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("id", this.id);
        result.addProperty("title", this.title);
        result.addProperty("image", this.image);
        result.addProperty("cuisine", this.cuisine);
        result.addProperty("calories", this.calories);
        result.addProperty("fat", this.fat);
        result.addProperty("protein", this.protein);
        result.addProperty("carbs", this.carbs);
        result.addProperty("servings", this.servings);
        result.addProperty("preparationMinutes", this.preparationMinutes);
        result.addProperty("cookingMinutes", this.cookingMinutes);
        result.addProperty("readyInMinutes", this.readyInMinutes);
        result.addProperty("summary", this.summary);
        result.addProperty("instructions", this.instructions);
        result.addProperty("ingredients", this.ingredients);
        return result;
    }
}
