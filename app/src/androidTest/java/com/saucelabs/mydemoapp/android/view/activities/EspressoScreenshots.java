package com.saucelabs.mydemoapp.android.view.activities;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor;
import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.Screenshot;

import com.saucelabs.mydemoapp.android.HappyFlow;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class EspressoScreenshots {

	@Test
	@HappyFlow
	public void captureScreenshotInTestCode() throws InterruptedException {
		SauceLabsCustomScreenshot.capture("first");

		Thread.sleep(1000);

		SauceLabsCustomScreenshot.capture("second");

		Thread.sleep(1000);

		SauceLabsCustomScreenshot.capture("last");
	}

	public static class SauceLabsCustomScreenshot {

		private static final AtomicInteger screenshotId = new AtomicInteger();

		public static void capture(String screenshotName) {
			try {
				screenshotName = screenshotId.getAndIncrement() + "-" + screenshotName;

				ScreenCapture capture = Screenshot.capture();
				capture.setFormat(Bitmap.CompressFormat.PNG);
				capture.setName(screenshotName);

				SauceLabsScreenCaptureProcessor processor = new SauceLabsScreenCaptureProcessor();
				processor.process(capture);

				Path savedScreenshotPath = new File(processor.getPathForFile(screenshotName) + ".png").toPath();

				Log.i("SauceLabs", String.format("Screenshot saved to %s", savedScreenshotPath.toString()));
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
}
