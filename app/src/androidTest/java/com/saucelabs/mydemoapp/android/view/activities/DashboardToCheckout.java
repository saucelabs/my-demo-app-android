package com.saucelabs.mydemoapp.android.view.activities;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.saucelabs.mydemoapp.android.R;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DashboardToCheckout {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        activityRule.getScenario().moveToState(Lifecycle.State.RESUMED);
    }

    @Test
    public void dashboard_Product_Test(){

        //Note::Sleep Only Use For Testing Purpose Wait Screen

        //Check RecyclerView
        onView(withId(R.id.productRV)).check(matches(isDisplayed()));

        sleep(1000);
        //AfterSelect The Item Which You Want
        onView(withId(R.id.productRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Confirm That On First Item Click
        onView(withId(R.id.productTV)).check(matches(withText("Sauce Lab Back Packs")));

        //Click To Increment
        sleep(1000);
        onView(withId(R.id.plusIV))
                .perform(scrollTo())
                .perform(click());

        //Click To Add to cart
        sleep(1000);
        onView(withId(R.id.cartBt))
                .perform(scrollTo())
                .perform(click());

        //Click On Cart Item
        sleep(1000);
        onView(withId(R.id.cartRL))
                .perform(click());

        //Click On Cart Item:: Fragment Product Detail
        sleep(1000);
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
        //Check The Screen
        onView(withId(R.id.nameET)).check(matches(withText(name)));
        onView(withId(R.id.passwordET)).check(matches(withText(pass)));

        onView(withId(R.id.loginBtn)).perform(click());
        //End................................................

        //Fragment Check Out Info
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
                .perform(customScrollTo);

        onView(withId(R.id.countryET))
                .perform(typeText(country))
                .perform(closeSoftKeyboard())
                .perform(customScrollTo);

        onView(withId(R.id.paymentBtn))
                .perform(customScrollTo)
                .perform(click());

        //End................................................

        //Fragment CheckOut Information
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
        //End................................................

        //Place Order Fragment
        onView(withId(R.id.paymentBtn)).perform(click());
        //End................................................

        //CheckOut Complete
        onView(withId(R.id.shoopingBt)).perform(click());
        //End................................................

        sleep(3000);

    }

    //This ViewAction For Nested ScrollView
    ViewAction customScrollTo = new ViewAction() {

        @Override
        public Matcher<View> getConstraints() {
            return allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), isDescendantOfA(anyOf(
                    isAssignableFrom(ScrollView.class),
                    isAssignableFrom(HorizontalScrollView.class),
                    isAssignableFrom(NestedScrollView.class)))
            );
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public void perform(UiController uiController, View view) {
            new ScrollToAction().perform(uiController, view);
        }
    };

}
