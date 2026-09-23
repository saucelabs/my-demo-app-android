package com.saucelabs.mydemoapp.android;

import android.util.Log;

import com.testfairy.SessionStateListener;
import com.testfairy.TestFairy;

import java.util.HashMap;
import java.util.Map;

import backtraceio.library.BacktraceClient;
import backtraceio.library.BacktraceDatabase;

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
		if (BuildConfig.SAUCE_MOBILE_BETA_TOKEN.trim().isEmpty()) {
			Log.w(Config.TAG, "Sauce Mobile Beta is not configured; set SAUCE_MOBILE_BETA_TOKEN (local.properties: sauceMobileBetaToken).");
			return;
		}

		if (backtraceClient == null) {
			// Each SDK is configured independently: Mobile Beta still records sessions, there is just no Backtrace report to correlate them with.
			Log.w(Config.TAG, "Backtrace is not configured; Sauce Mobile Beta starts without crash-report correlation.");
		}

		final Thread.UncaughtExceptionHandler backtraceCrashHandler =
			Thread.getDefaultUncaughtExceptionHandler();

		if (backtraceClient != null) {
			TestFairy.addSessionStateListener(new SessionStateListener() {
				@Override
				public void onSessionStarted(String sessionUrl) {
					// Overwritten on every session start: stop()/resume creates a new session with a new URL, all sharing this launch's sauce.correlation_id.
					mirrorToBacktrace(backtraceClient, "sauce.mobile_beta.session_started", "true");
					mirrorToBacktrace(backtraceClient, "sauce.mobile_beta.session_url", sessionUrl == null ? "" : sessionUrl);
				}

				@Override
				public void onSessionFailed() {
					mirrorToBacktrace(backtraceClient, "sauce.mobile_beta.session_started", "false");
				}
			});
		}

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
				"Sauce Mobile Beta replaced the default uncaught-exception handler"
			);
		}
	}

	/**
	 * JVM reports read the client's live attribute map, but native (Crashpad) annotations are snapshotted once at enableNativeIntegration();
	 * values added later must also be pushed through BacktraceDatabase.addAttribute (String values only) to reach native crash reports.
	 */
	private static void mirrorToBacktrace(BacktraceClient client, String key, String value) {
		client.getAttributes().put(key, value);
		if (client.database instanceof BacktraceDatabase) {
			try {
				((BacktraceDatabase) client.database).addAttribute(key, value);
			} catch (RuntimeException | LinkageError error) {
				// Defensive: addAttribute is a JNI call; when Crashpad is not initialized the native
				// side only logs a warning, so this path is not expected in practice.
				Log.w(Config.TAG, "Could not mirror " + key + " into native Backtrace reports: " + error);
			}
		}
	}
}
