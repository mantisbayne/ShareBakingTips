package com.mobile.meredithbayne.recipesharing.ui.main;

import com.mobile.meredithbayne.recipesharing.model.Recipe;

import java.util.List;

public interface MainInterface {
    void displayProgressBar();
    void hideProgressBar();
    void displayRecipes(List<Recipe> recipes);
    void displayError(String errorMessage);
}
