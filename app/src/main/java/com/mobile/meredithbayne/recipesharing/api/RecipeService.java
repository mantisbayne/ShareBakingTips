package com.mobile.meredithbayne.recipesharing.api;

import com.mobile.meredithbayne.recipesharing.model.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipes>> getRecipes();
}
