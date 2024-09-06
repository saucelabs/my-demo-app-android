package com.saucelabs.mydemoapp.android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ActivityDebugFeedbackBinding;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DebugFeedbackActivity extends BaseActivity {
	private ActivityDebugFeedbackBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_debug_feedback);
		initialize();
	}

	private void initialize(){
		binding.yesScreenshotReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// FIXME: Remove Trigger
			}
		});

		binding.noScreenshotReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// FIXME: Remove Trigger
			}
		});

		binding.stopReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// FIXME: Remove Trigger
			}
		});

		binding.customFieldsReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				prepareFeedbackForm(false);
				// FIXME: Remove Trigger ?
			}
		});

		binding.customFieldsReportButtonWithSession.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				prepareFeedbackForm(true);
				// FIXME: Remove Trigger ?
			}
		});

		binding.customActivityReportButtonWithSession.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DebugFeedbackActivity.this, CustomFeedbackFormActivity.class));
			}
		});
	}

	@Override
	public void onBackPressed() {
		onBackPressedDefault();
	}

	private void prepareFeedbackForm(boolean enableRecording) {
		// FIXME: Remove Trigger ?
	}
}
