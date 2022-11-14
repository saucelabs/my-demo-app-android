package com.saucelabs.mydemoapp.android.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ActivityDebugFeedbackBinding;
import com.saucelabs.mydemoapp.android.utils.TestFairyAssetReader;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;
import com.testfairy.FeedbackFormField;
import com.testfairy.FeedbackOptions;
import com.testfairy.SelectFeedbackFormField;
import com.testfairy.StringFeedbackFormField;
import com.testfairy.TestFairy;
import com.testfairy.TextAreaFeedbackFormField;

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
		final TestFairyAssetReader.Data testFairyData = new TestFairyAssetReader().read(this);

		binding.yesScreenshotReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TestFairy.setFeedbackOptions(new FeedbackOptions.Builder().build());
				TestFairy.showFeedbackForm(DebugFeedbackActivity.this, testFairyData.getAppToken(), true);
			}
		});

		binding.noScreenshotReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TestFairy.setFeedbackOptions(new FeedbackOptions.Builder().build());
				TestFairy.showFeedbackForm(DebugFeedbackActivity.this, testFairyData.getAppToken(), false);
			}
		});

		binding.stopReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TestFairy.stop();
				TestFairy.setFeedbackOptions(new FeedbackOptions.Builder().build());
				TestFairy.showFeedbackForm(DebugFeedbackActivity.this, testFairyData.getAppToken(), false);
			}
		});

		binding.customFieldsReportButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				prepareFeedbackForm(false);
				TestFairy.showFeedbackForm();
			}
		});

		binding.customFieldsReportButtonWithSession.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				prepareFeedbackForm(true);
				TestFairy.showFeedbackForm();
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
		Map<String, String> projects = new TreeMap<>();
		projects.put("Bravo", "bravo");
		projects.put("Fury", "fury");
		projects.put("Roam", "roam");
		projects.put("Wow Phase 2", "wow-phase-2");

		Map<String, String> audio = new TreeMap<>();
		audio.put("Stutter", "stutter");
		audio.put("Skipping", "skipping");
		audio.put("No Sound", "no-sound");
		audio.put("Volume", "volume");
		audio.put("Ramping", "ramping");
		audio.put("Other", "other");

		Map<String, String> frequency = new TreeMap<>();
		frequency.put("Happens all the time", "all-the-time");
		frequency.put("Happens sometimes", "sometimes");
		frequency.put("Happened once", "once");

		Map<String, String> dropoff = new TreeMap<>();
		dropoff.put("On", "on");
		dropoff.put("Off", "off");

		Map<String, String> power = new TreeMap<>();
		power.put("Battery", "battery");
		power.put("Charging", "charging");

//		Map<String, String> desktop = new TreeMap<>();
//		desktop.put("Windows", "windows");
//		desktop.put("Mac", "mac");
//
//		Map<String, String> voice = new TreeMap<>();
//		voice.put("Alexa", "alexa");
//		voice.put("Google", "google");

		List<FeedbackFormField> fields = new ArrayList<>();
		fields.add(new SelectFeedbackFormField("project", "Project", projects, "" /*default value*/));
		fields.add(new SelectFeedbackFormField("audio", "Is audio related?", audio, "" /*default value*/));
		fields.add(new SelectFeedbackFormField("frequency", "Frequency", frequency, "" /*default value*/));
		fields.add(new SelectFeedbackFormField("dropoff", "Hardware Type", dropoff, "" /*default value*/));
		fields.add(new SelectFeedbackFormField("power", "Power Issue", power, "" /*default value*/));
		fields.add(new StringFeedbackFormField(":userId", "Email", "")); // :userId is built-in for emails
		fields.add(new TextAreaFeedbackFormField(":text", "Please describe your overall experience", "")); // :text is built-in for feedback messages

		TestFairy.setFeedbackOptions(new FeedbackOptions.Builder()
				.setFeedbackFormFields(fields)
				.setRecordVideoButtonVisible(enableRecording)
				.setTakeScreenshotButtonVisible(enableRecording)
				.build()
		);
	}

	private void prepareSurveyForm(boolean enableRecording) {
		// Keys are what the user sees, values are what the server will receive
		Map<String, String> ratings = new TreeMap<>();
		ratings.put("Satisfied", "3");
		ratings.put("Neutral", "2");
		ratings.put("Dissatisfied", "1");

		Map<String, String> years = new TreeMap<>();
		for (int i = 121; i >= 0; i--) {
			String year = "" + (1900 + i);
			years.put(year, year);
		}

		Map<String, String> yesNo = new TreeMap<>();
		yesNo.put("Yes", "yes");
		yesNo.put("No", "no");

		List<FeedbackFormField> fields = new ArrayList<>();
		fields.add(new StringFeedbackFormField("name", "Full name", ""));
		fields.add(new StringFeedbackFormField(":userId", "Email", "")); // :userId is built-in for emails
		fields.add(new SelectFeedbackFormField("birthday", "Birthday", years, "" /*default value*/)); // A custom select field
		fields.add(new StringFeedbackFormField("occupation", "Occupation", ""));
		fields.add(new SelectFeedbackFormField("purchased", "Was your purchase successful?", new TreeMap<>(yesNo), "Yes" /*default value*/));
		fields.add(new SelectFeedbackFormField("coupon", "Have you used a coupon code?", new TreeMap<>(yesNo), "" /*default value*/));
		fields.add(new TextAreaFeedbackFormField(":text", "Please describe your overall experience", "")); // :text is built-in for feedback messages
		fields.add(new SelectFeedbackFormField("rating", "Rating", ratings, "Satisfied" /*default value*/)); // A custom select field
		fields.add(new SelectFeedbackFormField("suggest", "Would you suggest us to a friend?", new TreeMap<>(yesNo), "" /*default value*/));
		fields.add(new TextAreaFeedbackFormField("requests", "What kind of products would you like to see in the future?", ""));
		fields.add(new SelectFeedbackFormField("subscribed", "Would you like to receive emails from us?", new TreeMap<>(yesNo), "No" /*default value*/));

		TestFairy.setFeedbackOptions(new FeedbackOptions.Builder()
				.setFeedbackFormFields(fields)
				.setRecordVideoButtonVisible(enableRecording)
				.setTakeScreenshotButtonVisible(enableRecording)
				.build()
		);
	}

	private void prepareContactForm() {
		// Keys are what the user sees, values are what the server will receive
		Map<String, String> topics = new HashMap<>();
		topics.put("Support", "customer_support");
		topics.put("Refund", "refund_request");
		topics.put("Delivery", "delivery_track");
		topics.put("Coupons", "coupons");
		topics.put("Suggestion", "customer_feedback");

		List<FeedbackFormField> fields = new ArrayList<>();
		fields.add(new StringFeedbackFormField(":userId", "Email", "")); // :userId is built-in for emails
		fields.add(new SelectFeedbackFormField("topic", "Topic", topics, "Support" /*default value*/)); // A custom select field
		fields.add(new TextAreaFeedbackFormField(":text", "Your message", "")); // :text is built-in for feedback messages

		TestFairy.setFeedbackOptions(new FeedbackOptions.Builder()
				.setFeedbackFormFields(fields)
				.build()
		);

		TestFairy.showFeedbackForm();
	}
}
