package com.elorri.android.capstone.tieus;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.elorri.android.capstone.tieus.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by Elorri on 02/07/2016.
 */

//Hey Junit run this with the android AndroidJUnit4.class
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestEspressoListToDetail {

    @Rule //Use to tell witch activity we want to open for this test
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void selectContact() {
//        onView(withText("Say hello!")).perform(click());
//        onView(withId(R.id.item)).check(matches(withText("Hello, World!")));
//        onView(withId(R.id.item)).perform(click());

        //We are not displaying the detail view yet
        onView(withId(R.id.fragment_detail_view)).check(doesNotExist());

        //If using for verifying if a view is visible use isDisplayed() but for other verification incase if invisible and gone use withEffectiveVisibilty()
        onView(withId(R.id.fragment_main_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        //We are displaying the contacts list
        onView(withId(R.id.fragment_main_view)).check(matches(isDisplayed()));

        //Click on first item
        //We use onData here because the click happen on an adapter, not a view and also because
        // we don't know if the view is inside the view hierarchy
        onData(anything()).inAdapterView(withId(R.id.recyclerview))
                .atPosition(0)
                .perform(click());



        //Click on 5th item
        onData(anything()).inAdapterView(withId(R.id.recyclerview))
                .atPosition(4)
                .perform(click());


        //We are displaying the contacts list
        onView(withId(R.id.fragment_detail_view)).check(matches(isDisplayed()));


    }
}