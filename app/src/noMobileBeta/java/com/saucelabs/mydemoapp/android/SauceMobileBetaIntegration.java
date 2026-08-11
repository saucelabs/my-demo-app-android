package com.saucelabs.mydemoapp.android;

import java.util.Map;

import backtraceio.library.BacktraceClient;

/**
 * Production/default adapter. Sauce Mobile Beta is intentionally not packaged
 * unless a debug build explicitly opts in with an AAR or Maven version.
 */
final class SauceMobileBetaIntegration {

	private SauceMobileBetaIntegration() {
	}

	static void initialize(
		MyApplication application,
		BacktraceClient backtraceClient,
		Map<String, String> sharedAttributes
	) {
		// No-op. This source set has no Sauce Mobile Beta dependency.
	}
}
