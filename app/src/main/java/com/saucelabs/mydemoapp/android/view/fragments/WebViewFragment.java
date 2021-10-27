package com.saucelabs.mydemoapp.android.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentWebViewBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;


public class WebViewFragment extends BaseFragment implements View.OnClickListener {
    private FragmentWebViewBinding binding;
    private boolean isError = false;

    public static WebViewFragment newInstance(String param1, String param2, int param3) {
        WebViewFragment fragment = new WebViewFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false);
        init();
        setListeners();
        return binding.getRoot();
    }

    private void init() {
        if (!mParam1.contains("https://")) {
            mParam1 = "https://" + mParam1;
        }

        Animation rotation = AnimationUtils.loadAnimation(requireActivity(), R.anim.rotate);
        rotation.setFillAfter(true);
        binding.loadingIV.startAnimation(rotation);
        binding.webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();

        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.e("none", "Finished loading URL: " + url);

                if(!isError)
                    binding.webView.setVisibility(View.VISIBLE);

                hideLoading();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                isError = true;
                Toast.makeText(requireActivity(), getResources().getString(R.string.oh_no) + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();

                binding.errorCodeTV.setText("Error Code: " + errorCode);
                binding.errorNameTV.setText("Description: " + description);

                binding.errorCL.setVisibility(View.VISIBLE);
                binding.webView.setVisibility(View.GONE);
                hideLoading();
            }
        });

        if (mParam1 != null && !mParam1.isEmpty())
            binding.webView.loadUrl(mParam1);
    }

    private void hideLoading(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.loadingIV.clearAnimation();
                binding.loadingIV.setVisibility(View.GONE);
                binding.loadingTV.setVisibility(View.GONE);
            }
        },300);

    }


    private void setListeners() {

    }

    @Override
    public void onClick(View view) {

    }

}