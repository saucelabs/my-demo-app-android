package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.DatabaseRepository;
import com.saucelabs.mydemoapp.android.utils.base.BaseViewModel;

public class ProductDetailViewModel extends BaseViewModel {
    private AppDatabase mDb;
    private DatabaseRepository repository;
    String id;
    public MutableLiveData<ProductModel> product = new MutableLiveData<>();

    public ProductDetailViewModel(Application app,String id) {
        mDb = AppDatabase.getInstance(app);
        this.id = id;
        getProduct();
    }

    public void getProduct() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                product.postValue(mDb.personDao().getProduct(Integer.parseInt(id)));


            }
        });
    }



}
