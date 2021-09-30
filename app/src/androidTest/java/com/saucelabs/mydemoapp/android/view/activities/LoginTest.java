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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        activityRule.getScenario().moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void noCredentialLoginTest() {

        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        onView(withId(R.id.loginBtn)).perform(click());

        sleep(1000);
        Espresso.pressBack();

    }

    @Test
    public void noUsernameLoginTest() {

        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        String pass = "12345678";

        // enter a name
        onView(withId(R.id.passwordET)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn)).perform(click());

        sleep(1000);
        Espresso.pressBack();

    }

    @Test
    public void noPasswordLoginTest() {

        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        String name = "bod@example.com";

        // enter a name
        onView(withId(R.id.nameET))
                .perform(typeText(name))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));

        onView(withId(R.id.loginBtn)).perform(click());

        sleep(1000);
        Espresso.pressBack();

    }

    @Test
    public void succesfulLoginTest() {

        onView(withId(R.id.menuIV))
                .perform(click());

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(13,
                        SideNavView.clickChildViewWithId(R.id.itemTV)));

        String name = "bod@example.com";
        String pass = "12345678";

        // enter a name
        onView(withId(R.id.nameET)).perform(typeText(name));
        onView(withId(R.id.passwordET)).perform(typeText(pass),closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn)).perform(click());

        Espresso.pressBack();

    }
}
