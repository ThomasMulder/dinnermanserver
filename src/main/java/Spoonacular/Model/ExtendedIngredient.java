package Spoonacular.Model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by s124392 on 15-3-2016.
 */
public class ExtendedIngredient {
    private String aisle;
    private String name;
    private String unit;
    private String unitShort;
    private String unitLong;
    private String originalString;
    private double amount;

    public ExtendedIngredient(String aisle, String name, double amount, String unit, String unitShort,
                              String unitLong, String originalString) {
        this.setAisle(aisle);
        this.setName(name);
        this.setAmount(amount);
        this.setUnit(unit);
        this.setUnitShort(unitShort);
        this.setUnitLong(unitLong);
        this.setOriginalString(originalString);
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this).toString();
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("aisle", this.aisle);
        object.addProperty("name", this.name);
        object.addProperty("amount", String.valueOf(this.amount));
        object.addProperty("unit", this.unit);
        object.addProperty("unitShort", this.unitShort);
        object.addProperty("unitLong", this.unitLong);
        object.addProperty("originalString", this.originalString);
        return object;
    }
}
