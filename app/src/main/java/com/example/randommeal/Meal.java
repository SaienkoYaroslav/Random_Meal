package com.example.randommeal;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals")
public class Meal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mealName;
    private String recipe;

    public Meal(int id, String mealName, String recipe) {
        this.id = id;
        this.mealName = mealName;
        this.recipe = recipe;
    }

    // конструктор без id
    @Ignore
    public Meal(String mealName, String recipe) {
        this.mealName = mealName;
        this.recipe = recipe;
    }

    public int getId() {
        return id;
    }

    public String getMealName() {
        return mealName;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
