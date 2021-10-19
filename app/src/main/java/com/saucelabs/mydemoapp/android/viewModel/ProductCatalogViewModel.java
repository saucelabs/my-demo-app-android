package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.DatabaseRepository;
import com.saucelabs.mydemoapp.android.utils.base.BaseViewModel;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogViewModel extends BaseViewModel {
    private AppDatabase mDb;
    private DatabaseRepository repository;
    public MutableLiveData<List<ProductModel>> allProducts = new MutableLiveData<>();

    public ProductCatalogViewModel(Application app) {
        mDb = AppDatabase.getInstance(app);
        getAllProducts(MainActivity.NAME_ASC);
    }

    public void getAllProducts(int type) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
//                allProducts.postValue(mDb.personDao().getAllProducts());

                List<ProductModel> productList = new ArrayList<>();
                if (MainActivity.selectedSort == MainActivity.NAME_ASC) {
                    productList = mDb.personDao().getPersonsSortByAscName();
                } else if (MainActivity.selectedSort == MainActivity.NAME_DESC) {
                    productList = mDb.personDao().getPersonsSortByDescName();
                } else if (MainActivity.selectedSort == MainActivity.PRICE_ASC) {
                    productList = mDb.personDao().getPersonsSortByAscPrice();
                } else if (MainActivity.selectedSort == MainActivity.PRICE_DESC) {
                    productList = mDb.personDao().getPersonsSortByDescPrice();
                }

                allProducts.postValue(productList);

            }
        });
    }
}
