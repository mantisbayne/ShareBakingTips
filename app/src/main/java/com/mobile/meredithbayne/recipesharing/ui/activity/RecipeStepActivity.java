package com.mobile.meredithbayne.recipesharing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.ui.adapters.RecipeStepAdapter;
import com.mobile.meredithbayne.recipesharing.ui.fragment.RecipeStepDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mobile.meredithbayne.recipesharing.ui.fragment.RecipeListFragment.EXTRA_RECIPE;

public class RecipeStepActivity extends AppCompatActivity {
    public static final String EXTRA_STEP = "step";

    @BindView(R.id.recipe_step_list)
    RecyclerView mStepList;

    RecipeStepAdapter adapter;
    Recipe mRecipe;

    private boolean isTablet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_activity);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mRecipe = bundle.getParcelable(EXTRA_RECIPE);
            if (mRecipe != null) {
                setTitle(mRecipe.getName());
            }

            if (isTablet) {
                if (savedInstanceState == null && !isStepsEmpty(mRecipe)) {
                    Bundle args = new Bundle();
                    args.putParcelable(EXTRA_STEP, mRecipe.getSteps().get(0));
                    RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_step_detail_container, fragment)
                            .commit();
                }
            }

            mStepList.setLayoutManager(new LinearLayoutManager(this));
            mStepList.setHasFixedSize(true);

            adapter = new RecipeStepAdapter(mRecipe, this::handleStepClick);
            mStepList.setAdapter(adapter);
        }
    }

    private boolean isStepsEmpty(Recipe recipe) {
        return recipe != null && !recipe.getSteps().isEmpty();
    }

    private void handleStepClick(int position) {
        Intent intent = new Intent(this, RecipeStepDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, mRecipe);
        startActivity(intent);
    }
}
