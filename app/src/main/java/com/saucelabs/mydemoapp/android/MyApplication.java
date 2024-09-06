package com.saucelabs.mydemoapp.android;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.saucelabs.mydemoapp.android.utils.Network;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;

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
