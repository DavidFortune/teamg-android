package com.example.teamg_plantproject;

import android.graphics.Bitmap;

public class Image {

    private String date;
    private Bitmap image;
    private int imageNumber;

    public Image() {
    }

    public Image(String date, Bitmap image,int imageNumber) {
        this.date = date;
        this.image = image;
        this.imageNumber = imageNumber;
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

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

}
