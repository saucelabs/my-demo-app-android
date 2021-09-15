package com.saucelabs.mydemoapp.android;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.saucelabs.mydemoapp.android.utils.TestFairyAssetReader;
import com.testfairy.TestFairy;

public class MyApplication extends android.app.Application {

    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SingletonClass.getInstance();

        initializeTestFairy();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initializeTestFairy() {
        TestFairyAssetReader.Data testFairyData = new TestFairyAssetReader().read(this);
        TestFairy.setServerEndpoint(testFairyData.getServerEndpoint());
        TestFairy.begin(this, testFairyData.getAppToken());
    }
}
