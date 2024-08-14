package com.saucelabs.mydemoapp.android.viewModel;

import android.app.Application;
import android.app.Fragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;

import com.saucelabs.mydemoapp.android.database.AppDatabase;
import com.saucelabs.mydemoapp.android.database.AppExecutors;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.DatabaseRepository;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.saucelabs.mydemoapp.android.utils.base.BaseViewModel;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

                // Alter prices if needed
                SingletonClass ST = SingletonClass.getInstance();
                if (ST.hasVisualChanges) {
                    productList = generateVisualChanges(productList);
                }
                allProducts.postValue(productList);
            }
        });
    }

    private List<ProductModel> generateVisualChanges(List<ProductModel> productList) {
        Random random = new Random();

        // Replaces prices by Random ones
        for (int i = 0; i < productList.size(); i++) {
            double randomPrice = 1 + (100 - 1) * random.nextDouble();
            randomPrice = (double) Math.round(randomPrice * 100) / 100;
            productList.get(i).setPrice(randomPrice);
        }

        // Replace 2 first item by Onesie image.
        ProductModel onesie = findProductByName(productList, "Sauce Labs Onesie");
        productList.get(0).setImage(onesie.getImage());
        productList.get(0).setImageVal(onesie.getImageVal());
        productList.get(1).setImage(onesie.getImage());
        productList.get(1).setImageVal(onesie.getImageVal());
        return productList;
    }

    private ProductModel findProductByName(List<ProductModel> productList, String name) {
        for (ProductModel product: productList) {
            if (product.getTitle().equals(name)) {
                return product;
            }
        }
        return null;
    }
}
