package com.saucelabs.mydemoapp.android.view.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.Result;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.databinding.FragmentQrBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.testfairy.TestFairy;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.saucelabs.mydemoapp.android.view.activities.MainActivity.FRAGMENT_PRODUCT_CATAlOG;

public class QRFragment extends BaseFragment implements View.OnClickListener, ZXingScannerView.ResultHandler {
	private FragmentQrBinding binding;
	private ZXingScannerView mScannerView;

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

		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void bindData() {
		mDb = AppDatabase.getInstance(getActivity());
		mScannerView = new ZXingScannerView(mAct);
		binding.cameraFL.addView(mScannerView);
		checkPermission();
	}

	private void checkPermission() {
		if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 1002);
		} else {
			TestFairy.addEvent("Location permission granted");
		}
		//ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
		//MenuFragment.requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS)
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1002) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				TestFairy.addEvent("Camera permission granted");
			} else {
				TestFairy.addEvent("Camera permission denied");
				ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_PRODUCT_CATAlOG, 1));
//                        singleton.showCommonDialog(null, "Permission Denied!", "Please go to settings and enable permissions.", getString(R.string.ok), true);
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mScannerView.stopCamera();           // Stop camera on pause
	}

	@Override
	public void onResume() {
		super.onResume();
		mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
		mScannerView.startCamera();          // Start camera on resume
	}


	@Override
	public void onClick(View view) {

	}

	@Override
	public void handleResult(Result rawResult) {

	}
}
