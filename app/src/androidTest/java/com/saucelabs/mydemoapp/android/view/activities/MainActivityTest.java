package com.saucelabs.mydemoapp.android.view.activities;

import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.saucelabs.mydemoapp.android.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        activityRule.getScenario().moveToState(Lifecycle.State.RESUMED);

    }


    @Test
    public void sendFeedbackDialogTest() {

        String input="I am Android Developer Working with Testing";

        onView(withId(R.id.menuIV)).perform(click());

        onView(withId(R.id.menuRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(8,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        sleep(3000);
        Espresso.pressBack();

    }

    @Test
    public void remoteSupportDialogTest() {

        onView(withId(R.id.menuIV)).perform(click());

        onView(withId(R.id.menuRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(9,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        sleep(3000);
        Espresso.pressBack();

    }

    @Test
    public void contactFormDialogTest() {

        onView(withId(R.id.menuIV)).perform(click());

        onView(withId(R.id.menuRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(10,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        sleep(3000);
        Espresso.pressBack();

    }

    @Test
    public void surveyDialogTest() {

        onView(withId(R.id.menuIV)).perform(click());

        onView(withId(R.id.menuRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(11,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        sleep(3000);
        Espresso.pressBack();

    }




}