package com.saucelabs.mydemoapp.android.utils;

import android.util.Log;

import com.testfairy.TestFairy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class Network {

	private static Headers toHeaders(List<String> headers) {
		Headers.Builder builder = new Headers.Builder();

		for (String line: headers) {
			builder.add(line);
		}

		return builder.build();
	}

	private static Request buildRequest(String method, String url, List<String> headers, String requestBody, String contentType) {
		Request.Builder builder = new Request.Builder()
			.headers(toHeaders(headers))
			.url(url);

		if (requestBody != null) {
			RequestBody body = RequestBody.create(requestBody, MediaType.parse(contentType));
			builder.method(method, body);
		} else {
			builder.method(method, null);
		}

		return builder.build();
	}

	private static Response sendRequest(OkHttpClient client, Request request) throws IOException {
		return client.newCall(request).execute();
	}

	private static byte[] getRequestBodyBytes(Request request) throws IOException {
		Buffer requestBuffer = new Buffer();
		RequestBody requestBody = request.body();
		if (requestBody != null) {
			requestBody.writeTo(requestBuffer);
		}

		return requestBuffer.readByteArray();
	}

	private static byte[] getResponseBodyBytes(Response response) throws IOException {
		ResponseBody responseBody = response.body();
		return responseBody != null ? responseBody.bytes() : new byte[0];
	}

	private static String getRequestHeaders(Request request) {
		StringBuilder requestHeaders = new StringBuilder();
		Map<String, List<String>> requestHeadersMap = request.headers().toMultimap();
		for (Map.Entry<String, List<String>> entry : requestHeadersMap.entrySet()) {
			for (String val : entry.getValue()) {
				requestHeaders.append(entry.getKey()).append(": ").append(val).append("\n");
			}
		}

		return requestHeaders.toString().trim();
	}

	private static String getResponseHeaders(Response response) {
		StringBuilder responseHeaders = new StringBuilder();
		Map<String, List<String>> responseHeadersMap = response.headers().toMultimap();
		for (Map.Entry<String, List<String>> entry : responseHeadersMap.entrySet()) {
			for (String val : entry.getValue()) {
				responseHeaders.append(entry.getKey()).append(": ").append(val).append("\n");
			}
		}

		return responseHeaders.toString().trim();
	}

	public static void fetch(String method, String url, String body, String contentType) {
		new Thread(() -> {
			try {
				List<String> headers = new ArrayList<>();
				headers.add("User-Agent: my-demo-app-android");
				headers.add("X-Trace-ID: " + UUID.randomUUID().toString());

				OkHttpClient client = new OkHttpClient();
				Request request = buildRequest(method, url, headers, body, contentType);
				Log.v("Network", "Sending " + method + " request to " + url);

				long startTime = System.currentTimeMillis();
				Response response = sendRequest(client, request);
				long endTime = System.currentTimeMillis();

				byte[] requestBodyBytes = getRequestBodyBytes(request);
				byte[] responseBodyBytes = getResponseBodyBytes(response);

				String requestHeaders = getRequestHeaders(request);
				String responseHeaders = getResponseHeaders(response);

				TestFairy.addNetworkEvent(
					new URI(url),
					request.method(),
					response.code(),
					startTime,
					endTime,
					requestBodyBytes.length,
					responseBodyBytes.length,
					null,
					requestHeaders,
					requestBodyBytes,
					responseHeaders,
					responseBodyBytes
				);
			} catch (IOException | URISyntaxException ignored) {
			}
		}).start();
	}

	public static void fetch(String url) {
		fetch("GET", url, null, null);
	}

	public static void post(String url, String body, String contentType) {
		fetch("POST", url, body, contentType);
	}
}
