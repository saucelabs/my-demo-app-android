package com.saucelabs.mydemoapp.android.utils;

import android.content.Context;

import com.saucelabs.mydemoapp.android.model.CheckoutInfo;
import com.saucelabs.mydemoapp.android.model.CartItemModel;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingletonClass extends Methods {
    private static SingletonClass sSoleInstance;

    private Gson gson;
    public CheckoutInfo checkoutInfo = new CheckoutInfo();
    public CheckoutInfo billingInfo = new CheckoutInfo();
    public List<CartItemModel> cartItemList;
    public boolean isLogin = false;
    public boolean hasVisualChanges = false;

    private SingletonClass() {
        cartItemList = new ArrayList<>();
    }

    public static SingletonClass getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new SingletonClass();
        }

        return sSoleInstance;
    }

    public Gson gson() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }
}
