package com.saucelabs.mydemoapp.android.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "Product")
public class ProductModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    double price;
    int rating;
    int colors;
    String desc;
    String currency;
    byte[] image;
    int imageVal;
    @TypeConverters(ColorModelConverters.class)
    List<ColorModel> colorList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getImageVal() {
        return imageVal;
    }

    public void setImageVal(int imageVal) {
        this.imageVal = imageVal;
    }

    public List<ColorModel> getColorList() {
        return colorList;
    }

    public void setColorList(List<ColorModel> colorList) {
        this.colorList = colorList;
    }
}
