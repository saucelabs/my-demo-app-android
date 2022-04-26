package com.saucelabs.mydemoapp.android;

import java.util.*;

import android.content.Context;

import com.testfairy.TestFairy;
import com.testfairy.SessionStateListener;
import com.saucelabs.mydemoapp.android.utils.Network;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.saucelabs.mydemoapp.android.utils.TestFairyAssetReader;

public class MyApplication extends android.app.Application {

	public static MyApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SingletonClass.getInstance();

		initializeTestFairy();

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

	private String getRandomUserId() {
		Random random = new Random();
		String[] names = new String[]{
			"oliver", "william", "james", "benjamin", "henry", "diego", "alexander", "guy",
			"michael", "daniel", "jacob", "roy", "jonathan", "olivia", "charlotte", "sophia",
			"sarah", "isabella", "evelyn", "harper", "camila", "gianna", "abigail", "ella"
		};

		return names[Math.abs(random.nextInt()) % names.length] + "@example.com";
	}

	private void fetchToS() {
		Network.fetch("https://saucelabs.com/terms-of-service");
	}
}
