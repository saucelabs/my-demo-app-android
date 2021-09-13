package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentPlaceOrderBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.model.CheckoutInfo;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.saucelabs.mydemoapp.android.view.adapters.CartItemAdapter;


public class PlaceOrderFragment extends BaseFragment implements View.OnClickListener {
    private FragmentPlaceOrderBinding binding;
    private CartItemAdapter adapter;

    public static PlaceOrderFragment newInstance(String param1, String param2, int param3) {
        PlaceOrderFragment fragment = new PlaceOrderFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_order, container, false);
        init();
        setListeners();
        return binding.getRoot();
    }

    private void init() {
        CheckoutInfo info = ST.billingInfo;
        binding.fullNameTV.setText(ST.checkoutInfo.getName());
        binding.addressTV.setText(ST.checkoutInfo.getAddress1());
        binding.cityTV.setText(ST.checkoutInfo.getCity()+", "+ST.checkoutInfo.getState());
        binding.countryTV.setText(ST.checkoutInfo.getCountry()+", "+ST.checkoutInfo.getZip());
        binding.cardHolderTV.setText(ST.checkoutInfo.getCardHolderName());
        binding.cardNumberTV.setText(ST.checkoutInfo.getCardNumber());
        binding.expirationDateTV.setText("Exp: "+ST.checkoutInfo.getExpirationDate());
//        binding.billingAddressTV.setText(info.getBillingAddress());
        binding.itemNumberTV.setText(ST.getTotalNum() + " Items");
//        binding.totalAmountTV.setText("$ " + (ST.getTotalPrice(ST.cartItemList) + 5.99));

        binding.totalAmountTV.setText(ST.CURRENCY + " " + String.format("%.2f",(ST.getTotalPrice(ST.cartItemList) + 5.99)));


        if(ST.billingInfo.isSameShipping()){
            binding.billingAddressTV.setVisibility(View.VISIBLE);
        }else{
            binding.billingAddressTV.setVisibility(View.GONE);
            binding.billingAddressLL.setVisibility(View.VISIBLE);
        }


        binding.billFullnameTV.setText(ST.billingInfo.getName());
        binding.billaddressTV.setText(ST.billingInfo.getAddress1()+","+ST.billingInfo.getAddress2());
        binding.billingCityAndStateTV.setText(ST.billingInfo.getCity()+","+ST.billingInfo.getState());
        binding.billingZipAndCountryTV.setText(ST.billingInfo.getZip()+","+ST.billingInfo.getCountry());

        setAdapter();
    }

    private void setListeners() {
        binding.paymentBtn.setOnClickListener(this);
    }

    private void setAdapter() {
        binding.placeOrderRV.setLayoutManager(new LinearLayoutManager(mAct));
        adapter = new CartItemAdapter(mAct, ST.cartItemList,false, new OnItemClickListener() {
            @Override
            public void OnClick(int position, int status) {
            }
        });

        binding.placeOrderRV.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if(view.equals(binding.paymentBtn)){
            ST.startMainActivity(mAct, ST.getBundle(MainActivity.FRAGMENT_CHECKOUT_COMPLETE, 1));
        }
    }
}