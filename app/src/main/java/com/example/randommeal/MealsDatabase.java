package com.example.randommeal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class MealsDatabase extends RoomDatabase {

    private static MealsDatabase database;
    private static final String DB_NAME = "meals.db";

    // об’єкт для синхронізації
    private static final Object LOCK = new Object();

    public static MealsDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, MealsDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    // метод який повертає об’єкт нашого інтерфейсу
    public abstract MealDao mealsDao();

}
