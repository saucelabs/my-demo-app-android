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
import backtraceio.library.models.database.BacktraceDatabaseSettings;

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

		// replace with your endpoint url and token
		BacktraceCredentials credentials = new BacktraceCredentials(
				"https://cd03.sp.backtrace.io:6098",
				"7197fa84a54cec280e7fa377f5d7beb572aab141d0c310a2bf8f11873822d2ef"
		);

		String dbPath = getApplicationContext().getFilesDir().getAbsolutePath(); // any path, eg. absolute path to the internal storage
		BacktraceDatabaseSettings settings = new BacktraceDatabaseSettings(dbPath);
		BacktraceClient backtraceClient = new BacktraceClient(getApplicationContext(), credentials, settings);
		backtraceClient.enableBreadcrumbs(getApplicationContext());
		backtraceClient.enableAnr();
		backtraceClient.enableNativeIntegration(true);
		BacktraceExceptionHandler.enable(backtraceClient);

		MyApplication.backtraceClient = backtraceClient;
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
