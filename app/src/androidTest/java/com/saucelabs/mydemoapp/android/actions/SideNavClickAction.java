package com.saucelabs.mydemoapp.android.actions;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.saucelabs.mydemoapp.android.R;

import org.hamcrest.Matcher;

public class SideNavClickAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on a child view with specified id.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View v = view.findViewById(R.id.itemTV);
        v.performClick();
    }
}
