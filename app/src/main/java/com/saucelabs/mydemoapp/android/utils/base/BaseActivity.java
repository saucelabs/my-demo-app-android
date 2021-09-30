package com.saucelabs.mydemoapp.android.utils.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.saucelabs.mydemoapp.android.MyApplication;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {
    public Activity mAct = BaseActivity.this;
    public MyApplication app;
    public SingletonClass ST;
    public String DEFAULT_BROADCAST = "NOTIFICATION_BROADCAST";

    public Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBaseActivity();
    }

    private void initBaseActivity() {
        app = (MyApplication) getApplication();
        ST = SingletonClass.getInstance();
        gson = new Gson();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent != null && intent.getAction().equals(DEFAULT_BROADCAST)) {

                }
            } catch (Exception ignored) {
//                Log.d("roadVast", ignored.getLocalizedMessage());
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        if (receiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(DEFAULT_BROADCAST));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
