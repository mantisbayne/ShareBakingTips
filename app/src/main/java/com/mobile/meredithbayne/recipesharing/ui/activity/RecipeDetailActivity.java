package com.mobile.meredithbayne.recipesharing.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.ui.fragment.RecipeListFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mRecipe = bundle.getParcelable(RecipeListFragment.EXTRA_RECIPE);
            if (mRecipe != null) {
                setTitle(mRecipe.getName());
            }
        }
    }
}
