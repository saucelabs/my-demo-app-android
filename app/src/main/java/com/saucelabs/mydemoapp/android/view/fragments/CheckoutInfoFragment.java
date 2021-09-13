package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentCheckoutInfoBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutInfoFragment extends BaseFragment implements View.OnClickListener {
    private FragmentCheckoutInfoBinding binding;

    public static CheckoutInfoFragment newInstance(String param1, String param2, int param3) {
        CheckoutInfoFragment fragment = new CheckoutInfoFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout_info, container, false);
        init();
        setListeners();
        return binding.getRoot();
    }

    private void init() {
        binding.fullNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.fullNameET.getText().toString().length() == 0){
                    binding.fullNameErrorIV.setVisibility(View.VISIBLE);
                    binding.fullNameErrorTV.setVisibility(View.VISIBLE);
                    binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
                    binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                }else{
                    binding.fullNameErrorIV.setVisibility(View.GONE);
                    binding.fullNameErrorTV.setVisibility(View.INVISIBLE);
                    binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
                    binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.address1ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.address1ErrorIV.setVisibility(View.VISIBLE);
                    binding.address1ErrorTV.setVisibility(View.VISIBLE);
                    binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
                    binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                }else{
                    binding.address1ErrorIV.setVisibility(View.GONE);
                    binding.address1ErrorTV.setVisibility(View.INVISIBLE);
                    binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
                    binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.cityET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.cityIV.setVisibility(View.VISIBLE);
                    binding.cityErrorTV.setVisibility(View.VISIBLE);
                    binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
                    binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                }else{
                    binding.cityIV.setVisibility(View.GONE);
                    binding.cityErrorTV.setVisibility(View.INVISIBLE);
                    binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
                    binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.zipET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.zipIV.setVisibility(View.VISIBLE);
                    binding.zipErrorTV.setVisibility(View.VISIBLE);
                    binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
                    binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                }else{
                    binding.zipIV.setVisibility(View.GONE);
                    binding.zipErrorTV.setVisibility(View.INVISIBLE);
                    binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
                    binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.countryET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.countryIV.setVisibility(View.VISIBLE);
                    binding.countryErrorTV.setVisibility(View.VISIBLE);
                    binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
                    binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                }else{
                    binding.countryIV.setVisibility(View.GONE);
                    binding.countryErrorTV.setVisibility(View.INVISIBLE);
                    binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
                    binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setListeners() {
        binding.paymentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(binding.paymentBtn)){
            validate();
        }
    }

    private void validate() {
        String fullName = "";
        String address1 = "";
        String address2 = "";
        String city = "";
        String state = "";
        String zip = "";
        String country = "";

        if(binding.fullNameET.getText().toString().trim().length() == 0){
            binding.fullNameErrorIV.setVisibility(View.VISIBLE);
            binding.fullNameErrorTV.setVisibility(View.VISIBLE);
            binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
            binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            fullName = "";
        }else{
            binding.fullNameErrorIV.setVisibility(View.GONE);
            binding.fullNameErrorTV.setVisibility(View.INVISIBLE);
            binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
            binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
            fullName = binding.fullNameET.getText().toString().trim();
        }

        if(binding.address1ET.getText().toString().trim().length() == 0){
            binding.address1ErrorIV.setVisibility(View.VISIBLE);
            binding.address1ErrorTV.setVisibility(View.VISIBLE);
            binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
            binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            address1 = "";
        }else{
            binding.address1ErrorIV.setVisibility(View.GONE);
            binding.address1ErrorTV.setVisibility(View.INVISIBLE);
            binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
            binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
            address1 = binding.address1ET.getText().toString().trim();
        }

        if(binding.cityET.getText().toString().trim().length() == 0){
            binding.cityIV.setVisibility(View.VISIBLE);
            binding.cityErrorTV.setVisibility(View.VISIBLE);
            binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
            binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            city = "";
        }else{
            binding.cityIV.setVisibility(View.GONE);
            binding.cityErrorTV.setVisibility(View.INVISIBLE);
            binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
            binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
            city = binding.cityET.getText().toString().trim();
        }

        if(binding.zipET.getText().toString().trim().length() == 0){
            binding.zipIV.setVisibility(View.VISIBLE);
            binding.zipErrorTV.setVisibility(View.VISIBLE);
            binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
            binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            zip = "";
        }else{
            binding.zipIV.setVisibility(View.GONE);
            binding.zipErrorTV.setVisibility(View.INVISIBLE);
            binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
            binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
            zip = binding.zipET.getText().toString().trim();
        }

        if(binding.countryET.getText().toString().trim().length() == 0){
            binding.countryIV.setVisibility(View.VISIBLE);
            binding.countryErrorTV.setVisibility(View.VISIBLE);
            binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_red));
            binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            country = "";
        }else{
            binding.countryIV.setVisibility(View.GONE);
            binding.countryErrorTV.setVisibility(View.INVISIBLE);
            binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_bg_grey));
            binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.grey_text_color));
            country = binding.countryET.getText().toString().trim();
        }

        if(binding.address2ET.getText().toString().trim().length() != 0){
            address2 = binding.address2ET.getText().toString().trim();
        }

        if(binding.stateET.getText().toString().trim().length() != 0){
            state = binding.stateET.getText().toString().trim();
        }

        if(!fullName.isEmpty() && !address1.isEmpty() && !city.isEmpty() && !zip.isEmpty() && !country.isEmpty()){
            ST.checkoutInfo.setName(fullName);
            ST.checkoutInfo.setAddress1(address1);
            ST.checkoutInfo.setAddress2(address2);
            ST.checkoutInfo.setCity(city);
            ST.checkoutInfo.setZip(zip);
            ST.checkoutInfo.setState(state);
            ST.checkoutInfo.setCountry(country);
            Log.e("none", "validate: success" );

            ST.startMainActivity(mAct, ST.getBundle(MainActivity.FRAGMENT_CHECKOUT, 1));
        }
    }
}