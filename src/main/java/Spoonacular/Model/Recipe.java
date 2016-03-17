package Spoonacular.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by s124392 on 15-3-2016.
 */
public class Recipe {
    private String summary;
    private Information information;
    private Map<String, String> attributeMap;

    public Recipe(String summary, Information information) {
        this.summary = summary;
        this.information = information;
        this.attributeMap = getAttributeMap();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public String getAttribute(String name) {
        if (name.equals("extendedIngredients")) {
            return this.information.getExtendedIngredients().toString();
        } else {
            return attributeMap.get(name);
        }
    }

    private Map<String, String> getAttributeMap() {
        Map<String, String> attributeMap = new HashMap();
        attributeMap.put("summary", this.summary);
        attributeMap.put("vegetarian", String.valueOf(this.information.isVegetarian()));
        attributeMap.put("vegan", String.valueOf(this.information.isVegan()));
        attributeMap.put("glutenFree", String.valueOf(this.information.isGlutenFree()));
        attributeMap.put("dairyFree", String.valueOf(this.information.isDairyFree()));
        attributeMap.put("veryHealthy", String.valueOf(this.information.isVeryHealthy()));
        attributeMap.put("cheap", String.valueOf(this.information.isCheap()));
        attributeMap.put("veryPopular", String.valueOf(this.information.isVeryPopular()));
        attributeMap.put("sustainable", String.valueOf(this.information.isSustainable()));
        attributeMap.put("lowFodmap", String.valueOf(this.information.isLowFodmap()));
        attributeMap.put("ketogenic", String.valueOf(this.information.isKetogenic()));
        attributeMap.put("whole30", String.valueOf(this.information.isWhole30()));
        attributeMap.put("weightWatcherSmartPoints", String.valueOf(this.information.getWeightWatcherSmartPoints()));
        attributeMap.put("servings", String.valueOf(this.information.getServings()));
        attributeMap.put("preparationMinutes", String.valueOf(this.information.getPreparationMinutes()));
        attributeMap.put("cookingMinutes", String.valueOf(this.information.getCookingMinutes()));
        attributeMap.put("aggregateLikes", String.valueOf(this.information.getAggregateLikes()));
        attributeMap.put("id", String.valueOf(this.information.getId()));
        attributeMap.put("readyInMinutes", String.valueOf(this.information.getReadyInMinutes()));
        attributeMap.put("gaps", this.information.getGaps());
        attributeMap.put("sourceUrl", this.information.getSourceUrl());
        attributeMap.put("spoonacularSourceUrl", this.information.getSpoonacularSourceUrl());
        attributeMap.put("title", this.information.getTitle());
        attributeMap.put("image", this.information.getImage());
        attributeMap.put("text", this.information.getText());
        attributeMap.put("instructions", this.information.getInstructions());
        return attributeMap;
    }

    public boolean isValidRecipe(String[] requiredAttributes) {
        String[] requiredValues = new String[requiredAttributes.length];
        int i = 0;
        for (String requiredAttribute : requiredAttributes) {
            requiredValues[i] = this.attributeMap.get(requiredAttribute);
            i++;
        }
        for (String requiredValue : requiredValues) {
            if (requiredValue.equals("-1") || requiredValue.equals("-1.0") || requiredValue.equals("null") ||
                    requiredValue.equals("")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Summary = " + this.summary + "\n" + "information = " + this.information.toString();
    }
}
