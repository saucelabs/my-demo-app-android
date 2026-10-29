package com.saucelabs.mydemoapp.android;

import java.util.Map;

import backtraceio.library.BacktraceClient;

/**
 * Production/default adapter. Sauce Mobile Beta is intentionally not packaged
 * unless a debug build explicitly opts in with an AAR or Maven version.
 */
public final class SauceMobileBetaIntegration {

	private SauceMobileBetaIntegration() {
	}

	static void initialize(
		MyApplication application,
		BacktraceClient backtraceClient,
		Map<String, String> sharedAttributes
	) {
		// No-op. This source set has no Sauce Mobile Beta dependency.
	}

	public static void restartSession() {
		// No-op. This source set has no Sauce Mobile Beta dependency.
	}

	public static String sessionCorrelationId() {
		// No-op. This source set has no Sauce Mobile Beta dependency.
		return null;
	}
}
