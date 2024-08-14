package com.saucelabs.mydemoapp.android.model;

public class MenuItem {
    public final String name;
    public final String contentDescription;

    public MenuItem(String name) {
        this.name = name;
        this.contentDescription = null;
    }

    public MenuItem(String name, String contentDescription) {
        this.name = name;
        this.contentDescription = contentDescription;
    }
}
