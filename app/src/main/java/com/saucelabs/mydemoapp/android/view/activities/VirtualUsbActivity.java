package com.saucelabs.mydemoapp.android.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.saucelabs.mydemoapp.android.Config;
import com.saucelabs.mydemoapp.android.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class VirtualUsbActivity extends Activity {

	private static final int SERVER_SOCKET_PORT = 50000;

	volatile boolean serverSocketActive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_virtual_usb);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Thread t = new Thread(this::startServer);
		t.setName("VirtualUSB_ServerSocket_Thread");
		t.start();
	}

	@Override
	protected void onPause() {
		stopServer();
		super.onPause();
	}

	private void stopServer() {
		serverSocketActive = false;
	}

	private void onNewClientMessage(String message) {
		TextView textView = this.findViewById(R.id.virtual_usb_message);
		textView.setText(message);
	}

	private void acceptConnection(ServerSocket serverSocket) throws IOException {

		// we might not be getting a socket because accept() is set up to timeout after 1 second
		Socket clientSocket = serverSocket.accept();

		Thread t = new Thread(() -> {
			try {
				// read only the first line from socket
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String line = in.readLine();
				onNewClientMessage(line);

				// Close the client socket and input stream
				in.close();
				clientSocket.close();
			} catch (IOException e) {
				Log.e(Config.TAG, "Exception while reading from socket", e);
			}
		});

		t.start();
	}

	private void startServer() {

		serverSocketActive = true;

		try (ServerSocket serverSocket = new ServerSocket(SERVER_SOCKET_PORT)) {
			serverSocket.setSoTimeout(1000);

			while (serverSocketActive) {
				try {
					acceptConnection(serverSocket);
				} catch (SocketTimeoutException ste) {
					// ignored
				}
			}
		} catch (IOException e) {
			Log.e(Config.TAG, "Exception during virtual-usb demo", e);
		}
	}

}