package com.example.randommeal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static MealsDatabase database;
    private LiveData<List<Meal>> meals;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MealsDatabase.getInstance(getApplication());
        meals = database.mealsDao().getAllMeals();
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }


    public void insertMeal(Meal meal) {
        new InsertTask().execute(meal);
    }

    public void deleteMeal(Meal meal) {
        new DeleteTask().execute(meal);
    }

    public void updateMeal(Meal meal) {
        new UpdateTask().execute(meal);
    }

    private static class InsertTask extends AsyncTask<Meal, Void, Void> {

        @Override
        protected Void doInBackground(Meal... meals) {
            if (meals != null && meals.length > 0) {
                database.mealsDao().insertMeal(meals[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Meal, Void, Void> {

        @Override
        protected Void doInBackground(Meal... meals) {
            if (meals != null && meals.length > 0) {
                database.mealsDao().deleteMeal(meals[0]);
            }
            return null;
        }
    }

    private static class UpdateTask extends AsyncTask<Meal, Void, Void> {

        @Override
        protected Void doInBackground(Meal... meals) {
            if (meals != null && meals.length > 0) {
                database.mealsDao().updateMeal(meals[0]);
            }
            return null;
        }
    }
}
