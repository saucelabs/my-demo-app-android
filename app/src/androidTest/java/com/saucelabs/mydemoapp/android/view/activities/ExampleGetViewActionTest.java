package com.saucelabs.mydemoapp.android.view.activities;

import com.saucelabs.mydemoapp.android.BaseTest;
import com.saucelabs.visual.espresso.GetViewAction;

import org.junit.Test;

public class ExampleGetViewActionTest extends BaseTest {

    @Test
    public void testGetViewWithWait() {
        GetViewAction getViewAction = new GetViewAction();
    }
}
