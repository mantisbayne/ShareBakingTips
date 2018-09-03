package com.mobile.meredithbayne.recipesharing.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;

import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_detail_activity);

        ButterKnife.bind(this);
    }
}
