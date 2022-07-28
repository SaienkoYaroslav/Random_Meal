package com.example.randommeal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {

    // отримуємо всі дані з БД
    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllMeals();

    // Отримуємо всі дані у вигляді List
    @Query("SELECT * FROM meals")
    List<Meal> getListMeal();

    // редагувати дані
    @Update
    void updateMeal(Meal meal);

    // записуємо дангі в БД
    @Insert
    void insertMeal(Meal meal);

    // видаляємо дані з БД
    @Delete
    void deleteMeal(Meal meal);

    // видаляємо всі дані з БД
    @Query("DELETE FROM meals")
    void deleteAll();

}
