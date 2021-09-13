package com.saucelabs.mydemoapp.android.utils.base;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.saucelabs.mydemoapp.android.MyApplication;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;


public class BaseFragment extends Fragment {
    public Activity mAct;
    public MyApplication app;
    public SingletonClass ST;
    public AppDatabase mDb;

    public String mParam1;
    public String mParam2;
    public int mParam3;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (MyApplication) getActivity().getApplication();
        ST = SingletonClass.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean OnBackPress() {
        return true;
    }
}
