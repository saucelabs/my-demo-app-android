package com.saucelabs.mydemoapp.android.utils;

import com.testfairy.TestFairy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class Network {

	public static void fetch(String url) {
		new Thread(() -> {
			long startTime = System.currentTimeMillis();

			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(url)
					.build();

			try {
				Buffer requestBuffer = new Buffer();
				RequestBody requestBody = request.body();
				if (requestBody != null) {
					requestBody.writeTo(requestBuffer);
				}
				byte[] requestBodyBytes = requestBuffer.readByteArray();

				Response response = client.newCall(request).execute();
				ResponseBody responseBody = response.body();
				byte[] responseBodyBytes = responseBody != null ? responseBody.bytes() : new byte[0];

				long endTime = System.currentTimeMillis();

				StringBuilder requestHeaders = new StringBuilder();
				Map<String, List<String>> requestHeadersMap = request.headers().toMultimap();
				for (Map.Entry<String, List<String>> entry : requestHeadersMap.entrySet()) {
					for (String val : entry.getValue()) {
						requestHeaders.append(entry.getKey()).append(": ").append(val).append("\n");
					}
				}

				StringBuilder responseHeaders = new StringBuilder();
				Map<String, List<String>> responseHeadersMap = response.headers().toMultimap();
				for (Map.Entry<String, List<String>> entry : responseHeadersMap.entrySet()) {
					for (String val : entry.getValue()) {
						responseHeaders.append(entry.getKey()).append(": ").append(val).append("\n");
					}
				}

				TestFairy.addNetworkEvent(
						new URI(url),
						request.method(),
						200,
						startTime,
						endTime,
						requestBodyBytes.length,
						responseBodyBytes.length,
						null,
						requestHeaders.toString().trim(),
						requestBodyBytes,
						responseHeaders.toString().trim(),
						responseBodyBytes
				);
			} catch (IOException | URISyntaxException t) {
			}
		}).start();
	}
}
