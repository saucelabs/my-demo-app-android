package com.saucelabs.mydemoapp.android.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ActivityCustomFeedbackFormBinding;
import com.saucelabs.mydemoapp.android.utils.TestFairyAssetReader;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;
import com.testfairy.TestFairy;


public class CustomFeedbackFormActivity extends BaseActivity {


	private ActivityCustomFeedbackFormBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_feedback_form);

		initialize();
		TestFairy.stop();
	}

	private void initialize() {
		binding.submitForm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				submit();
			}
		});
	}

	private void submit() {
		String appToken = new TestFairyAssetReader().read(this).getAppToken();

		// You can set any custom attribute you like
		TestFairy.setAttribute("name", binding.nameInput.getText().toString());

		TestFairy.setUserId(binding.emailInput.getText().toString());
		TestFairy.sendUserFeedback(this, appToken, binding.messageInput.getText().toString());

		Toast.makeText(getApplicationContext(), "Feedback submitted", Toast.LENGTH_LONG).show();
		finish();
	}
}