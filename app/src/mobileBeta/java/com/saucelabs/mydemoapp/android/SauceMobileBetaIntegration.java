package com.saucelabs.mydemoapp.android;

import android.util.Log;

import com.testfairy.TestFairy;

import java.util.HashMap;
import java.util.Map;

import backtraceio.library.BacktraceClient;

/**
 * Debug/beta-only Backtrace + Sauce Mobile Beta integration.
 *
 * Backtrace is the sole crash owner. Sauce Mobile Beta is provided by the
 * crashless renamed artifact and supplies beta session/feedback/log behavior.
 *
 * The SDK owns the coexistence contract: it generates a new sauce.correlation_id
 * at every session start, attaches it to that session and hands its managed
 * attributes (sauce.correlation_id, sauce.mobile_beta.session_started,
 * sauce.mobile_beta.session_url, sauce.sdk.coexistence_mode, sauce.release,
 * sauce.dist) into the Backtrace client linked below with TestFairy.setBacktraceClient.
 */
public final class SauceMobileBetaIntegration {

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
		if (BuildConfig.SAUCE_MOBILE_BETA_ENDPOINT.trim().isEmpty()) {
			// The SDK's built-in default server is no longer in service, so a start without an endpoint records nothing.
			Log.w(Config.TAG, "Sauce Mobile Beta is not configured; set SAUCE_MOBILE_BETA_ENDPOINT (local.properties: sauceMobileBetaEndpoint) to the Server Endpoint from Organization Settings > SDK Settings.");
			return;
		}

		final Thread.UncaughtExceptionHandler backtraceCrashHandler =
			Thread.getDefaultUncaughtExceptionHandler();

		if (backtraceClient == null) {
			// Each SDK is configured independently: Mobile Beta still records sessions, there is just no Backtrace report to correlate them with.
			Log.w(Config.TAG, "Backtrace is not configured; Sauce Mobile Beta starts without crash-report correlation.");
		} else {
			// Linked before beginWithoutCrashHandler so the first session's attributes reach Backtrace. At every session start
			// (and on the start response) the SDK writes its managed attributes through BacktraceBase.addAttribute(Map)
			// (backtrace-android 3.7.14+), which updates both the JVM attribute map and the Crashpad annotations, so JVM and
			// native crash reports carry the current session's values. No glue code: the SDK calls Backtrace's public API itself.
			TestFairy.setBacktraceClient(backtraceClient);
		}

		TestFairy.disableAutoUpdate();
		TestFairy.setUserId(MyApplication.getRandomUserId());
		for (Map.Entry<String, String> attribute : sharedAttributes.entrySet()) {
			if (!TestFairy.setAttribute(attribute.getKey(), attribute.getValue())) {
				Log.w(Config.TAG, "Sauce Mobile Beta rejected attribute: " + attribute.getKey());
			}
		}

		// Required before begin: the Server Endpoint from Organization Settings > SDK Settings.
		TestFairy.setServerEndpoint(BuildConfig.SAUCE_MOBILE_BETA_ENDPOINT);

		Map<String, String> options = new HashMap<>();
		options.put("enableCrashReporter", "false");
		TestFairy.beginWithoutCrashHandler(
			application.getApplicationContext(),
			BuildConfig.SAUCE_MOBILE_BETA_TOKEN,
			options
		);
		TestFairy.log(
			"SauceMobileBeta",
			"Started with Backtrace crash ownership. sauce.correlation_id="
				+ TestFairy.getSessionCorrelationId()
		);
		// Also in logcat, so the first session's id is visible without the debug screen.
		Log.i(Config.TAG, "Sauce Mobile Beta session started, sauce.correlation_id: " + TestFairy.getSessionCorrelationId());

		if (Thread.getDefaultUncaughtExceptionHandler() != backtraceCrashHandler) {
			throw new IllegalStateException(
				"Sauce Mobile Beta replaced the default uncaught-exception handler"
			);
		}
	}

	/**
	 * Stops the current session and starts a new one. The SDK generates a new
	 * sauce.correlation_id for the new session and pushes it to Backtrace through the sink.
	 */
	public static void restartSession() {
		TestFairy.stop();
		TestFairy.resume();
	}

	/**
	 * @return the SDK-generated correlation id of the current session, or null before the first session start.
	 */
	public static String sessionCorrelationId() {
		return TestFairy.getSessionCorrelationId();
	}
}
