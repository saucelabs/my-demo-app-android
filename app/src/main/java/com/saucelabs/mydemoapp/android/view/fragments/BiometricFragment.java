package com.saucelabs.mydemoapp.android.view.fragments;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentBiometricBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;

public class BiometricFragment extends BaseFragment implements View.OnClickListener {
    private FragmentBiometricBinding binding;
    BiometricManager manager;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public static BiometricFragment newInstance(String param1, String param2, int param3) {
        BiometricFragment fragment = new BiometricFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_biometric, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        manager = BiometricManager.from(requireActivity());

        if(manager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS){
            binding.bioMetricSw.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.bioMetricSw.setFocusable(View.FOCUSABLE);
            }
        }else {
            binding.bioMetricSw.setEnabled(false);
            binding.bioMetricSw.setChecked(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.bioMetricSw.setFocusable(View.NOT_FOCUSABLE);
            }
            ST.showDialog(null , mAct , getString(R.string.biometrics),getString(R.string.biomatric_message),getString(R.string.ok));
            Constants.is_biometric = false;
        }
        setListeners();
    }

    private void setListeners() {
        binding.bioMetricSw.setOnClickListener(this);

        binding.bioMetricSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Constants.is_biometric = true;
//                    BiometricLogin();
                }else{
                    Constants.is_biometric = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.equals(binding.bioMetricSw)){
            if(binding.bioMetricSw.isChecked()){
                Constants.is_biometric = true;
            }else{
                Constants.is_biometric = false;
            }
        }
    }

    private void BiometricLogin() {
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Login using you biometric credential")
                .setDescription("Unlock using fingerprint")
                .setAllowedAuthenticators(BIOMETRIC_WEAK)
                .setConfirmationRequired(true)
                .setNegativeButtonText("Nothing")
                .build();

        biometricPrompt = new BiometricPrompt(this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("", "onAuthenticationError: ");
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.e("", "onAuthenticationSucceeded: ");
                ST.isLogin = true;
                ST.startActivity(mAct , MainActivity.class , ST.START_ACTIVITY_WITH_CLEAR_BACK_STACK);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("", "onAuthenticationFailed: ");
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }
}