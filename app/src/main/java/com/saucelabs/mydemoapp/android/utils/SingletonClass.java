package com.saucelabs.mydemoapp.android.utils;

import android.content.Context;

import com.saucelabs.mydemoapp.android.model.CheckoutInfo;
import com.saucelabs.mydemoapp.android.model.CartItemModel;

import com.google.gson.Gson;
import com.testfairy.TestFairy;

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

    public void syncCartToTestFairy(Context context) {
        if (context != null) {
            File file = new File(context.getCacheDir(), "shopping-cart.txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(gson().toJson(cartItemList).getBytes());
                fos.close();

                TestFairy.attachFile(file);
                TestFairy.addEvent("Number of items in cart: " + cartItemList.size());
            } catch (IOException e) {
                TestFairy.logThrowable(e);
            }
        }
    }
}
