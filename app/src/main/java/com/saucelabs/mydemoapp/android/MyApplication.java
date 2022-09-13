package com.saucelabs.mydemoapp.android;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.testfairy.TestFairy;
import com.testfairy.SessionStateListener;
import com.saucelabs.mydemoapp.android.utils.Network;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.saucelabs.mydemoapp.android.utils.TestFairyAssetReader;

import backtraceio.library.BacktraceClient;
import backtraceio.library.BacktraceCredentials;
import backtraceio.library.models.BacktraceExceptionHandler;

public class MyApplication extends android.app.Application {

	public static MyApplication instance;
	public static BacktraceClient backtraceClient;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SingletonClass.getInstance();

		initializeTestFairy();
		initializeBacktrace();

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

	private void initializeBacktrace() {
		/*
		BacktraceCredentials credentials = new BacktraceCredentials(
			"<url>",
			"<token>"
		);

		BacktraceClient backtraceClient = new BacktraceClient(getApplicationContext(), credentials);
		BacktraceExceptionHandler.enable(backtraceClient);

		MyApplication.backtraceClient = backtraceClient;
		*/
	}

	private void initializeTestFairy() {
		TestFairyAssetReader.Data testFairyData = new TestFairyAssetReader().read(this);

		TestFairy.setServerEndpoint(testFairyData.getServerEndpoint());
		TestFairy.addSessionStateListener(new SessionStateListener() {
			@Override
			public void onSessionStarted(String s) {
				fetchToS();
				Network.fetch("https://my-demo-app.net/api/init");
			}
		});

		TestFairy.setUserId(getRandomUserId());
		TestFairy.begin(this, testFairyData.getAppToken());
	}

	private static String getRandomUserId() {
		Random random = new Random();
		String[] names = new String[]{
			"oliver", "william", "james", "benjamin", "henry", "diego", "alexander", "guy",
			"michael", "daniel", "jacob", "roy", "jonathan", "olivia", "charlotte", "sophia",
			"sarah", "isabella", "evelyn", "harper", "camila", "gianna", "abigail", "ella"
		};

		return names[Math.abs(random.nextInt()) % names.length] + "@example.com";
	}

	private void fetchToS() {
		Network.fetch("https://my-demo-app.net/terms-of-service");
	}
}
