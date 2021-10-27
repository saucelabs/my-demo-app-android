package com.saucelabs.mydemoapp.android.view.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.saucelabs.mydemoapp.android.ErrorFlow;
import com.saucelabs.mydemoapp.android.HappyFlow;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.actions.SideNavClickAction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    @ErrorFlow
    public void noCredentialLoginTest() {
        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13, new SideNavClickAction()));

        onView(withId(R.id.loginBtn)).perform(click());

        onView(withText("Username is required")).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    @ErrorFlow
    public void noUsernameLoginTest() {
        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13, new SideNavClickAction()));

        String pass = "10203040";

        // enter a name
        onView(withId(R.id.passwordET)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn)).perform(click());

        onView(withText("Username is required")).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    @ErrorFlow
    public void noPasswordLoginTest() {
        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13, new SideNavClickAction()));

        String name = "bod@example.com";

        // enter a name
        onView(withId(R.id.nameET))
                .perform(typeText(name))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));

        onView(withId(R.id.loginBtn)).perform(click());

        onView(withText("Enter Password")).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    @HappyFlow
    public void succesfulLoginTest() {
        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13, new SideNavClickAction()));

        String name = "bod@example.com";
        String pass = "10203040";

        // enter a name
        onView(withId(R.id.nameET)).perform(typeText(name));
        onView(withId(R.id.passwordET)).perform(typeText(pass),closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn)).perform(click());

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }
}
