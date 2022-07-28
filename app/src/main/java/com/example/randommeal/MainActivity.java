package com.example.randommeal;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibGoToRecipeList;
    private TextView tvMealName, tvRecipe;
    private Button bSearchOnInternet, bGetRandomMeal;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    private String mealName;
    private String recipe;
    private boolean isVisible;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        init();

        if (savedInstanceState !=null) {
            mealName = savedInstanceState.getString("mealName");
            recipe = savedInstanceState.getString("recipe");
            isVisible = savedInstanceState.getBoolean("isVisible");
            tvMealName.setText(mealName);
            tvRecipe.setText(recipe);
            if (isVisible) {
                bSearchOnInternet.setVisibility(View.VISIBLE);
            }
        }

        clickOnRecipeList();
        clickOnGetRandomMeal();
        clickOnSearch();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mealName", mealName);
        outState.putString("recipe", recipe);
        outState.putBoolean("isVisible", isVisible);
    }

    void init() {
        ibGoToRecipeList = findViewById(R.id.imageButton_go_to_recipe_list);
        tvMealName = findViewById(R.id.textView_meal_name);
        tvRecipe = findViewById(R.id.textView_recipe);
        bSearchOnInternet = findViewById(R.id.button_search_on_internet);
        bGetRandomMeal = findViewById(R.id.button_get_random_meal);
    }

    void clickOnRecipeList() {
        ibGoToRecipeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MealsListActivity.class);
                startActivity(intent);
            }
        });
    }

    void clickOnSearch() {
        bSearchOnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = tvMealName.getText().toString().trim() + getString(R.string.search_recipe);
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, search);
                startActivity(intent);
            }
        });
    }

    void clickOnGetRandomMeal() {
        bGetRandomMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListAllMeals();
                bSearchOnInternet.setVisibility(View.VISIBLE);
                isVisible = true;
            }
        });
    }

    public void getListAllMeals() {
        final MealDao mDAO = MealsDatabase.getInstance(getApplicationContext()).mealsDao();
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Meal> mealsList = mDAO.getListMeal();
                int listSize = mealsList.size();
                int randomNumber = (int) (Math.random() * listSize);
                Meal meal = mealsList.get(randomNumber);
                mealName = meal.getMealName();
                recipe = meal.getRecipe();
                tvMealName.post(new Runnable() {
                    @Override
                    public void run() {
                        tvMealName.setText(mealName);
                    }
                });
                tvRecipe.post(new Runnable() {
                    @Override
                    public void run() {
                        tvRecipe.setText(recipe);
                    }
                });
            }
        });
    }


}