package com.mobile.meredithbayne.recipesharing.ui.presenter;

import com.mobile.meredithbayne.recipesharing.model.Recipe;

import java.util.List;

public interface RecipeListInterface {
    void displayProgressBar();
    void hideProgressBar();
    void displayRecipes(List<Recipe> recipes);
    void displayError(String errorMessage);
}
