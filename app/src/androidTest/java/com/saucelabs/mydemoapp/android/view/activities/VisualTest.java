package com.saucelabs.mydemoapp.android.view.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

import android.text.TextUtils;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.saucelabs.mydemoapp.android.BaseTest;
import com.saucelabs.mydemoapp.android.BuildConfig;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.actions.NestingAwareScrollAction;
import com.saucelabs.mydemoapp.android.actions.SideNavClickAction;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.saucelabs.visual.VisualCheckOptions;
import com.saucelabs.visual.VisualClient;
import com.saucelabs.visual.junit.TestMetaInfoRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class VisualTest extends BaseTest {

    private final ViewAction scroll = new NestingAwareScrollAction();

    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule = new ActivityScenarioRule<>(SplashActivity.class);

    @Rule
    public TestMetaInfoRule testMetaInfoRule = new TestMetaInfoRule();

    static VisualClient client = VisualClient.builder(BuildConfig.SAUCE_USERNAME, BuildConfig.SAUCE_ACCESS_KEY)
            .buildName("My Demo App")
            .build();

    @Before
    public void removeLogin() {
        SingletonClass.getInstance().isLogin = false;
        SingletonClass.getInstance().hasVisualChanges = false;
    }

    @Test
    public void checkAppCatalog() {

        waitView(withId(R.id.menuIV));

        client.sauceVisualCheck("Startup");

        onView(withId(R.id.menuIV))
                .perform(click());

        waitView(withId(R.id.menuRV));

        onView(withId(R.id.menuRV))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, new SideNavClickAction()));

        String visualCheck = InstrumentationRegistry.getArguments().getString("visualCheck", "");
        String name = TextUtils.isEmpty(visualCheck) ? "bod@example.com" : "visual@example.com";
        String pass = "10203040";

        waitView(withId(R.id.menuRV));

        // enter a name
        onView(withId(R.id.nameET)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.passwordET)).perform(typeText(pass), closeSoftKeyboard());

        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn))
                .perform(scroll)
                .perform(click());

        waitView(withId(R.id.menuIV));

        client.sauceVisualCheck("App Catalog", VisualCheckOptions.builder()
                .ignore( // Ignore any changes on the first image
                        allOf(withParentIndex(0),  // Get the first element in ViewGroup (an image)
                                withParent(allOf(
                                        withParentIndex(0),  // Get the first element in RV (a ViewGroup)
                                        withParent(withId(R.id.productRV)))))
                )
                .build());

        onView(withId(R.id.menuIV)).check(matches(isDisplayed()));
    }

    @Test
    public void captureOnlyAppCatalog() {
        waitView(withId(R.id.menuIV));
        client.sauceVisualCheck("Catalog Fragment", VisualCheckOptions.builder()
                .clipElement(withId(R.id.scrollView))
                .build());
    }

    @AfterClass
    public static void tearDown() {
        client.finish();
    }
}
