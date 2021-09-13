package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductCatalogViewModelFactory implements   ViewModelProvider.Factory {

    private Application mApplication;
    private String mParam;


    public ProductCatalogViewModelFactory(Application application) {
        mApplication = application;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ProductCatalogViewModel(mApplication);
    }


}
