package com.saucelabs.mydemoapp.android.actions;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;

/**
 * Scroll action for nested scrollables
 */
public class NestingAwareScrollAction implements ViewAction {
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
}
