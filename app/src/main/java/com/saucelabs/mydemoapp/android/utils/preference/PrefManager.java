package com.saucelabs.mydemoapp.android.utils.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.saucelabs.mydemoapp.android.utils.Methods;
import com.saucelabs.mydemoapp.android.utils.base.BaseModel;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class PrefManager extends Methods {
    private SharedPreferences getPrefs(Activity mAct) {
        return mAct.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public int getMyIntPref(Activity mAct, String prefName, int defVal) {
        return getPrefs(mAct).getInt(prefName, defVal);
    }

    public void setMyIntPref(Activity mAct, String prefName, int value) {
        getPrefs(mAct).edit().putInt(prefName, value).apply();
    }

    public String getMyStringPref(Activity mAct, String prefName, String defVal) {
        return getPrefs(mAct).getString(prefName, defVal);
    }

    public void setMyStringPref(Activity mAct, String prefName, String value) {
        getPrefs(mAct).edit().putString(prefName, value).apply();
    }

    public BaseModel getMyObjectPref(Activity mAct, String prefName, String defVal, Class aClass) {
        Gson gson = new Gson();
        String json = getPrefs(mAct).getString(prefName, defVal);
        if (json != null) {
            BaseModel baseModel = gson.fromJson(json, (Type) aClass);
            return baseModel;
        }

        return null;
    }

    public void setMyObjectPref(Activity mAct, String prefName, BaseModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);

        getPrefs(mAct).edit().putString(prefName, json).apply();
    }

    public boolean getMyBooleanPref(Activity mAct, String prefName, boolean defVal) {
        return getPrefs(mAct).getBoolean(prefName, defVal);
    }

    public void setMyBooleanPref(Activity mAct, String prefName, boolean value) {
        getPrefs(mAct).edit().putBoolean(prefName, value).apply();
    }

    public long getMyLongPref(Activity mAct, String prefName, long defVal) {
        return getPrefs(mAct).getLong(prefName, defVal);
    }

    public void setMyLongPref(Activity mAct, String prefName, long value) {
        getPrefs(mAct).edit().putLong(prefName, value).apply();
    }

    public void setRatePref(Activity mAct, String prefName, boolean val) {
        getPrefs(mAct).edit().putBoolean(prefName, val).apply();
    }

    public boolean getRatePref(Activity mAct, String prefName, boolean val) {
        return getPrefs(mAct).getBoolean(prefName, val);
    }

    public boolean getRateTimePref(Activity mAct, String prefName, boolean value) {
        return getPrefs(mAct).getBoolean(prefName, value);
    }

    public void setRateTimePref(Activity mAct, String prefName, boolean value) {
        getPrefs(mAct).edit().putBoolean(prefName, value).apply();
    }

    public void removePref(Activity mAct, String prefName) {
        getPrefs(mAct).edit().remove(prefName).apply();
    }
}