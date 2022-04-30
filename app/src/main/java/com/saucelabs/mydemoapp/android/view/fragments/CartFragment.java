package com.saucelabs.mydemoapp.android.view.fragments;

import static com.saucelabs.mydemoapp.android.utils.Network.fetch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.databinding.FragmentCartBinding;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.saucelabs.mydemoapp.android.view.adapters.CartItemAdapter;

public class CartFragment extends BaseFragment implements View.OnClickListener {
	private FragmentCartBinding binding;
	CartItemAdapter adapter;

	public static CartFragment newInstance(String param1, String param2, int param3) {
		CartFragment fragment = new CartFragment();
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
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
		bindData();

		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void bindData() {
		mDb = AppDatabase.getInstance(getActivity());
		setListener();
		setData();
		checkLocalDataBase();
	}

	private void setListener() {
		binding.shoppingBt.setOnClickListener(this);
		binding.cartBt.setOnClickListener(this);
	}

	private void checkLocalDataBase() {
		setAdapter();
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                productList = mDb.personDao().getAllProducts();
//
//            }
//        });
	}

	private void setAdapter() {
		binding.productRV.setLayoutManager(new LinearLayoutManager(mAct));
		adapter = new CartItemAdapter(mAct, ST.cartItemList, true, (position, status) -> setData());
		binding.productRV.setAdapter(adapter);
	}

	public void setData() {
		if (ST.cartItemList == null || ST.cartItemList.size() == 0) {
			binding.noItemCL.setVisibility(View.VISIBLE);
			binding.cartCL.setVisibility(View.GONE);
		} else {
			binding.noItemCL.setVisibility(View.GONE);
			binding.cartCL.setVisibility(View.VISIBLE);
		}

		binding.itemsTV.setText(ST.getTotalNum() + " " + getString(R.string.items));
		binding.totalPriceTV.setText(ST.CURRENCY + " " + ST.getTotalPrice(ST.cartItemList));
		binding.totalPriceTV.setText(ST.CURRENCY + " " + String.format("%.2f", ST.getTotalPrice(ST.cartItemList)));
		if (mAct instanceof MainActivity) {
			((MainActivity) mAct).setData();
		}
	}

	@Override
	public void onClick(View view) {
		if (view.equals(binding.shoppingBt)) {
			ST.startMainActivity(mAct, ST.getBundle(MainActivity.FRAGMENT_PRODUCT_CATAlOG, 1));
		} else if (view.equals(binding.cartBt)) {
			if (ST.isLogin) {
				ST.startMainActivity(mAct, ST.getBundle(MainActivity.FRAGMENT_CHECKOUT_INFO, 1));
			} else {
				Bundle bundle = ST.getBundle(MainActivity.FRAGMENT_LOGIN, 1);
				bundle.putString(Constants.ARG_PARAM1, ST.CHECKOUT);
				ST.startMainActivity(mAct, bundle);
			}

			fetch("https://my-demo-app.net/api/checkout");
		}
	}
}
