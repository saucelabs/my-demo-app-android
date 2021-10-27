package com.saucelabs.mydemoapp.android.view.activities;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.saucelabs.mydemoapp.android.HappyFlow;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.actions.NestingAwareScrollAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DashboardToCheckout {

    //This ViewAction For Nested ScrollView
    private final ViewAction scroll = new NestingAwareScrollAction();

    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule = new ActivityScenarioRule<>(SplashActivity.class);

    @Before
    public void setUp(){

    }

    @HappyFlow
    @Test
    public void dashboard_Product_Test() throws InterruptedException {
        // Splash screen
        Thread.sleep(5000);

        // Check RecyclerView
        onView(withId(R.id.productRV)).check(matches(isDisplayed()));

        // AfterSelect The Item Which You Want
        onView(withId(R.id.productRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Confirm That On First Item Click
        onView(withId(R.id.productTV)).check(matches(withText("Sauce Lab Back Packs")));

        // Click To Increment
        onView(withId(R.id.plusIV))
                .perform(scrollTo())
                .perform(click());

        // Click To Add to cart
        onView(withId(R.id.cartBt))
                .perform(scrollTo())
                .perform(click());

        // Click On Cart Item
        onView(withId(R.id.cartRL))
                .perform(click());

        // Click On Cart Item:: Fragment Product Detail
        onView(withId(R.id.cartBt))
                .perform(click());

        // Login User Fragment
        String name = "bod@example.com";
        String pass = "12345678";

        onView(withId(R.id.nameET))
                .perform(typeText(name))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.passwordET))
                .perform(typeText(pass))
                .perform(closeSoftKeyboard());

        // Check The Screen
        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn)).perform(click());
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
                .perform(typeText(uName))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.address1ET))
                .perform(typeText(address1))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.address2ET))
                .perform(typeText(address2))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.cityET))
                .perform(typeText(city))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.stateET))
                .perform(typeText(state))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.zipET))
                .perform(typeText(zipCode))
                .perform(closeSoftKeyboard())
                .perform(scroll);

        onView(withId(R.id.countryET))
                .perform(typeText(country))
                .perform(closeSoftKeyboard())
                .perform(scroll);

        onView(withId(R.id.paymentBtn))
                .perform(scroll)
                .perform(click());

        // End................................................

        // Fragment CheckOut Information
        String name_Checkout="Rebecca Winter";
        String card_no="3258125675687891";
        String date="03/25";
        String securityCode="123";

        onView(withId(R.id.nameET)).perform(typeText(name_Checkout),closeSoftKeyboard());
        onView(withId(R.id.cardNumberET)).perform(typeText(card_no),closeSoftKeyboard());

        onView(withId(R.id.expirationDateET)).perform(typeText(date),closeSoftKeyboard());
        onView(withId(R.id.securityCodeET))
                .perform(typeText(securityCode),closeSoftKeyboard());

        onView(withId(R.id.paymentBtn)).perform(click());
        // End................................................

        // Place Order Fragment
        onView(withId(R.id.paymentBtn)).perform(click());
        // End................................................

        // CheckOut Complete
        onView(withId(R.id.shoopingBt)).perform(click());
        // End................................................

        // Assert we are back to main page
        onView(withId(R.id.productRV)).check(matches(isDisplayed()));
    }
}
