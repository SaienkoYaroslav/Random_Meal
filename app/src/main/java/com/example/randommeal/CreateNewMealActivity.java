package com.example.randommeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CreateNewMealActivity extends AppCompatActivity {

    private EditText etUserMealName, etUserRecipe;
    private Button buttonAddUserMeal;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_meal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        init();
        onClickAddUserMeal();
    }

    private void init() {
        etUserMealName = findViewById(R.id.editText_detail_meal_name);
        etUserRecipe = findViewById(R.id.editTextTextMultiLine_detail_recipe);
        buttonAddUserMeal = findViewById(R.id.button_save_detail_meal);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
    }

    private void onClickAddUserMeal() {
        buttonAddUserMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = etUserMealName.getText().toString().trim();
                String recipe = etUserRecipe.getText().toString().trim();
                if (recipe.isEmpty()) {
                    recipe = "";
                }
                if (isFilled(mealName)) {
                        Meal meal = new Meal(mealName, recipe);
                        viewModel.insertMeal(meal);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MealsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private boolean isFilled(String mealName) {
        return !mealName.isEmpty();
    }

}