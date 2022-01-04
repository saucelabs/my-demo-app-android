package com.saucelabs.mydemoapp.android;

import static com.saucelabs.mydemoapp.android.utils.Network.fetch;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.saucelabs.mydemoapp.android.utils.TestFairyAssetReader;
import com.testfairy.SessionStateListener;
import com.testfairy.TestFairy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class MyApplication extends android.app.Application {

	public static MyApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SingletonClass.getInstance();

		initializeTestFairy();
	}

	@Override
	public Context getApplicationContext() {
		return super.getApplicationContext();
	}

	public static MyApplication getInstance() {
		return instance;
	}

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	private void initializeTestFairy() {
		TestFairyAssetReader.Data testFairyData = new TestFairyAssetReader().read(this);

		TestFairy.setServerEndpoint(testFairyData.getServerEndpoint());
		TestFairy.addSessionStateListener(new SessionStateListener() {
			@Override
			public void onSessionStarted(String s) {
				fetchToS();
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
		fetch("https://saucelabs.com/terms-of-service");
	}
}
