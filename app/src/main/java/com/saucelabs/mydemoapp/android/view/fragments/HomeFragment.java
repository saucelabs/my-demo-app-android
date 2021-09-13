package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentHomeBinding;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.utils.Constants;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance(String param1, String param2, int param3) {
        HomeFragment fragment = new HomeFragment();
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

//        ST.logActivityToFirebase(getActivity(),"HomeActivity", ST.SCREEN_HOME, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        bindData();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void bindData() {
    }


    @Override
    public void onClick(View view) {

    }
}
