package com.saucelabs.mydemoapp.android.view.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.SystemClock;
import static org.junit.Assert.assertEquals;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.saucelabs.mydemoapp.android.BaseTest;
import com.saucelabs.mydemoapp.android.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ScreenRotationTest extends BaseTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public void screenOrientationTest() {
        // Splash screen
        waitView(withId(R.id.menuIV));

        // Verify the screen orientation is portrait
        onView(isRoot()).check(matchesOrientation(Configuration.ORIENTATION_PORTRAIT));

        // set screen orientation to landscape
        activityRule.getScenario().onActivity(activity -> {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        // Pause for 2 seconds for demo purpose
        SystemClock.sleep(2000);
        // Verify the screen orientation is landscape
        onView(isRoot()).check(matchesOrientation(Configuration.ORIENTATION_LANDSCAPE));

        // set screen orientation to portrait again
        activityRule.getScenario().onActivity(activity -> {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
        // Pause for 2 seconds for demo purpose
        SystemClock.sleep(2000);
        // Verify the screen orientation is portrait
        onView(isRoot()).check(matchesOrientation(Configuration.ORIENTATION_PORTRAIT));
    }

    public static ViewAssertion matchesOrientation(int orientation) {
        return (view, noViewFoundException) -> {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            int actualOrientation = view.getContext().getResources().getConfiguration().orientation;
            assertEquals(orientation, actualOrientation);
        };
    }
}
