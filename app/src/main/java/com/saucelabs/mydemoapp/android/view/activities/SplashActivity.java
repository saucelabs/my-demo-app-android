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
import com.saucelabs.mydemoapp.android.model.ColorModel;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;
import com.saucelabs.mydemoapp.android.viewModel.SplashViewModel;
import com.saucelabs.mydemoapp.android.viewModel.SplashViewModelFactory;

import java.util.ArrayList;
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
					addData();
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

	private void addData() {
		List<ProductModel> list = new ArrayList<>();
		List<ColorModel> colorList = new ArrayList<>();
		ColorModel colorModel;

		ProductModel model = new ProductModel();
		model.setTitle("Sauce Lab Bolt T-Shirt");
		model.setPrice(15.99);
		model.setColors(3);
		model.setRatting(4);
		model.setCurrency("$");
		model.setDesc("Get your testing superhero on with the sauce Labs bolt T-Shirt. From American Apparel, 100% ringspun combed cotton gray with red bolt.");
		model.setImageVal(R.drawable.ic_product2);
		colorModel = new ColorModel(R.drawable.ic_black_circle, ST.BLACK);
		colorList.add(colorModel);

		model.setColorList(colorList);
		list.add(model);

		model = new ProductModel();
		colorList = new ArrayList<>();
		model.setTitle("Sauce Lab Fleece T-Shirt");
		model.setPrice(49.99);
		model.setColors(1);
		model.setRatting(4);
		model.setCurrency("$");
		model.setDesc("Its not everyday that you come across a midweight quarter-zip fleece jacket capable of handling everythinh from a relaxing day outdoors to a busy day at the office.");
		model.setImageVal(R.drawable.ic_product3);
		colorModel = new ColorModel(R.drawable.ic_gray_circle, ST.GRAY);
		colorList.add(colorModel);

		model.setColorList(colorList);
		list.add(model);

		model = new ProductModel();
		colorList = new ArrayList<>();
		model.setTitle("Sauce Lab Onesie");
		model.setPrice(7.99);
		model.setColors(1);
		model.setRatting(4);
		model.setCurrency("$");
		model.setDesc("The Sauce Labs Backpack is the best of all worlds - infinite capacity and endless style.\n" +
				"Fits in comfortably a laptop up to 15 inch. Never leave a thing behind and forget you have your world on your shoulders.");
		model.setImageVal(R.drawable.ic_product1);

		colorModel = new ColorModel(R.drawable.ic_black_circle, ST.BLACK);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_gray_circle, ST.GRAY);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_red_circle, ST.RED);
		colorList.add(colorModel);

		model.setColorList(colorList);
		list.add(model);

		model = new ProductModel();
		colorList = new ArrayList<>();
		model.setTitle("Sauce Lab Back Packs");
		model.setPrice(29.99);
		model.setColors(3);
		model.setRatting(4);
		model.setCurrency("$");
		model.setDesc("carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
		model.setImageVal(R.drawable.ic_product4);

		colorModel = new ColorModel(R.drawable.ic_black_circle, ST.BLACK);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_blue_circle, ST.BLUE);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_gray_circle, ST.GRAY);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_red_circle, ST.RED);
		colorList.add(colorModel);

		model.setColorList(colorList);
		list.add(model);


		model = new ProductModel();
		colorList = new ArrayList<>();
		model.setTitle("Sauce Lab Bike Light");
		model.setPrice(9.99);
		model.setColors(1);
		model.setRatting(4);
		model.setCurrency("$");
		model.setDesc("A red light isn't the desire state in testing but it sure helps when riding your bike at night. Water-resistance with 3 lighting modes, 1 AAA battery included.");
		model.setImageVal(R.drawable.ic_product6);

		colorModel = new ColorModel(R.drawable.ic_black_circle, ST.BLACK);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_gray_circle, ST.GRAY);
		colorList.add(colorModel);

		colorModel = new ColorModel(R.drawable.ic_red_circle, ST.RED);
		colorList.add(colorModel);

		model.setColorList(colorList);
		list.add(model);

		model = new ProductModel();
		colorList = new ArrayList<>();
		model.setTitle("Test.sllTheThings() T-Shirt");
		model.setPrice(15.99);
		model.setColors(3);
		model.setRatting(4);
		model.setCurrency("$");
		model.setDesc("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
		model.setImageVal(R.drawable.ic_product5);

		colorModel = new ColorModel(R.drawable.ic_black_circle, ST.BLACK);
		colorList.add(colorModel);

		model.setColorList(colorList);
		list.add(model);

		viewModel.insertProducts(list);
//        insertProducts(mDb.personDao(), list);
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
					addData();
				} else {
//                    myVieswModel.addDelays();
					ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_FINISH);
				}
			}
		});
	}


}
