package com.saucelabs.mydemoapp.android.view.activities;

import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.saucelabs.mydemoapp.android.BaseTest;
import com.saucelabs.mydemoapp.android.ErrorFlow;
import com.saucelabs.mydemoapp.android.HappyFlow;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.actions.SideNavClickAction;
import com.saucelabs.mydemoapp.android.screenshot.SauceLabsCustomScreenshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class WebViewTest extends BaseTest {

    String url;

    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule
            = new ActivityScenarioRule<>(SplashActivity.class);

    @Before
    public void setUp(){
        url="https://www.google.com";
    }

    @After
    public void cleanup() {

    }

    @Test
    @ErrorFlow
    public void withoutUrlTest() throws InterruptedException {
        waitView(withId(R.id.menuIV));

        SauceLabsCustomScreenshot.capture("launch-screen");

        // Open Drawer to click on navigation.
        onView(withId(R.id.menuIV)).perform(click());

        onView(withId(R.id.menuRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, new SideNavClickAction()));

        onView(withId(R.id.goBtn)).perform(click());
        onView(withId(R.id.fragment_container)).check(matches((isDisplayed())));

        SauceLabsCustomScreenshot.capture("error-screen");

        onView(withText("Please provide a correct https url.")).check(matches(isDisplayed()));
    }

    @Test
    @HappyFlow
    public void webViewTest() {
        waitView(withId(R.id.menuIV));

        // Open Drawer to click on navigation.
        onView(withId(R.id.menuIV)).perform(click());

        onView(withId(R.id.menuRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, new SideNavClickAction()));

        onView(withId(R.id.urlET))
                .perform(typeText(url),closeSoftKeyboard());
        onView(withId(R.id.goBtn))
                .perform(click());

        onView(withText("Please provide a correct https url.")).check(doesNotExist());
    }
}
