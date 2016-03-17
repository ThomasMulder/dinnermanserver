package Spoonacular.Model;

import com.google.gson.JsonArray;

/**
 * Created by s124392 on 15-3-2016.
 */
public class Information {
    private boolean vegetarian, vegan, glutenFree, dairyFree, veryHealthy, cheap, veryPopular, sustainable, lowFodmap, ketogenic, whole30;
    private int weightWatcherSmartPoints, servings, preparationMinutes, cookingMinutes, aggregateLikes, id, readyInMinutes;
    private String gaps, sourceUrl, spoonacularSourceUrl, title, image, text, instructions;
    private ExtendedIngredient[] extendedIngredients;

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public void setVeryHealthy(boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    public void setCheap(boolean cheap) {
        this.cheap = cheap;
    }

    public void setVeryPopular(boolean veryPopular) {
        this.veryPopular = veryPopular;
    }

    public void setSustainable(boolean sustainable) {
        this.sustainable = sustainable;
    }

    public void setLowFodmap(boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    public void setKetogenic(boolean ketogenic) {
        this.ketogenic = ketogenic;
    }

    public void setWhole30(boolean whole30) {
        this.whole30 = whole30;
    }

    public void setWeightWatcherSmartPoints(int weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    public void setCookingMinutes(int cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
    }

    public void setAggregateLikes(int aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setExtendedIngredients(ExtendedIngredient[] extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public boolean isSustainable() {
        return sustainable;

    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public boolean isWhole30() {
        return whole30;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public int getServings() {
        return servings;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public int getCookingMinutes() {
        return cookingMinutes;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public int getId() {
        return id;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getGaps() {
        return gaps;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public ExtendedIngredient[] getExtendedIngredients() {
        return extendedIngredients;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Information(boolean vegetarian, boolean vegan, boolean glutenFree, boolean dairyFree, boolean veryHealthy,
                       boolean cheap, boolean veryPopular, boolean sustainable, boolean lowFodmap, boolean ketogenic,
                       boolean whole30, int weightWatcherSmartPoints, int servings, int preparationMinutes,
                       int cookingMinutes, int aggregateLikes, int id, int readyInMinutes, String gaps,
                       String sourceUrl, String spoonacularSourceUrl, String title, String image, String text,
                       String instructions, ExtendedIngredient[] extendedIngredients) {
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
        this.dairyFree = dairyFree;
        this.veryHealthy = veryHealthy;
        this.cheap = cheap;
        this.veryPopular = veryPopular;
        this.sustainable = sustainable;
        this.lowFodmap = lowFodmap;
        this.ketogenic = ketogenic;
        this.whole30 = whole30;
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
        this.servings = servings;
        this.preparationMinutes = preparationMinutes;
        this.cookingMinutes = cookingMinutes;
        this.aggregateLikes = aggregateLikes;
        this.id = id;
        this.readyInMinutes = readyInMinutes;
        this.gaps = gaps;
        this.sourceUrl = sourceUrl;
        this.spoonacularSourceUrl = spoonacularSourceUrl;
        this.title = title;
        this.image = image;
        this.text = text;
        this.instructions = instructions;
        this.extendedIngredients = extendedIngredients;
    }

    @Override
    public String toString() {
        String result = "vegetarian = " + this.vegetarian + "\n";
        result = result.concat("vegan = " + this.vegan + "\n");
        result = result.concat("glutenFree = " + this.glutenFree + "\n");
        result = result.concat("dairyFree = " + this.dairyFree + "\n");
        result = result.concat("veryHealthy = " + this.veryHealthy + "\n");
        result = result.concat("cheap = " + this.cheap + "\n");
        result = result.concat("veryPopular = " + this.veryPopular + "\n");
        result = result.concat("sustainable = " + this.sustainable + "\n");
        result = result.concat("lowFodmap = " + this.lowFodmap + "\n");
        result = result.concat("ketogenic = " + this.ketogenic + "\n");
        result = result.concat("whole30 = " + this.whole30 + "\n");
        result = result.concat("weightWatcherSmartPoints = " + this.weightWatcherSmartPoints + "\n");
        result = result.concat("servings = " + this.servings + "\n");
        result = result.concat("preparationMinutes = " + this.preparationMinutes + "\n");
        result = result.concat("cookingMinutes = " + this.cookingMinutes + "\n");
        result = result.concat("aggregateLikes = " + this.aggregateLikes + "\n");
        result = result.concat("id = " + this.id + "\n");
        result = result.concat("readyInMinutes = " + this.readyInMinutes + "\n");
        result = result.concat("gaps = " + this.gaps + "\n");
        result = result.concat("sourceUrl = " + this.sourceUrl + "\n");
        result = result.concat("spoonacularSourceUrl = " + this.spoonacularSourceUrl + "\n");
        result = result.concat("title = " + this.title + "\n");
        result = result.concat("image = " + this.image + "\n");
        result = result.concat("text = " + this.text + "\n");
        result = result.concat("instructions = " + this.instructions + "\n");
        return result;
    }

    public JsonArray getExtenededIngredientsAsJson() {
        JsonArray array = new JsonArray();
        for (ExtendedIngredient e : this.extendedIngredients) {
            array.add(e.toJson());
        }
        return array;
    }
}
