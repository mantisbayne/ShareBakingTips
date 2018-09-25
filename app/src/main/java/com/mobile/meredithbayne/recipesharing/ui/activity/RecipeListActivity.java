package com.mobile.meredithbayne.recipesharing.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.ui.fragment.RecipeListFragment;
import com.mobile.meredithbayne.recipesharing.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {
    @BindView(R.id.no_connection_error)
    TextView mConnectionError;

    @BindView(R.id.no_connection_error_image)
    ImageView mConnectionErrorImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!NetworkUtils.isNetworkConnected(this)) {
            mConnectionError.setVisibility(View.VISIBLE);
            mConnectionErrorImage.setVisibility(View.VISIBLE);
        }

        RecipeListFragment fragment = new RecipeListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_list_fragment, fragment)
                .commit();
    }
}
