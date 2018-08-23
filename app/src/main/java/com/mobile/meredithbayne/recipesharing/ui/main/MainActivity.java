package com.mobile.meredithbayne.recipesharing.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            presenter = new MainPresenter(this);
            presenter.getRecipes();
        }
    }

    @Override
    public void displayProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void displayRecipes(List<Recipe> recipes) {

    }

    @Override
    public void displayError(String errorMessage) {

    }
}
