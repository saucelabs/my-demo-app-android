package com.saucelabs.mydemoapp.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.saucelabs.mydemoapp.android.MyApplication;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.databinding.FragmentProductCatalogBinding;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.saucelabs.mydemoapp.android.view.adapters.ProductsAdapter;
import com.saucelabs.mydemoapp.android.viewModel.ProductCatalogViewModel;
import com.saucelabs.mydemoapp.android.viewModel.ProductCatalogViewModelFactory;

import java.util.List;

public class ProductCatalogFragment extends BaseFragment implements View.OnClickListener {
    private FragmentProductCatalogBinding binding;
    List<ProductModel> productList;
    ProductsAdapter adapter;
    ProductCatalogViewModel viewModel;

    public static ProductCatalogFragment newInstance(String param1, String param2, int param3) {
        ProductCatalogFragment fragment = new ProductCatalogFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_catalog, container, false);
        viewModel = ViewModelProviders.of(this, new ProductCatalogViewModelFactory(app)).get(ProductCatalogViewModel.class);
        bindData();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void bindData() {
        mDb = AppDatabase.getInstance(getActivity());

        int spanCount = 2;
        int spacing = 25;
        binding.productRV.setLayoutManager(new GridLayoutManager(mAct, spanCount));
        boolean includeEdge = false;
//        binding.productRV.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        observer();
    }

    private void observer() {
        viewModel.allProducts.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                if (productModels != null) {
                    productList = productModels;
                    setAdapter();
                }
            }
        });
    }

    private void checkLocalDataBase() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productList = mDb.personDao().getAllProducts();
                setAdapter();
            }
        });
    }

    public void updateData() {
        viewModel.getAllProducts(MainActivity.selectedSort);
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                productList = mDb.personDao().getAllProducts();
//                if (MainActivity.selectedSort == MainActivity.NAME_ASC) {
//                    productList = mDb.personDao().getPersonsSortByAscName();
//                } else if (MainActivity.selectedSort == MainActivity.NAME_DESC) {
//                    productList = mDb.personDao().getPersonsSortByDescName();
//                } else if (MainActivity.selectedSort == MainActivity.PRICE_ASC) {
//                    productList = mDb.personDao().getPersonsSortByAscPrice();
//                } else if (MainActivity.selectedSort == MainActivity.PRICE_DESC) {
//                    productList = mDb.personDao().getPersonsSortByDescPrice();
//                }
//
//                setAdapter();
//            }
//        });
    }

    private void setAdapter() {

        final Integer[] meta = new Integer[] {
                0, null, 2, 3, 4, 5 // null is an intentionally introduced bug for demos
        };

        mAct.runOnUiThread(() -> {
            adapter = new ProductsAdapter(mAct, productList, (position, status) -> {

//                if (position == 1) {
//                    MyApplication.backtraceClient.nativeCrash();
//                }

                Bundle bundle = ST.getBundle(MainActivity.FRAGMENT_PRODUCT_DETAIL, 1);
                bundle.putString("meta", "" + meta[position].intValue());
                bundle.putString(Constants.ARG_PARAM1, String.valueOf(productList.get(position).getId()));

                ST.startMainActivity(mAct, bundle);
            });
            binding.productRV.setAdapter(adapter);
        });
    }


    @Override
    public void onClick(View view) {

    }
}
