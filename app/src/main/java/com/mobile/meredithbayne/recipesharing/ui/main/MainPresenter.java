package com.mobile.meredithbayne.recipesharing.ui.main;

import com.mobile.meredithbayne.recipesharing.api.RecipeService;
import com.mobile.meredithbayne.recipesharing.api.RecipesClient;
import com.mobile.meredithbayne.recipesharing.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter implements MainPresenterInterface {
    private static final String TAG = MainPresenter.class.getSimpleName();

    private MainInterface mainInterface;
    private Disposable disposable;

    public MainPresenter(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    private Observable<List<Recipe>> getRecipesObservable() {
        return RecipesClient.getRetrofit().create(RecipeService.class)
                .getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observer<List<Recipe>> getRecipesObserver() {
        return new Observer<List<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(List<Recipe> recipes) {
                Timber.d(TAG, "onNext");
                mainInterface.displayRecipes(recipes);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                e.printStackTrace();
                mainInterface.displayError("Could not fetch recipes");
            }

            @Override
            public void onComplete() {
                Timber.d(TAG, "onComplete");
                mainInterface.hideProgressBar();
            }
        };
    }

    @Override
    public void getRecipes() {
        getRecipesObservable().subscribeWith(getRecipesObserver());
    }
}
