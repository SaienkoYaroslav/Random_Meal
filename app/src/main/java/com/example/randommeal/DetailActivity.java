package com.example.randommeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    private EditText etDetailMealName, etDetailRecipe;
    private Button btnSave, btnCancel;
    private MainViewModel viewModel;
    private AdapterMeal adapter;

    private String mealName;
    private String recipe;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        init();
        intent();
        onClickBtnCancel();
        onClickBtnSave();
    }

    void init() {
        etDetailMealName = findViewById(R.id.editText_detail_meal_name);
        etDetailRecipe = findViewById(R.id.editTextTextMultiLine_detail_recipe);
        btnSave = findViewById(R.id.button_save_detail_meal);
        btnCancel = findViewById(R.id.button_cancel_detail_meal);

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
    }

    void intent() {
        Intent intent = getIntent();
        mealName = intent.getStringExtra("mealName");
        recipe = intent.getStringExtra("recipe");
        id = intent.getIntExtra("id", 0);

        etDetailMealName.setText(mealName);
        etDetailRecipe.setText(recipe);
    }

    void onClickBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealName = etDetailMealName.getText().toString().trim();
                recipe = etDetailRecipe.getText().toString().trim();
                Meal meal = new Meal(id, mealName, recipe);
                viewModel.updateMeal(meal);
                onBackPressed();
            }
        });
    }

    void onClickBtnCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}