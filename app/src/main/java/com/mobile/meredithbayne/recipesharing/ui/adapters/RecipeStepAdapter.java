package com.mobile.meredithbayne.recipesharing.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Recipe mRecipe;
    private RecipeStepClickListener listener;

    public interface RecipeStepClickListener {
        void onStepClick(int position);
    }

    public RecipeStepAdapter(Recipe mRecipe, RecipeStepClickListener listener) {
        this.mRecipe = mRecipe;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_ingredients, parent, false);
            return new IngredientsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_step_list_item, parent, false);
            return new RecipeStepViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class IngredientsViewHolder extends RecyclerView.ViewHolder {
        IngredientsViewHolder(View view) {
            super(view);
        }
    }

    private class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        RecipeStepViewHolder(View view) {
            super(view);
        }
    }
}
