package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SplashViewModelFactory implements   ViewModelProvider.Factory {
//    private static final String DEFAULT_LIMIT = "4";
//    static Application application;
//    static String created="ViewModelFactorySuccess";
//
//    public SplashViewModelFactory(Application application, String created) {
//
//        this.application=application;
//
//    }
//
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        try {
//            return modelClass.getConstructor(SplashViewModel.class, int.class)
//                    .newInstance(application, created);
//        } catch (NoSuchMethodException | IllegalAccessException |
//                InstantiationException | InvocationTargetException e) {
//            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
//        }
//    }

    private Application mApplication;
    private String mParam;


    public SplashViewModelFactory(Application application) {
        mApplication = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new SplashViewModel(mApplication);
    }


}
