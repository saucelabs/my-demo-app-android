package com.saucelabs.mydemoapp.android.screenshot;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.test.runner.screenshot.BasicScreenCaptureProcessor;
import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.Screenshot;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class SauceLabsCustomScreenshot {

	private static final AtomicInteger screenshotId = new AtomicInteger(1);

	public static void capture(String screenshotName) {
		try {
			screenshotName = String.format("%04d-%s", screenshotId.getAndIncrement(), screenshotName);

			ScreenCapture capture = Screenshot.capture();
			capture.setFormat(Bitmap.CompressFormat.PNG);
			capture.setName(screenshotName);

			SauceLabsScreenCaptureProcessor processor = new SauceLabsScreenCaptureProcessor();
			processor.process(capture);

			String savedScreenshotPath = new File(processor.getPathForFile(screenshotName) + ".png").getPath();

			Log.i("SauceLabs", String.format("Screenshot saved to %s", savedScreenshotPath));
		} catch (Exception e) {
			Log.e("SauceLabs", "Failed to capture screenshot", e);
		}
	}

	private static class SauceLabsScreenCaptureProcessor extends BasicScreenCaptureProcessor {

		private static final String SAUCE_LABS_CUSTOM_SCREENSHOTS = "sauce_labs_custom_screenshots";

		public SauceLabsScreenCaptureProcessor() {
			super();

			File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			File screenshotsDir = new File(downloadsDir, SAUCE_LABS_CUSTOM_SCREENSHOTS);
			this.mDefaultScreenshotPath = screenshotsDir;
		}

		public String getPathForFile(String name) {
			File file = new File(mDefaultScreenshotPath, getFilename(name));
			return file.getAbsolutePath();
		}
	}
}
