package com.mobile.meredithbayne.recipesharing.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Ingredient;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.model.Step;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                    .inflate(R.layout.recipe_step_ingredients_list_item, parent, false);
            return new IngredientsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_step_list_item, parent, false);
            return new RecipeStepViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Set the first item in RecyclerView to the ingredients
        if (holder instanceof IngredientsViewHolder) {
            IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
            List<Ingredient> ingredients = mRecipe.getIngredients();
            String ingredientsText = buildIngredientsText(ingredients);
            ingredientsViewHolder.mIngredientsBody.setText(ingredientsText);
        } else if (holder instanceof RecipeStepViewHolder) {
            RecipeStepViewHolder recipeStepViewHolder = (RecipeStepViewHolder) holder;
            List<Step> steps = mRecipe.getSteps();
            Step step = steps.get(position - 1);
            recipeStepViewHolder.mRecipeStepOrder.setText(String.valueOf(step.getId()));
            recipeStepViewHolder.mRecipeStepName.setText(step.getShortDescription());
        }
    }

    private String buildIngredientsText(List<Ingredient> ingredients) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            builder.append(String.format(Locale.getDefault(), "• %s (%s %s)",
                    ingredient.getIngredient(), String.valueOf(ingredient.getQuantity()),
                    ingredient.getMeasure()));
            appendNewLine(i, builder, ingredients);
        }
        return builder.toString();
    }

    private void appendNewLine(int index, StringBuilder builder, List<Ingredient> ingredients) {
        int lastIngredient = ingredients.size() - 1;
        if (index != lastIngredient)
            builder.append("\n");
    }

    @Override
    public int getItemCount() {
        return mRecipe
                .getSteps()
                .size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_body)
        TextView mIngredientsBody;

        IngredientsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_step_order)
        TextView mRecipeStepOrder;

        @BindView(R.id.recipe_step_name)
        TextView mRecipeStepName;

        RecipeStepViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> listener.onStepClick(getAdapterPosition()));
        }
    }
}
