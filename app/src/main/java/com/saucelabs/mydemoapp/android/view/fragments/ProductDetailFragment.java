package com.saucelabs.mydemoapp.android.view.fragments;

import static com.saucelabs.mydemoapp.android.utils.Network.fetch;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.databinding.FragmentProductDetailBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.model.CartItemModel;
import com.saucelabs.mydemoapp.android.model.ColorModel;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseFragment;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;
import com.saucelabs.mydemoapp.android.view.adapters.ColorsAdapter;
import com.saucelabs.mydemoapp.android.viewModel.ProductDetailViewModel;
import com.saucelabs.mydemoapp.android.viewModel.ProductDetailViewModelFactory;
import com.testfairy.TestFairy;

public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {
    private FragmentProductDetailBinding binding;
    ColorsAdapter adapter;
    ProductModel selectedProduct;
    ColorModel selectedColor;
    int cartNo = 1;

    ProductDetailViewModel viewModel;

    public static ProductDetailFragment newInstance(String param1, String param2, int param3) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        viewModel = ViewModelProviders.of(this, new ProductDetailViewModelFactory(app,mParam1)).get(ProductDetailViewModel.class);

        bindData();

        return binding.getRoot();
    }
    

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void bindData() {

        mDb = AppDatabase.getInstance(getActivity());
//        checkLocalDataBase();
        observer();
        setListener();
    }

    private void observer(){
        viewModel.product.observe(getViewLifecycleOwner(), new Observer<ProductModel>() {
            @Override
            public void onChanged(ProductModel productModel) {
                if(productModel != null){
                    selectedProduct =productModel;
                    setData();
                    fetch("https://my-demo-app.net/api/item-load?id=" + selectedProduct.getId());
                }
            }
        });
    }

    private void setListener() {
        binding.minusIV.setOnClickListener(this);
        binding.plusIV.setOnClickListener(this);
        binding.cartBt.setOnClickListener(this);
        binding.rattingV.start1IV.setOnClickListener(this);
        binding.rattingV.start2IV.setOnClickListener(this);
        binding.rattingV.start3IV.setOnClickListener(this);
        binding.rattingV.start4IV.setOnClickListener(this);
        binding.rattingV.start5IV.setOnClickListener(this);
    }

    private void checkLocalDataBase() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                selectedProduct = mDb.personDao().getProduct(Integer.parseInt(mParam1));
                setData();
            }
        });
    }

    private void setData() {
        if (selectedProduct != null) {
            binding.productTV.setText(selectedProduct.getTitle());
            binding.priceTV.setText(selectedProduct.getCurrency() + " " + selectedProduct.getPrice());
            binding.descTV.setText(selectedProduct.getDesc());
            binding.productIV.setImageResource(selectedProduct.getImageVal());
            handleRatting(selectedProduct.getRating());
            setAdapter();
        }

    }


    private void setAdapter() {
        mAct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.colorRV.setLayoutManager(new LinearLayoutManager(mAct, LinearLayoutManager.HORIZONTAL, false));

                adapter = new ColorsAdapter(mAct, selectedProduct.getColorList(), new OnItemClickListener() {
                    @Override
                    public void OnClick(int position, int status) {
                        selectedColor = selectedProduct.getColorList().get(position);
                    }
                });

                selectedColor = selectedProduct.getColorList().get(0);
                binding.colorRV.setAdapter(adapter);
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.equals(binding.minusIV)) {
            if (cartNo != 0) {
                cartNo -= 1;
                binding.noTV.setText(String.valueOf(cartNo));
            }
            if (cartNo == 0) {
                binding.cartBt.setEnabled(false);
                binding.cartBt.setBackgroundColor(getResources().getColor(R.color.light_grey));
                binding.cartBt.setTextColor(getResources().getColor(R.color.black));
                binding.cartBt.getBackground().setColorFilter(ContextCompat.getColor(mAct, R.color.light_grey), PorterDuff.Mode.MULTIPLY);

            }
        } else if (view.equals(binding.plusIV)) {
            cartNo += 1;
            binding.cartBt.setEnabled(true);
            binding.noTV.setText(String.valueOf(cartNo));
            binding.cartBt.setBackgroundColor(getResources().getColor(R.color.red));
            binding.cartBt.setTextColor(getResources().getColor(R.color.white));
            binding.cartBt.getBackground().setColorFilter(ContextCompat.getColor(mAct, R.color.red), PorterDuff.Mode.MULTIPLY);

        } else if (view.equals(binding.cartBt)) {
            addToCart(selectedProduct, cartNo);
        } else if (view.equals(binding.rattingV.start1IV)) {
            handleRatting(1);
            ST.showReviewDialog(mAct);
        } else if (view.equals(binding.rattingV.start2IV)) {
            handleRatting(2);
            ST.showReviewDialog(mAct);
        } else if (view.equals(binding.rattingV.start3IV)) {
            handleRatting(3);
            ST.showReviewDialog(mAct);
        } else if (view.equals(binding.rattingV.start4IV)) {
            handleRatting(4);
            ST.showReviewDialog(mAct);
        } else if (view.equals(binding.rattingV.start5IV)) {
            handleRatting(5);
            ST.showReviewDialog(mAct);
        }
    }

    private void addToCart(ProductModel productModel, int number) {
        // Intentionally introduced bug for demos
        if (productModel.getTitle().equals("Sauce Lab Bolt T-Shirt")) {
            number = 10;
        }

        boolean isAvailable = false;
        if (ST.cartItemList != null) {
            for (int pos = 0; pos < ST.cartItemList.size(); pos++) {
                CartItemModel model = ST.cartItemList.get(pos);
                if (model.getProductModel().getId() == productModel.getId() && selectedColor.getColorImg() == model.getColor()) {
                    int totalNumber = model.getNumberOfProduct() + number;
                    model.setNumberOfProduct(totalNumber);
                    ST.cartItemList.set(pos, model);
                    isAvailable = true;
                    fetch("https://my-demo-app.net/api/add-item?id=" + model.getProductModel().getId());
                }
            }
        }

        if (!isAvailable) {
            CartItemModel model = new CartItemModel();
            model.setProductModel(productModel);
            model.setColor(selectedColor.getColorImg());
            model.setNumberOfProduct(number);

            ST.cartItemList.add(model);
            fetch("https://my-demo-app.net/api/add-item?id=" + model.getProductModel().getId());

        }

        if (mAct instanceof MainActivity) {
            ((MainActivity) mAct).setData();
        }

        ST.syncCartToTestFairy(getContext());
    }

    private void handleRatting(int ratting) {
        TestFairy.setAttribute("product.id." + selectedProduct.getId() + ".rating", "" + ratting);

        binding.rattingV.start1IV.setImageResource(R.drawable.ic_unselected_start);
        binding.rattingV.start2IV.setImageResource(R.drawable.ic_unselected_start);
        binding.rattingV.start3IV.setImageResource(R.drawable.ic_unselected_start);
        binding.rattingV.start4IV.setImageResource(R.drawable.ic_unselected_start);
        binding.rattingV.start5IV.setImageResource(R.drawable.ic_unselected_start);

        if (ratting == 1) {
            binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
        } else if (ratting == 2) {
            binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
        } else if (ratting == 3) {
            binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
        } else if (ratting == 4) {
            binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start4IV.setImageResource(R.drawable.ic_selected_star);
        } else {
            binding.rattingV.start1IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start2IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start3IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start4IV.setImageResource(R.drawable.ic_selected_star);
            binding.rattingV.start5IV.setImageResource(R.drawable.ic_selected_star);
        }
    }


}
