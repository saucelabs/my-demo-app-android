package com.saucelabs.mydemoapp.android.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.saucelabs.mydemoapp.android.MyApplication;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ActivityDebugCrashBinding;
import com.saucelabs.mydemoapp.android.databinding.ActivityDebugFeedbackBinding;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import backtraceio.library.BacktraceClient;

public class DebugCrashActivity extends BaseActivity {
	private ActivityDebugCrashBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_debug_crash);
		initialize();
	}

	private void initialize() {

		binding.causeUncaughtExceptionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MyApplication.backtraceClient != null) {
					throw new RuntimeException("You clicked on the wrong button!");
				}
			}
		});

		binding.causeNativeCrashButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MyApplication.backtraceClient == null) {
					Toast.makeText(DebugCrashActivity.this, "Backtrace client not initialized, cannot crash app", Toast.LENGTH_LONG).show();
					return;
				}

				MyApplication.backtraceClient.nativeCrash();
			}
		});
	}
}
