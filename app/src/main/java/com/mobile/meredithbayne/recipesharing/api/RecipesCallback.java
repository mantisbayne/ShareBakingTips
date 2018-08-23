package com.mobile.meredithbayne.recipesharing.api;

public interface RecipesCallback<T> {
    void onResponse(T res);

    void onCancel();
}
