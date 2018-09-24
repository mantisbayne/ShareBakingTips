package com.mobile.meredithbayne.recipesharing;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests RecipeListFragment
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListTest extends BaseTest {

    @Before
    public void init() {
        activityActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction();
    }

    @Test
    public void recipeListIsVisible() {
        onView(withId(R.id.recipe_list_container)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeOpensSteps() {
        navigateToStep(1);
        onView(withId(R.id.ingredients_container)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_step_list)).check(matches(isDisplayed()));
    }
}
