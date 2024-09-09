package com.saucelabs.mydemoapp.android.utils.base;

import static com.saucelabs.mydemoapp.android.utils.Constants.BLACK;
import static com.saucelabs.mydemoapp.android.utils.Constants.BLUE;
import static com.saucelabs.mydemoapp.android.utils.Constants.GRAY;
import static com.saucelabs.mydemoapp.android.utils.Constants.GREEN;
import static com.saucelabs.mydemoapp.android.utils.Constants.PINK;
import static com.saucelabs.mydemoapp.android.utils.Constants.RED;
import static com.saucelabs.mydemoapp.android.utils.Constants.VIOLET;
import static com.saucelabs.mydemoapp.android.utils.Constants.YELLOW;

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
        model.setTitle("Sauce Labs Bolt T-Shirt");
        model.setPrice(15.99);
        model.setColors(BLACK);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("Get your testing superhero on with the Sauce Labs bolt T-Shirt. From American Apparel, 100% ringspun combed cotton gray with red bolt.");
        model.setImageVal(R.drawable.ic_product2);
        colorModel = new ColorModel(R.drawable.ic_black_circle, BLACK);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Sauce Labs Fleece Jacket");
        model.setPrice(49.99);
        model.setColors(GRAY);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("Its not everyday that you come across a midweight quarter-zip fleece jacket capable of handling everythinh from a relaxing day outdoors to a busy day at the office.");
        model.setImageVal(R.drawable.ic_product3);
        colorModel = new ColorModel(R.drawable.ic_gray_circle, GRAY);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Sauce Labs Onesie");
        model.setPrice(7.99);
        model.setColors(BLACK);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.");
        model.setImageVal(R.drawable.ic_product1);

        colorModel = new ColorModel(R.drawable.ic_black_circle, BLACK);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_gray_circle, GRAY);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_green_circle, GREEN);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Sauce Labs Backpack");
        model.setPrice(29.99);
        model.setColors(GRAY);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        model.setImageVal(R.drawable.ic_product4);

        colorModel = new ColorModel(R.drawable.ic_black_circle, BLACK);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_blue_circle, BLUE);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_gray_circle, GRAY);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_green_circle, GREEN);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Sauce Labs Bike Light");
        model.setPrice(9.99);
        model.setColors(BLACK);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("A red light isn't the desire state in testing but it sure helps when riding your bike at night. Water-resistance with 3 lighting modes, 1 AAA battery included.");
        model.setImageVal(R.drawable.ic_product6);

        colorModel = new ColorModel(R.drawable.ic_black_circle, BLACK);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_gray_circle, GRAY);
        colorList.add(colorModel);

        colorModel = new ColorModel(R.drawable.ic_green_circle, GREEN);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Test.sllTheThings() T-Shirt");
        model.setPrice(15.99);
        model.setColors(BLACK);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
        model.setImageVal(R.drawable.ic_product5);

        colorModel = new ColorModel(R.drawable.ic_black_circle, BLACK);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        // Extra Colors
        model = new ProductModel();
        model.setTitle("Sauce Labs Bolt T-Shirt (blue)");
        model.setPrice(15.99);
        model.setColors(BLUE);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("Get your testing superhero on with the Sauce Labs bolt T-Shirt. From American Apparel, 100% ringspun combed cotton gray with red bolt.");
        model.setImageVal(R.drawable.ic_product2_blue);
        colorModel = new ColorModel(R.drawable.ic_black_circle, BLUE);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        model.setTitle("Sauce Labs Bolt T-Shirt (green)");
        model.setPrice(15.99);
        model.setColors(GREEN);
        model.setRating(3);
        model.setCurrency("$");
        model.setDesc("Get your testing superhero on with the Sauce Labs bolt T-Shirt. From American Apparel, 100% ringspun combed cotton gray with red bolt.");
        model.setImageVal(R.drawable.ic_product2_green);
        colorModel = new ColorModel(R.drawable.ic_black_circle, GREEN);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        model.setTitle("Sauce Labs Bolt T-Shirt (red)");
        model.setPrice(15.99);
        model.setColors(RED);
        model.setRating(3);
        model.setCurrency("$");
        model.setDesc("Get your testing superhero on with the Sauce Labs bolt T-Shirt. From American Apparel, 100% ringspun combed cotton gray with red bolt.");
        model.setImageVal(R.drawable.ic_product2_red);
        colorModel = new ColorModel(R.drawable.ic_black_circle, RED);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        model.setTitle("Sauce Labs Bolt T-Shirt (yellow)");
        model.setPrice(15.99);
        model.setColors(YELLOW);
        model.setRating(3);
        model.setCurrency("$");
        model.setDesc("Get your testing superhero on with the Sauce Labs bolt T-Shirt. From American Apparel, 100% ringspun combed cotton gray with red bolt.");
        model.setImageVal(R.drawable.ic_product2_red);
        colorModel = new ColorModel(R.drawable.ic_black_circle, YELLOW);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Test.allTheThings() T-Shirt (blue)");
        model.setPrice(15.99);
        model.setColors(BLUE);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
        model.setImageVal(R.drawable.ic_product5_blue);
        colorModel = new ColorModel(R.drawable.ic_black_circle, BLUE);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Test.allTheThings() T-Shirt (pink)");
        model.setPrice(15.99);
        model.setColors(PINK);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
        model.setImageVal(R.drawable.ic_product5_pink);
        colorModel = new ColorModel(R.drawable.ic_black_circle, PINK);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Test.allTheThings() T-Shirt (violet)");
        model.setPrice(15.99);
        model.setColors(VIOLET);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
        model.setImageVal(R.drawable.ic_product5_violet);
        colorModel = new ColorModel(R.drawable.ic_black_circle, VIOLET);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        model = new ProductModel();
        colorList = new ArrayList<>();
        model.setTitle("Test.allTheThings() T-Shirt (yellow)");
        model.setPrice(15.99);
        model.setColors(YELLOW);
        model.setRating(4);
        model.setCurrency("$");
        model.setDesc("This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.");
        model.setImageVal(R.drawable.ic_product5_yellow);
        colorModel = new ColorModel(R.drawable.ic_black_circle, YELLOW);
        colorList.add(colorModel);

        model.setColorList(colorList);
        list.add(model);

        viewModel.insertProducts(list);
//        insertProducts(mDb.personDao(), list);
    }
}
