package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.Result;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.databinding.FragmentQrBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRFragment extends BaseFragment implements View.OnClickListener , ZXingScannerView.ResultHandler {
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
