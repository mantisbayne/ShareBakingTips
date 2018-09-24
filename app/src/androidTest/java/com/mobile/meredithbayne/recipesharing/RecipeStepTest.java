package com.mobile.meredithbayne.recipesharing;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mobile.meredithbayne.recipesharing.ui.activity.RecipeStepActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests RecipeStepActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeStepTest extends BaseTest {
    @Before
    public void init() {
        activityActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction();
        navigateToStep(0);
    }

    @Test
    public void recipeStepIngredientsAreVisible() {
        onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_body)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_header)).check(matches(isDisplayed()));
    }

    @Test
    public void recipeStepListIsVisible() {
        onView(withId(R.id.recipe_step_list)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeOpensDetails() {
        navigateToDetails(1);
        onView(withId(R.id.step_details_container)).check(matches(isDisplayed()));
    }
}
