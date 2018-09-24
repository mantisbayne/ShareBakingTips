package com.mobile.meredithbayne.recipesharing.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.ui.fragment.RecipeStepDetailsFragment;

import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.step_details_title);
        setContentView(R.layout.recipe_step_detail_activity);

        ButterKnife.bind(this);

        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        fragment.setArguments(getIntent().getExtras());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_detail_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
