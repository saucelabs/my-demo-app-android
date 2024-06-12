package com.saucelabs.mydemoapp.android;

import android.util.Log;

import com.saucelabs.mydemoapp.android.utils.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class DeviceVitalsDemo {

	private final TimerTask fetchJavascript = new TimerTask() {

		private static final String url = "https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js";

		@Override
		public void run() {
			Network.fetch(url);
		}
	};

	private final TimerTask fetchImage = new TimerTask() {

		private static final String url = "https://my-demo-app.net/logo.png";

		@Override
		public void run() {
			Network.fetch(url);
		}
	};

	private final TimerTask postAnalytics = new TimerTask() {

		private static final String url = "https://my-demo-app.net/api/analytics/collect/";

		@Override
		public void run() {
			String randomness = "";
			while (randomness.length() < 4096) {
				String chunk = UUID.randomUUID().toString().replace("-", "");
				randomness = randomness.concat(chunk);
			}

			Log.v(Config.TAG, randomness);
			Network.post(url, randomness, "application/text");
		}
	};

	private final TimerTask memorySawtooth = new TimerTask() {

		private int step = 0;
		private final List<Byte[]> items = new ArrayList<>();

		private static final int ONE_MEGABYTE = 1024 * 1024;

		@Override
		public void run() {
			if (step < 32) {
				// [0 .. 31] we add one megabyte
				step++;
				items.add(new Byte[ONE_MEGABYTE]);
			} else if (step < 64) {
				// [ 32 .. 63] we remove one megabyte
				step++;
				items.remove(0);
			} else if (step == 64) {
				step = 0;
			}

			System.gc();
		}
	};

	public void kickstart() {
		Timer timer = new Timer();
		timer.schedule(fetchJavascript, 2000);
//		timer.schedule(fetchImage, 4000);
		timer.schedule(postAnalytics, 5000);
		timer.schedule(memorySawtooth, 1000, 500);
	}
}
