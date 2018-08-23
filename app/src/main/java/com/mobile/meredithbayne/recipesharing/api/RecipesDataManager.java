package com.mobile.meredithbayne.recipesharing.api;

import android.support.annotation.NonNull;

import com.mobile.meredithbayne.recipesharing.model.Recipe;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RecipesDataManager implements Serializable {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static volatile RecipesDataManager sharedInstance = new RecipesDataManager();
    private RecipeService recipeService;

    private RecipesDataManager() {
        if (sharedInstance != null)
            throw new RuntimeException("Only single instance allowed, use getInstance()");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeService = retrofit.create(RecipeService.class);
    }

    public static RecipesDataManager getInstance() {
        if (sharedInstance == null) {
            synchronized (RecipesDataManager.class) {
                if (sharedInstance == null)
                    sharedInstance = new RecipesDataManager();
            }
        }

        return sharedInstance;
    }

    public void getRecipeList(final RecipesCallback<List<Recipe>> recipesCallback) {
        recipeService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call,
                                   @NonNull Response<List<Recipe>> response) {
                recipesCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    Timber.e("Request canceled");
                    recipesCallback.onCancel();
                } else {
                    Timber.e(t);
                    recipesCallback.onResponse(null);
                }
            }
        });
    }
}
