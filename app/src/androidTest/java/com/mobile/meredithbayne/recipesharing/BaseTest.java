package com.mobile.meredithbayne.recipesharing;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.mobile.meredithbayne.recipesharing.ui.activity.RecipeListActivity;

import org.junit.Rule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public abstract class BaseTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> activityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    public static void navigateToDetails(int stepPosition) {
        onView(withId(R.id.recipe_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepPosition, click()));
    }

    public static void navigateToStep(int recipePosition) {
        onView(withId(R.id.recipe_list_container))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }
}
