package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.FragmentCheckoutBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.testfairy.TestFairy;


public class CheckoutFragment extends BaseFragment implements View.OnClickListener {
	private FragmentCheckoutBinding binding;

	String fullName = "";
	String address1 = "";
	String address2 = "";
	String city = "";
	String state = "";
	String zip = "";
	String country = "";
	String name = "";
	String cardNumber = "";
	String expirationDate = "";
	String securityCode = "";

	public static CheckoutFragment newInstance(String param1, String param2, int param3) {
		CheckoutFragment fragment = new CheckoutFragment();
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

//        TestFairy.hideView(R.id.nameET);
		TestFairy.hideView(R.id.cardNumberET);
		TestFairy.hideView(R.id.expirationDateET);
		TestFairy.hideView(R.id.securityCodeET);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
		init();
		setListeners();
		return binding.getRoot();
	}

	private void init() {
		setTextWatchers();
	}

	private void setTextWatchers() {
		binding.nameET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if (charSequence.length() == 0) {
					binding.nameErrorIV.setVisibility(View.VISIBLE);
					binding.nameErrorTV.setVisibility(View.VISIBLE);
					binding.nameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.nameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.nameErrorIV.setVisibility(View.GONE);
					binding.nameErrorTV.setVisibility(View.INVISIBLE);
					binding.nameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.nameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		binding.cardNumberET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if (charSequence.length() == 0) {
					binding.cardNumberErrorIV.setVisibility(View.VISIBLE);
					binding.cardNumberTV.setVisibility(View.VISIBLE);
					binding.cardNumberRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.cardNumberET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.cardNumberErrorIV.setVisibility(View.GONE);
					binding.cardNumberErrorTV.setVisibility(View.INVISIBLE);
					binding.cardNumberRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.cardNumberET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		binding.expirationDateET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if (charSequence.length() == 0) {
					binding.expirationDateIV.setVisibility(View.VISIBLE);
					binding.expirationDateErrorTV.setVisibility(View.VISIBLE);
					binding.expirationDateRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.expirationDateET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.expirationDateIV.setVisibility(View.GONE);
					binding.expirationDateErrorTV.setVisibility(View.INVISIBLE);
					binding.expirationDateRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.expirationDateET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		binding.securityCodeET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if (charSequence.length() == 0) {
					binding.securityCodeIV.setVisibility(View.VISIBLE);
					binding.securityCodeErrorTV.setVisibility(View.VISIBLE);
					binding.securityCodeRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.securityCodeET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.securityCodeIV.setVisibility(View.GONE);
					binding.securityCodeErrorTV.setVisibility(View.INVISIBLE);
					binding.securityCodeRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.securityCodeET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		binding.fullNameET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if (binding.fullNameET.getText().toString().length() == 0) {
					binding.fullNameErrorIV.setVisibility(View.VISIBLE);
					binding.fullNameErrorTV.setVisibility(View.VISIBLE);
					binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.fullNameErrorIV.setVisibility(View.GONE);
					binding.fullNameErrorTV.setVisibility(View.INVISIBLE);
					binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
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
				if (charSequence.length() == 0) {
					binding.address1ErrorIV.setVisibility(View.VISIBLE);
					binding.address1ErrorTV.setVisibility(View.VISIBLE);
					binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.address1ErrorIV.setVisibility(View.GONE);
					binding.address1ErrorTV.setVisibility(View.INVISIBLE);
					binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
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
				if (charSequence.length() == 0) {
					binding.cityIV.setVisibility(View.VISIBLE);
					binding.cityErrorTV.setVisibility(View.VISIBLE);
					binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.cityIV.setVisibility(View.GONE);
					binding.cityErrorTV.setVisibility(View.INVISIBLE);
					binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
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
				if (charSequence.length() == 0) {
					binding.zipIV.setVisibility(View.VISIBLE);
					binding.zipErrorTV.setVisibility(View.VISIBLE);
					binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.zipIV.setVisibility(View.GONE);
					binding.zipErrorTV.setVisibility(View.INVISIBLE);
					binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
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
				if (charSequence.length() == 0) {
					binding.countryIV.setVisibility(View.VISIBLE);
					binding.countryErrorTV.setVisibility(View.VISIBLE);
					binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
					binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
				} else {
					binding.countryIV.setVisibility(View.GONE);
					binding.countryErrorTV.setVisibility(View.INVISIBLE);
					binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
					binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

	}

	private void setListeners() {
		binding.paymentBtn.setOnClickListener(this);
		binding.billingAddressCB.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(binding.paymentBtn)) {
			validate();
		} else if (view.equals(binding.billingAddressCB)) {
			if (binding.billingAddressCB.isChecked()) {
				binding.checkoutInfoCL.setVisibility(View.GONE);
			} else {
				binding.checkoutInfoCL.setVisibility(View.VISIBLE);
			}
		}
	}

	private void validate() {
		if (!binding.billingAddressCB.isChecked()) {
			infoValidation();
		} else {
			fullName = ST.checkoutInfo.getName();
			address1 = ST.checkoutInfo.getAddress1();
			address2 = ST.checkoutInfo.getAddress2();
			state = ST.checkoutInfo.getState();
			zip = ST.checkoutInfo.getZip();
			city = ST.checkoutInfo.getCity();
			country = ST.checkoutInfo.getCountry();
			ST.billingInfo = ST.checkoutInfo;
			ST.billingInfo.setBillingAddress(address1);
		}

		if (binding.nameET.getText().toString().trim().length() == 0) {
			binding.nameErrorIV.setVisibility(View.VISIBLE);
			binding.nameErrorTV.setVisibility(View.VISIBLE);
			binding.nameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.nameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			name = "";
		} else {
			binding.nameErrorIV.setVisibility(View.GONE);
			binding.nameErrorTV.setVisibility(View.INVISIBLE);
			binding.nameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.nameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			name = binding.nameET.getText().toString().trim();
		}

		if (binding.cardNumberET.getText().toString().trim().length() == 0) {
			binding.cardNumberErrorIV.setVisibility(View.VISIBLE);
			binding.cardNumberTV.setVisibility(View.VISIBLE);
			binding.cardNumberRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.cardNumberET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			cardNumber = "";
		} else {
			binding.cardNumberErrorIV.setVisibility(View.GONE);
			binding.cardNumberErrorTV.setVisibility(View.INVISIBLE);
			binding.cardNumberRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.cardNumberET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			cardNumber = binding.cardNumberET.getText().toString().trim();
		}

		if (binding.expirationDateET.getText().toString().trim().length() == 0) {
			binding.expirationDateIV.setVisibility(View.VISIBLE);
			binding.expirationDateErrorTV.setVisibility(View.VISIBLE);
			binding.expirationDateRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.expirationDateET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			expirationDate = "";
		} else {
			binding.expirationDateIV.setVisibility(View.GONE);
			binding.expirationDateErrorTV.setVisibility(View.INVISIBLE);
			binding.expirationDateRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.expirationDateET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			expirationDate = binding.expirationDateET.getText().toString().trim();
		}

		if (binding.securityCodeET.getText().toString().trim().length() == 0) {
			binding.securityCodeIV.setVisibility(View.VISIBLE);
			binding.securityCodeErrorTV.setVisibility(View.VISIBLE);
			binding.securityCodeRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.securityCodeET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			securityCode = "";
		} else {
			binding.securityCodeIV.setVisibility(View.GONE);
			binding.securityCodeErrorTV.setVisibility(View.INVISIBLE);
			binding.securityCodeRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.securityCodeET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			securityCode = binding.securityCodeET.getText().toString().trim();
		}

		if (!fullName.isEmpty() && !address1.isEmpty() && !city.isEmpty() && !zip.isEmpty() && !country.isEmpty() && !name.isEmpty() && !cardNumber.isEmpty() && !expirationDate.isEmpty() && !securityCode.isEmpty()) {
			Log.e("none", "validate: success");
			ST.checkoutInfo.setCardHolderName(name);
			ST.checkoutInfo.setCardNumber(cardNumber);
			ST.checkoutInfo.setExpirationDate(expirationDate);
			ST.checkoutInfo.setSecurityCode(securityCode);
			ST.checkoutInfo.setSameShipping(binding.billingAddressCB.isChecked());

			ST.startMainActivity(mAct, ST.getBundle(MainActivity.FRAGMENT_PLACE_ORDER, 1));
		} else {
			TestFairy.addEvent("Checkout error");
		}
	}

	private void infoValidation() {
		if (binding.fullNameET.getText().toString().trim().length() == 0) {
			binding.fullNameErrorIV.setVisibility(View.VISIBLE);
			binding.fullNameErrorTV.setVisibility(View.VISIBLE);
			binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			fullName = "";
		} else {
			binding.fullNameErrorIV.setVisibility(View.GONE);
			binding.fullNameErrorTV.setVisibility(View.INVISIBLE);
			binding.fullNameRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.fullNameET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			fullName = binding.fullNameET.getText().toString().trim();
		}

		if (binding.address1ET.getText().toString().trim().length() == 0) {
			binding.address1ErrorIV.setVisibility(View.VISIBLE);
			binding.address1ErrorTV.setVisibility(View.VISIBLE);
			binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			address1 = "";
		} else {
			binding.address1ErrorIV.setVisibility(View.GONE);
			binding.address1ErrorTV.setVisibility(View.INVISIBLE);
			binding.address1RL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.address1ET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			address1 = binding.address1ET.getText().toString().trim();
		}

		if (binding.cityET.getText().toString().trim().length() == 0) {
			binding.cityIV.setVisibility(View.VISIBLE);
			binding.cityErrorTV.setVisibility(View.VISIBLE);
			binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			city = "";
		} else {
			binding.cityIV.setVisibility(View.GONE);
			binding.cityErrorTV.setVisibility(View.INVISIBLE);
			binding.cityRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.cityET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			city = binding.cityET.getText().toString().trim();
		}

		if (binding.zipET.getText().toString().trim().length() == 0) {
			binding.zipIV.setVisibility(View.VISIBLE);
			binding.zipErrorTV.setVisibility(View.VISIBLE);
			binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			zip = "";
		} else {
			binding.zipIV.setVisibility(View.GONE);
			binding.zipErrorTV.setVisibility(View.INVISIBLE);
			binding.zipRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.zipET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			zip = binding.zipET.getText().toString().trim();
		}

		if (binding.countryET.getText().toString().trim().length() == 0) {
			binding.countryIV.setVisibility(View.VISIBLE);
			binding.countryErrorTV.setVisibility(View.VISIBLE);
			binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_red));
			binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red));
			country = "";
		} else {
			binding.countryIV.setVisibility(View.GONE);
			binding.countryErrorTV.setVisibility(View.INVISIBLE);
			binding.countryRL.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_bg_grey));
			binding.countryET.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.grey_text_color));
			country = binding.countryET.getText().toString().trim();
		}

		if (binding.address2ET.getText().toString().trim().length() != 0) {
			address2 = binding.address2ET.getText().toString().trim();
		}

		if (binding.stateET.getText().toString().trim().length() != 0) {
			state = binding.stateET.getText().toString().trim();
		}

		if (!fullName.isEmpty() && !address1.isEmpty() && !city.isEmpty() && !zip.isEmpty() && !country.isEmpty()) {
//            ST.billingInfo = ST.checkoutInfo;
			ST.billingInfo.setName(binding.fullNameET.getText().toString());
			ST.billingInfo.setBillingAddress(binding.billingAddressCB.getText().toString());
			ST.billingInfo.setCity(binding.cityET.getText().toString());
			ST.billingInfo.setState(binding.stateET.getText().toString());
			ST.billingInfo.setAddress1(binding.address1ET.getText().toString());
			ST.billingInfo.setAddress2(binding.address2ET.getText().toString());
			ST.billingInfo.setZip(binding.zipET.getText().toString());
			ST.billingInfo.setCountry(binding.countryET.getText().toString());
			ST.billingInfo.setSameShipping(binding.billingAddressCB.isChecked());
		}
	}
}