package com.saucelabs.mydemoapp.android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.view.View;

import org.hamcrest.Matcher;

public class BaseTest {

	protected void waitView(Matcher<View> viewMatcher) {
		waitView(viewMatcher, 5000);
	}

	protected void waitView(Matcher<View> viewMatcher, long millis) {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start <= millis) {
			try {
				onView(viewMatcher).check(matches(isDisplayed()));
				break;
			} catch (Throwable t) {
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
