package com.saucelabs.mydemoapp.android.view.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.saucelabs.mydemoapp.android.BaseTest;
import com.saucelabs.mydemoapp.android.HappyFlow;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.actions.NestingAwareScrollAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DashboardToCheckout extends BaseTest {

    //This ViewAction For Nested ScrollView
    private final ViewAction scroll = new NestingAwareScrollAction();

    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule = new ActivityScenarioRule<>(SplashActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    @HappyFlow
    public void dashboardProductTest() {
        // Splash screen
        waitView(withId(R.id.menuIV));

        // Check RecyclerView
        onView(withId(R.id.productRV)).check(matches(isDisplayed()));

        // AfterSelect The Item Which You Want
        onView(withId(R.id.productRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Confirm That On First Item Click
        onView(withId(R.id.productTV)).check(matches(withText("Sauce Lab Back Packs")));

        // Click To Increment
        onView(withId(R.id.plusIV))
                .perform(scroll)
                .perform(click());

        // Click To Add to cart
        onView(withId(R.id.cartBt))
                .perform(scroll)
                .perform(click());

        // Click On Cart Item
        onView(withId(R.id.cartRL))
                .perform(click());

        // Click On Cart Item:: Fragment Product Detail
        onView(withId(R.id.cartBt))
                .perform(click());

        // Login User Fragment
        String name = "bod@example.com";
        String pass = "10203040";

        onView(withId(R.id.nameET))
                .perform(typeText(name))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.passwordET))
                .perform(typeText(pass))
                .perform(closeSoftKeyboard());

        // Check The Screen
        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn))
                .perform(scroll)
                .perform(click());
        // End................................................

        // Fragment Check Out Info
        String uName="Rebecca Winter";
        String address1="Mandorley 112";
        String address2="Entrance 1";
        String city="Truro";
        String state="Cornwall";
        String zipCode="89750";
        String country="United Kingdom";

        onView(withId(R.id.fullNameET))
                .perform(scroll)
                .perform(typeText(uName))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.address1ET))
                .perform(scroll)
                .perform(typeText(address1))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.address2ET))
                .perform(scroll)
                .perform(typeText(address2))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.cityET))
                .perform(scroll)
                .perform(typeText(city))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.stateET))
                .perform(scroll)
                .perform(typeText(state))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.zipET))
                .perform(scroll)
                .perform(typeText(zipCode))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.countryET))
                .perform(scroll)
                .perform(typeText(country))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.paymentBtn))
                .perform(scroll)
                .perform(click());

        // End................................................

        // Fragment CheckOut Information
        String name_Checkout="Rebecca Winter";
        String card_no="3258125675687891";
        String date="03/25";
        String securityCode="123";

        onView(withId(R.id.nameET))
                .perform(scroll)
                .perform(typeText(name_Checkout), closeSoftKeyboard());
        onView(withId(R.id.cardNumberET))
                .perform(scroll)
                .perform(typeText(card_no), closeSoftKeyboard());

        onView(withId(R.id.expirationDateET))
                .perform(scroll)
                .perform(typeText(date), closeSoftKeyboard());
        onView(withId(R.id.securityCodeET))
                .perform(scroll)
                .perform(typeText(securityCode), closeSoftKeyboard());

        onView(withText("Review Order"))
                .perform(click());
        // End................................................

        // Place Order Fragment
        onView(withText("Place Order"))
                .perform(click());
        // End................................................

        // CheckOut Complete
        onView(withText("Continue Shopping"))
                .perform(scroll)
                .perform(click());
        // End................................................

        // Assert we are back to main page
        onView(withId(R.id.productRV))
                .check(matches(isDisplayed()));
    }
}
