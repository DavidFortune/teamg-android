package com.example.teamg_plantproject;

import android.graphics.Bitmap;

public class Image {

    private String date;
    private Bitmap image;
    private Integer image_number;

    public Image() {
    }

    public Image(String date, Bitmap image, int image_number) {
        this.date = date;
        this.image = image;
        this.image_number = image_number;
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

    public Integer getImageNumber() {
        return image_number;
    }

    public void setImage_number(Integer image_number) {
        this.image_number = image_number;
    }

}
