package com.saucelabs.mydemoapp.android.utils.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.saucelabs.mydemoapp.android.MyApplication;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.model.ColorModel;
import com.saucelabs.mydemoapp.android.model.ProductModel;
import com.saucelabs.mydemoapp.android.utils.SingletonClass;
import com.google.gson.Gson;
import com.saucelabs.mydemoapp.android.viewModel.SplashViewModel;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public Activity mAct = BaseActivity.this;
    public MyApplication app;
    public SingletonClass ST;
    public String DEFAULT_BROADCAST = "NOTIFICATION_BROADCAST";

    public Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBaseActivity();
    }

    private void initBaseActivity() {
        app = (MyApplication) getApplication();
        ST = SingletonClass.getInstance();
        gson = new Gson();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent != null && intent.getAction().equals(DEFAULT_BROADCAST)) {

                }
            } catch (Exception ignored) {
//                Log.d("roadVast", ignored.getLocalizedMessage());
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    protected void onBackPressedDefault() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        if (receiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter(DEFAULT_BROADCAST));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void populateProductsDb(SplashViewModel viewModel) {
        List<ProductModel> list = new ArrayList<>();
        List<ColorModel> colorList = new ArrayList<>();
        ColorModel colorModel;

        ProductModel model = new ProductModel();
        model.setTitle("Sauce Lab Bolt T-Shirt");
        model.setPrice(15.99);
        model.setColors(3);
        model.setRating(4);
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
        model.setRating(4);
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
        model.setRating(4);
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
        model.setRating(4);
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
        model.setRating(4);
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
        model.setRating(4);
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
}
