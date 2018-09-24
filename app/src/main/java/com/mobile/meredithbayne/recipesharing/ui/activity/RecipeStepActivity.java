package com.mobile.meredithbayne.recipesharing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.model.Step;
import com.mobile.meredithbayne.recipesharing.ui.adapters.RecipeStepAdapter;
import com.mobile.meredithbayne.recipesharing.ui.fragment.RecipeStepDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepActivity extends AppCompatActivity {
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_RECIPE = "recipe";
    private static final String STATE_SELECTED_STEP = "selected_step";

    @BindView(R.id.recipe_step_list)
    RecyclerView mStepList;

    RecipeStepAdapter adapter;
    Recipe mRecipe;

    private boolean isTablet;
    Step selectedStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_activity);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (bundle != null) {
            mRecipe = bundle.getParcelable(EXTRA_RECIPE);
            if (mRecipe != null) {
                setTitle(mRecipe.getName());
            }

            mStepList.setLayoutManager(new LinearLayoutManager(this));
            mStepList.setHasFixedSize(true);

            adapter = new RecipeStepAdapter(mRecipe, this::handleStepClick);
            mStepList.setAdapter(adapter);
        }

        if (isTablet && !isStepsEmpty(mRecipe)) {
            if (savedInstanceState != null) {
                selectedStep = savedInstanceState.getParcelable(STATE_SELECTED_STEP);
                showStepDetailsFragment(selectedStep);
            }
        }
    }

    private void showStepDetailsFragment(Step selectedStep) {
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(STATE_SELECTED_STEP, selectedStep);
        fragment.setArguments(arguments);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_detail_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private boolean isStepsEmpty(Recipe recipe) {
        return recipe != null && !recipe.getSteps().isEmpty();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_SELECTED_STEP, selectedStep);
    }

    private void handleStepClick(int position) {
        if (isTablet) {
            selectedStep = mRecipe.getSteps().get(position);
            showStepDetailsFragment(selectedStep);
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(EXTRA_RECIPE, mRecipe);
            intent.putExtra(EXTRA_STEP, mRecipe.getSteps().get(position));
            startActivity(intent);
        }
    }
}
