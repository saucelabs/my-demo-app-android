package com.saucelabs.mydemoapp.android;

import android.util.Log;

import com.testfairy.SessionStateListener;
import com.testfairy.TestFairy;

import java.util.HashMap;
import java.util.Map;

import backtraceio.library.BacktraceClient;

/**
 * Debug/beta-only Backtrace + Sauce Mobile Beta integration.
 *
 * Backtrace is the sole crash owner. Sauce Mobile Beta is provided by the
 * crashless renamed artifact and supplies beta session/feedback/log behavior.
 */
final class SauceMobileBetaIntegration {

	private SauceMobileBetaIntegration() {
	}

	static void initialize(
		MyApplication application,
		final BacktraceClient backtraceClient,
		Map<String, String> sharedAttributes
	) {
		if (backtraceClient == null) {
			Log.w(Config.TAG, "Sauce Mobile Beta skipped because Backtrace is not configured.");
			return;
		}

		if (BuildConfig.SAUCE_MOBILE_BETA_TOKEN.trim().isEmpty()) {
			Log.w(Config.TAG, "Sauce Mobile Beta is not configured; set SAUCE_MOBILE_BETA_TOKEN.");
			return;
		}

		final Thread.UncaughtExceptionHandler backtraceCrashHandler =
			Thread.getDefaultUncaughtExceptionHandler();

		TestFairy.addSessionStateListener(new SessionStateListener() {
			@Override
			public void onSessionStarted(String sessionUrl) {
				backtraceClient.getAttributes().put("sauce.mobile_beta.session_url", sessionUrl);
				backtraceClient.getAttributes().put("sauce.mobile_beta.session_started", true);
			}

			@Override
			public void onSessionFailed() {
				backtraceClient.getAttributes().put("sauce.mobile_beta.session_started", false);
			}
		});

		TestFairy.disableAutoUpdate();
		TestFairy.setUserId(MyApplication.getRandomUserId());
		for (Map.Entry<String, String> attribute : sharedAttributes.entrySet()) {
			if (!TestFairy.setAttribute(attribute.getKey(), attribute.getValue())) {
				Log.w(Config.TAG, "Sauce Mobile Beta rejected attribute: " + attribute.getKey());
			}
		}

		Map<String, String> options = new HashMap<>();
		options.put("enableCrashReporter", "false");
		TestFairy.beginWithoutCrashHandler(
			application.getApplicationContext(),
			BuildConfig.SAUCE_MOBILE_BETA_TOKEN,
			options
		);
		TestFairy.log(
			"SauceMobileBeta",
			"Started with Backtrace crash ownership. correlation_id="
				+ sharedAttributes.get("sauce.correlation_id")
		);

		if (Thread.getDefaultUncaughtExceptionHandler() != backtraceCrashHandler) {
			throw new IllegalStateException(
				"Sauce Mobile Beta replaced Backtrace's uncaught-exception handler"
			);
		}
	}
}
