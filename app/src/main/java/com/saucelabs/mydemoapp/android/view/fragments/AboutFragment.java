package com.saucelabs.mydemoapp.android.view.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.saucelabs.mydemoapp.android.BuildConfig;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentAboutBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;


public class AboutFragment extends BaseFragment implements View.OnClickListener {
    private FragmentAboutBinding binding;

    public static AboutFragment newInstance(String param1, String param2, int param3) {
        AboutFragment fragment = new AboutFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        setListeners();

        binding.versionTV.setText(
                String.format(
                        "V.%s-build %d",
                        BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE
                )
        );

        return binding.getRoot();
    }

    private void setListeners() {
        binding.webTV.setOnClickListener(this);
        binding.versionTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(binding.webTV)){
            openWebPage("https://saucelabs.com/");
        } else if (v.equals(binding.versionTV)) {
            openWebPage("https://github.com/saucelabs/my-demo-app-android/releases/tag/" + BuildConfig.VERSION_NAME);
        }
    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            requireActivity().startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireActivity(), "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}