package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;
import android.os.Handler;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.DatabaseRepository;
import com.saucelabs.mydemoapp.android.utils.base.BaseViewModel;

import java.util.List;

public class SplashViewModel extends BaseViewModel {
    public MutableLiveData<Integer> pb;
    private AppDatabase mDb;
    private DatabaseRepository repository;
    public MutableLiveData<List<ProductModel>> allProducts = new MutableLiveData<>();

    public SplashViewModel(Application app) {
        this.pb = new MutableLiveData<>();
        pb.setValue(View.VISIBLE);
//        repository = new DatabaseRepository(app);
//        allNotes = repository.getAllNotes();
        mDb = AppDatabase.getInstance(app);
        getAllProducts();
    }

    public void addDelays() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb.setValue(View.GONE);
            }
        }, 1000);
    }

    public void getAllProducts() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                allProducts.postValue(mDb.personDao().getAllProducts());
            }
        });
    }

    public void insertProducts(List<ProductModel> list) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.personDao().insertProduct(list);
                pb.postValue(View.GONE);
            }
        });
    }
}
