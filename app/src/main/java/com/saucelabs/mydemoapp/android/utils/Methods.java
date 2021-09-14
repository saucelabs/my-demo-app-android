package com.saucelabs.mydemoapp.android.utils;

import android.app.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.DialogReviewBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnDialogCallBack;
import com.saucelabs.mydemoapp.android.model.CartItemModel;
import com.saucelabs.mydemoapp.android.view.activities.MainActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Methods extends Constants {
    //    public EditText inputET;

    public void startActivity(Activity mAc, Class aClass, int status) {
        if (mAc.isFinishing())
            return;
        if (status == START_ACTIVITY) {
            mAc.startActivity(new Intent(mAc, aClass));
        } else if (status == START_ACTIVITY_WITH_FINISH) {
            mAc.startActivity(new Intent(mAc, aClass));
            mAc.finish();
        } else if (status == START_ACTIVITY_WITH_CLEAR_BACK_STACK) {
            mAc.startActivity(new Intent(mAc, aClass).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (status == START_ACTIVITY_WITH_TOP) {
            mAc.startActivity(new Intent(mAc, aClass).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
//        mAc.overridePendingTransition(0, 0);
//        Animatoo.animateFade(mAc);
    }

    public void startActivityWithDataBundle(Activity mAc, Class aClass, Bundle bundle, int status) {
        if (mAc.isFinishing())
            return;
        if (status == START_ACTIVITY) {
            mAc.startActivity(new Intent(mAc, aClass).putExtras(bundle));
        } else if (status == START_ACTIVITY_WITH_FINISH) {
            mAc.startActivity(new Intent(mAc, aClass).putExtras(bundle));
            mAc.finish();
        } else if (status == START_ACTIVITY_WITH_CLEAR_BACK_STACK) {
            mAc.startActivity(new Intent(mAc, aClass).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (status == START_ACTIVITY_WITH_TOP) {
            mAc.startActivity(new Intent(mAc, aClass).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
//        mAc.overridePendingTransition(0, 0);
//        Animatoo.animateFade(mAc);
    }

    public void finishActivity(Activity mAc) {
        mAc.finish();
        mAc.overridePendingTransition(0, 0);
//        Animatoo.animateSplit(mAc);
    }

    public Bitmap flip(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public void hideKeyboard(Activity mAc) {
        try {
            InputMethodManager imm = (InputMethodManager) mAc.getSystemService(INPUT_METHOD_SERVICE);
            if (imm == null) throw new AssertionError("assertion Error! Imm is null");
            imm.hideSoftInputFromWindow(Objects.requireNonNull(mAc.getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public boolean isValidPassword(String passwordStr) {
        if (passwordStr != null && passwordStr.length() >= 6) {
            return true;
        }

        return false;
    }

    public boolean isEqual(String str1, String str2) {
        if (str1 != null && str2 != null && str1.equals(str2)) {
            return true;
        }

        return false;
    }

    public boolean isEmptyField(String str) {
        if (TextUtils.isEmpty(str)) return false;
        return true;
    }

    public void openURL(Activity mAct, String url) {
        if (mAct.isFinishing())
            return;
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "http://" + url;
        }

        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mAct.startActivity(openUrlIntent);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getImage(byte[] imgByte) {
        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);

    }

    public void startMainActivity(Activity mAc, Bundle bundle) {

        mAc.startActivity(new Intent(mAc, MainActivity.class).putExtras(bundle));
        mAc.overridePendingTransition(0, 0);
//        ((MainActivity) mAc).handleBundle(bundle);
//        Animatoo.animateFade(mAc);
    }

    public Bundle getBundle(int reqFrag, int selectedTab) {
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_FRAGMENT, reqFrag);
        bundle.putInt(SELECTED_TAB, selectedTab);

        return bundle;
    }

    public int getTotalNum() {
        SingletonClass ST = SingletonClass.getInstance();
        int totalNumOfProducts = 0;
        for (CartItemModel model : ST.cartItemList) {
            totalNumOfProducts = totalNumOfProducts + model.getNumberOfProduct();
        }
        return totalNumOfProducts;
    }

    public void showDialog(final OnDialogCallBack onOkClickRCB,Activity mActivity, String title, String message, String okButtonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity,R.style.MyDialogTheme);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                    if (onOkClickRCB != null)
//                        onOkClickRCB.OnDialogCallBack(false);
//                    dialog.dismiss();
//                    hideKeyboard();
//                })
                .setPositiveButton(okButtonText, (dialog, id) -> {
                    if (onOkClickRCB != null)
                        onOkClickRCB.OnDialogCallBack(true);
                    dialog.dismiss();
                    hideKeyboard(mActivity);
                });
        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (WindowManager.BadTokenException ex) {
            ex.printStackTrace();
        }
    }


    public void showDialog(OnDialogCallBack onOkClickRCB,Activity mActivity, String title, String message,
                           String okButtonText, String cancelButtonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(cancelButtonText, (dialog, which) -> {
                    if (onOkClickRCB != null)
                        onOkClickRCB.OnDialogCallBack(false);
                    dialog.dismiss();
                    hideKeyboard(mActivity);
                })
                .setPositiveButton(okButtonText, (dialog, id) -> {
                    if (onOkClickRCB != null)
                        onOkClickRCB.OnDialogCallBack(true);
                    dialog.dismiss();
                    hideKeyboard(mActivity);
                });
        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (WindowManager.BadTokenException ex) {
            ex.printStackTrace();
        }
    }

    public void showDialog(OnDialogCallBack onOkClickRCB,Activity mActivity, String title, String message,
                           String okButtonText, String cancelButtonText, String neutralButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(neutralButton, (dialog, i) -> {
                    dialog.dismiss();
                    hideKeyboard(mActivity);
                })
                .setNegativeButton(cancelButtonText, (dialog, which) -> {
                    if (onOkClickRCB != null)
                        onOkClickRCB.OnDialogCallBack(false);
                    dialog.dismiss();
                    hideKeyboard(mActivity);
                })
                .setPositiveButton(okButtonText, (dialog, id) -> {
                    if (onOkClickRCB != null)
                        onOkClickRCB.OnDialogCallBack(true);
                    dialog.dismiss();
                    hideKeyboard(mActivity);
                });
        AlertDialog alert = builder.create();
        try {
            alert.show();
        } catch (WindowManager.BadTokenException ex) {
            ex.printStackTrace();
        }
    }


    public double getTotalPrice(List<CartItemModel> cartItemList) {
        double totalPrice = 0;
        for (CartItemModel model : cartItemList) {
            totalPrice = totalPrice + (model.getNumberOfProduct() * model.getProductModel().getPrice());
        }

        return totalPrice;
    }

    public void showReviewDialog(Activity mAct) {
        final Dialog dialog = new Dialog(mAct);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        DialogReviewBinding reviewBinding = DataBindingUtil.inflate(LayoutInflater.from(mAct), R.layout.dialog_review, null, false);
        dialog.setContentView(reviewBinding.getRoot());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = (int) (mAct.getResources().getDisplayMetrics().widthPixels * 0.80);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        int width = (int) (mAct.getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (mAct.getResources().getDisplayMetrics().heightPixels * 0.90);

        dialog.getWindow().setAttributes(lp);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        };

        reviewBinding.closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}
