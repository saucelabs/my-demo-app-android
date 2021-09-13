package com.saucelabs.mydemoapp.android.model;

public class ColorModel {
    private int colorImg;
    private int colorValue  ;

    public ColorModel(int colorImg , int colorValue){
        this.colorImg = colorImg;
        this.colorValue = colorValue;
    }

    public int getColorImg() {
        return colorImg;
    }

    public void setColorImg(int colorImg) {
        this.colorImg = colorImg;
    }

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }
}
