package com.example.teamg_plantproject;

import android.graphics.Bitmap;

public class Image {

    private String date;
    private Bitmap image;

    public Image() {
    }

    public Image(String date, Bitmap image) {
        this.date = date;
        this.image = image;
    }

    public String getImageDate() {
        return date;
    }

    public void setImageDate(String date) {
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
