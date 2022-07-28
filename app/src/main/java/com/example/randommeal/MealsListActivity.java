package com.example.randommeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MealsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddNewMeal;
    private AdapterMeal adapter;
    private final List<Meal> meals = new ArrayList();
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        init();
        adapter();
        clickOnAddNewMeal();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycle_view_recipes);
        fabAddNewMeal = findViewById(R.id.floatingActionButton_add_new_meal);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
    }

    private void adapter() {
        adapter = new AdapterMeal(meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getData();
        recyclerView.setAdapter(adapter);
        adapter.setOnMealClickListener(new AdapterMeal.OnMealClickListener() {
            @Override
            public void onMealClick(int position) {
                Meal meal = adapter.getMeals().get(position);
                int id = meal.getId();
                String mealName = meal.getMealName();
                String recipe = meal.getRecipe();
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("mealName", mealName);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }
        });
    }


    private void clickOnAddNewMeal() {
        fabAddNewMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNewMealActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 121:
                Meal meal = adapter.getMeals().get(item.getGroupId());
                int id = meal.getId();
                String mealName = meal.getMealName();
                String recipe = meal.getRecipe();
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("mealName", mealName);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
                return true;
            case 122:
                remove(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);


        }
    }

    private void remove(int position) {
        Meal meal = adapter.getMeals().get(position);
        viewModel.deleteMeal(meal);
    }

    private void getData() {
        LiveData<List<Meal>> mealsFromBd = viewModel.getMeals();
        mealsFromBd.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> mealsFromLiveData) {
                adapter.setMeals(mealsFromLiveData);
            }
        });
    }

}