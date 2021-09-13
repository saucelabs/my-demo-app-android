package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProductDetailViewModelFactory implements   ViewModelProvider.Factory {

    private Application mApplication;
    private String mParam;


    public ProductDetailViewModelFactory(Application application,String mParam) {
        mApplication = application;
        this.mParam = mParam;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ProductDetailViewModel(mApplication,mParam);
    }


}
