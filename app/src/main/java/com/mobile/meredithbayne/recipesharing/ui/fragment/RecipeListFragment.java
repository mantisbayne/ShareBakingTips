package com.mobile.meredithbayne.recipesharing.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.ui.activity.RecipeStepActivity;
import com.mobile.meredithbayne.recipesharing.ui.adapters.RecipeListAdapter;
import com.mobile.meredithbayne.recipesharing.ui.presenter.RecipeListInterface;
import com.mobile.meredithbayne.recipesharing.ui.presenter.RecipeListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements RecipeListInterface {
    public static final String EXTRA_RECIPE = "recipe";

    @BindView(R.id.recipe_list_container)
    RecyclerView mRecipeList;
    @BindView(R.id.empty_recipe_list_message)
    TextView mEmptyMessage;
    @BindView(R.id.recipe_list_loading)
    ProgressBar mLoading;

    RecipeListAdapter adapter;
    RecipeListPresenter presenter;

    Recipe recipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        ButterKnife.bind(this, root);

        mRecipeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecipeList.setHasFixedSize(true);

        presenter = new RecipeListPresenter(this);
        presenter.getRecipes();

        return root;
    }

    @Override
    public void displayProgressBar() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayRecipes(List<Recipe> recipes) {
        if (recipes != null) {
            adapter = new RecipeListAdapter(getActivity(), recipes,
                    this::handleRecipeClick);
            mRecipeList.setAdapter(adapter);
            mRecipeList.setVisibility(View.VISIBLE);
            mLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void handleRecipeClick(int position) {
        recipe = adapter.getRecipeItem(position);
        Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void displayError(String errorMessage) {
        mRecipeList.setVisibility(View.INVISIBLE);
        mEmptyMessage.setVisibility(View.VISIBLE);
    }
}
