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
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.saucelabs.mydemoapp.android.BaseTest;
import com.saucelabs.mydemoapp.android.ErrorFlow;
import com.saucelabs.mydemoapp.android.HappyFlow;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.actions.NestingAwareScrollAction;
import com.saucelabs.mydemoapp.android.actions.SideNavClickAction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest extends BaseTest {

    //This ViewAction For Nested ScrollView
    private final ViewAction scroll = new NestingAwareScrollAction();

    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule = new ActivityScenarioRule<>(SplashActivity.class);

    @Test
    @ErrorFlow
    public void noCredentialLoginTest() {
        waitView(withId(R.id.menuIV));

        onView(withId(R.id.menuIV))
                .perform(click());

        waitView(withId(R.id.menuRV));

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(RecyclerViewActions.actionOnItemAtPosition(11, new SideNavClickAction()));

        onView(withId(R.id.loginBtn))
                .perform(scroll)
                .perform(click());

        onView(withText("Username is required"))
                .perform(scroll)
                .check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    @ErrorFlow
    public void noUsernameLoginTest() {
        waitView(withId(R.id.menuIV));

        onView(withId(R.id.menuIV))
                .perform(click());

        waitView(withId(R.id.menuRV));

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(RecyclerViewActions.actionOnItemAtPosition(11, new SideNavClickAction()));

        String pass = "10203040";

        // enter a name
        onView(withId(R.id.passwordET)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn))
                .perform(scroll)
                .perform(click());

        onView(withText("Username is required"))
                .perform(scroll)
                .check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    @ErrorFlow
    public void noPasswordLoginTest() {
        waitView(withId(R.id.menuIV));

        onView(withId(R.id.menuIV))
                .perform(click());

        waitView(withId(R.id.menuRV));

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(RecyclerViewActions.actionOnItemAtPosition(11, new SideNavClickAction()));

        String name = "bod@example.com";

        // enter a name
        onView(withId(R.id.nameET))
                .perform(typeText(name), closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));

        onView(withId(R.id.loginBtn))
                .perform(scroll)
                .perform(click());

        onView(withText("Enter Password"))
                .perform(scroll)
                .check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    @HappyFlow
    public void succesfulLoginTest() {
        waitView(withId(R.id.menuIV));

        onView(withId(R.id.menuIV))
                .perform(click());

        waitView(withId(R.id.menuRV));

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(RecyclerViewActions.actionOnItemAtPosition(11, new SideNavClickAction()));

        String name = "bod@example.com";
        String pass = "10203040";

        // enter a name
        onView(withId(R.id.nameET)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.passwordET)).perform(typeText(pass),closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn))
                .perform(scroll)
                .perform(click());

        Espresso.pressBack();
        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }
}
