package com.saucelabs.mydemoapp.android.view.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.util.Size;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.common.util.concurrent.ListenableFuture;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.databinding.FragmentQrBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.QrCodeAnalyzer;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.saucelabs.mydemoapp.android.view.activities.MainActivity.FRAGMENT_PRODUCT_CATAlOG;

public class QRFragment extends BaseFragment implements View.OnClickListener {
	private FragmentQrBinding binding;
	private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
	private ExecutorService cameraExecutor;
	private static final int REQUEST_CODE_PERMISSIONS = 10;
	private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};

	public static QRFragment newInstance(String param1, String param2, int param3) {
		QRFragment fragment = new QRFragment();
		Bundle args = new Bundle();
		args.putString(Constants.ARG_PARAM1, param1);
		args.putString(Constants.ARG_PARAM2, param2);
		args.putInt(Constants.ARG_PARAM3, param3);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAct = getActivity();
		if (getArguments() != null) {
			mParam1 = getArguments().getString(Constants.ARG_PARAM1, "");
			mParam2 = getArguments().getString(Constants.ARG_PARAM2, "");
			mParam3 = getArguments().getInt(Constants.ARG_PARAM3, -1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qr, container, false);
		bindData();

		cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
		cameraExecutor = Executors.newSingleThreadExecutor();

		if (allPermissionsGranted()) {
			cameraProvider();
		}

		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void bindData() {
		mDb = AppDatabase.getInstance(getActivity());
		checkPermission();
	}

	private void checkPermission() {
		if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_CODE_PERMISSIONS) {

			if (allPermissionsGranted()) {
				cameraProvider();
			} else {
				ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_PRODUCT_CATAlOG, 1));
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!allPermissionsGranted()) {
			ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
		}
	}

	@Override
	public void onClick(View view) {

	}

	private void cameraProvider() {
		cameraProviderFuture.addListener(() -> {
			try {
				ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
				startCamera(cameraProvider);
			} catch (Exception e) {
				Log.e("Sauce", "Can't start camera", e);
			}
		}, ContextCompat.getMainExecutor(requireContext()));
	}

	private void startCamera(ProcessCameraProvider cameraProvider) {
		CameraSelector cameraSelector = new CameraSelector.Builder()
				.requireLensFacing(CameraSelector.LENS_FACING_BACK)
				.build();

		Display display = requireActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		Size cameraSize = new Size(size.x, size.y);
		Preview preview = new Preview.Builder()
				.setTargetResolution(cameraSize)
				.build();

		ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
				.setTargetResolution(cameraSize)
				.setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
				.build();

		imageAnalysis.setAnalyzer(cameraExecutor, new QrCodeAnalyzer(qrResult  -> {
			binding.previewView.post(() -> {
				Log.d("QRCodeAnalyzer", "Barcode scanned: " + qrResult.getText());
				String scannedUrl = qrResult.getText();

				if (!scannedUrl.startsWith("http://") && !scannedUrl.startsWith("https://")) {
					scannedUrl = "https://" + scannedUrl;
				}

				if (Patterns.WEB_URL.matcher(scannedUrl).matches()) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedUrl));
					try {
						startActivity(browserIntent);
					} catch (ActivityNotFoundException e) {
						// Handle exception
					}
				} else {
					// Handle the scenario when the scanned text is not a valid URL
				}
			});
		}));

		try {
			cameraProvider.unbindAll();
			Camera camera = cameraProvider.bindToLifecycle(requireActivity(), cameraSelector, preview, imageAnalysis);
			preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
		} catch (Exception e) {
			Log.e("Sauce", "Can't start camera", e);
		}
	}

	private boolean allPermissionsGranted() {
		for (String permission : REQUIRED_PERMISSIONS) {
			if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (cameraExecutor != null) {
			cameraExecutor.shutdown();
		}
		binding = null;
	}
}