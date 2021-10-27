package com.saucelabs.mydemoapp.android.view.activities;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.database.AppDao;
import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.databinding.ActivitySplashBinding;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;
import com.saucelabs.mydemoapp.android.viewModel.SplashViewModel;
import com.saucelabs.mydemoapp.android.viewModel.SplashViewModelFactory;

import java.util.List;

public class SplashActivity extends BaseActivity {
	private ActivitySplashBinding binding;
	SplashViewModel viewModel;
	private AppDatabase mDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

		viewModel = ViewModelProviders.of(this, new SplashViewModelFactory(this.getApplication())).get(SplashViewModel.class);
		initViews();
	}

	private void initViews() {
		mDb = AppDatabase.getInstance(getApplicationContext());
		checkObserver();
	}

	private void checkObserver() {
		viewModel.allProducts.observe(this, new Observer<List<ProductModel>>() {
			@Override
			public void onChanged(List<ProductModel> productModels) {
				if (productModels != null && productModels.size() > 0) {
					ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_FINISH);
				} else {
					populateProductsDb(viewModel);
				}
			}
		});

		viewModel.pb.observe(this, new Observer<Integer>() {
			@Override
			public void onChanged(Integer integer) {
				if (integer == View.GONE) {
					ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_FINISH);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void insertProducts(AppDao dao, List<ProductModel> list) {
		AppExecutors.getInstance().diskIO().execute(new Runnable() {
			@Override
			public void run() {
				dao.insertProduct(list);
				ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_FINISH);
			}
		});
	}

	private void checkLocalDataBase() {
		AppExecutors.getInstance().diskIO().execute(new Runnable() {
			@Override
			public void run() {
				final List<ProductModel> productList = mDb.personDao().getAllProducts();
				if (productList != null && productList.size() == 0) {
					populateProductsDb(viewModel);
				} else {
//                    myVieswModel.addDelays();
					ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_FINISH);
				}
			}
		});
	}
}
