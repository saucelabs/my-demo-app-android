package com.saucelabs.mydemoapp.android;

import android.content.Context;
import android.util.Log;

import com.saucelabs.mydemoapp.android.utils.Network;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;

import backtraceio.library.BacktraceClient;
import backtraceio.library.BacktraceCredentials;
import backtraceio.library.models.BacktraceExceptionHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MyApplication extends android.app.Application {

	public static MyApplication instance;
	public static BacktraceClient backtraceClient;
	private final String correlationId = UUID.randomUUID().toString();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SingletonClass.getInstance();

		Map<String, String> sharedAttributes = createSharedAttributes();
		initializeBacktrace(sharedAttributes);
		SauceMobileBetaIntegration.initialize(this, backtraceClient, sharedAttributes);

		DeviceVitalsDemo demo = new DeviceVitalsDemo();
		demo.kickstart();
	}

	@Override
	public Context getApplicationContext() {
		return super.getApplicationContext();
	}

	public static MyApplication getInstance() {
		return instance;
	}

	private Map<String, String> createSharedAttributes() {
		Map<String, String> attributes = new LinkedHashMap<>();
		attributes.put("sauce.correlation_id", correlationId);
		attributes.put("sauce.sdk.coexistence_mode", "backtrace_crash_owner");
		attributes.put("sauce.environment", BuildConfig.SAUCE_ENVIRONMENT);
		attributes.put(
			"sauce.release",
			BuildConfig.APPLICATION_ID + "@" + BuildConfig.VERSION_NAME
		);
		attributes.put("sauce.dist", String.valueOf(BuildConfig.VERSION_CODE));
		if (!BuildConfig.SAUCE_DISTRIBUTION_ID.trim().isEmpty()) {
			attributes.put("mad.distribution_id", BuildConfig.SAUCE_DISTRIBUTION_ID);
		}
		return attributes;
	}

	private void initializeBacktrace(Map<String, String> sharedAttributes) {
		if (BuildConfig.BACKTRACE_SUBMISSION_URL.trim().isEmpty()) {
			Log.w(Config.TAG, "Backtrace is not configured; set BACKTRACE_SUBMISSION_URL.");
			return;
		}

		Map<String, Object> backtraceAttributes = new HashMap<>();
		backtraceAttributes.putAll(sharedAttributes);

		BacktraceCredentials credentials = new BacktraceCredentials(
			BuildConfig.BACKTRACE_SUBMISSION_URL
		);
		BacktraceClient client = new BacktraceClient(
			getApplicationContext(),
			credentials,
			backtraceAttributes
		);

		BacktraceExceptionHandler.enable(client);
		client.enableNativeIntegration();
		MyApplication.backtraceClient = client;
	}

	static String getRandomUserId() {
		Random random = new Random();
		String[] names = new String[]{
			"oliver", "william", "james", "benjamin", "henry", "diego", "alexander", "guy",
			"michael", "daniel", "jacob", "roy", "jonathan", "olivia", "charlotte", "sophia",
			"sarah", "isabella", "evelyn", "harper", "camila", "gianna", "abigail", "ella"
		};

		return names[random.nextInt(names.length)] + "@example.com";
	}

	private void fetchToS() {
		Network.fetch("https://my-demo-app.net/terms-of-service");
	}
}
