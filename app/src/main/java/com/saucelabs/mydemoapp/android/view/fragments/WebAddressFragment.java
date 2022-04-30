package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentWebAddressBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.testfairy.TestFairy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebAddressFragment extends BaseFragment implements View.OnClickListener {
	private FragmentWebAddressBinding binding;

	public static WebAddressFragment newInstance(String param1, String param2, int param3) {
		WebAddressFragment fragment = new WebAddressFragment();
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

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_address, container, false);
		init();
		setListeners();
		return binding.getRoot();
	}

	private void init() {
	}

	private void setListeners() {
		binding.goBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(binding.goBtn)) {
			try {
				sendLink();
			} catch (MalformedURLException e) {
				TestFairy.logThrowable(e);
			}
		}
	}

	private void sendLink() throws MalformedURLException {

		String urlString = binding.urlET.getText().toString().trim();
//        if(!urlString.startsWith("https://")){
//            if(!urlString.startsWith("http://")){
//                urlString = "https://"+urlString;
//            }
//        }
		if (binding.urlET.getText().toString().trim().isEmpty()) {
			binding.urlErrorTV.setVisibility(View.VISIBLE);
		} else if (!isValidUrl(urlString)) {
			binding.urlErrorTV.setVisibility(View.VISIBLE);
		} else {

			binding.urlErrorTV.setVisibility(View.INVISIBLE);
			Bundle bundle = ST.getBundle(MainActivity.FRAGMENT_WEB_VIEW, 1);
			bundle.putString(Constants.ARG_PARAM1, urlString);
			ST.startMainActivity(mAct, bundle);
			binding.urlET.setText("");
		}
	}

	private boolean isValidUrl(String url) {
		String WebUrl = "^((ftp|http|https):\\/\\/)?(www.)?(?!.*(ftp|http|https|www.))[a-zA-Z0-9_-]+(\\.[a-zA-Z]+)+((\\/)[\\w#]+)*(\\/\\w+\\?[a-zA-Z0-9_]+=\\w+(&[a-zA-Z0-9_]+=\\w+)*)?$";
		String website = url.trim();
		if (website.trim().length() > 0) {
			if (!website.matches(WebUrl)) {
				//validation msg
				return false;
			}
		}
		return true;
	}
}