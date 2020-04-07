package com.example.teamg_plantproject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
    private String date;
    private Bitmap image;
    private int imageNumber;

    public Image() {
    }

    public Image(String date, Bitmap image, int imageNumber) {
        this.date = date;
        this.image = image;
        this.imageNumber = imageNumber;
    }

    protected Image(Parcel in) {
        date = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        imageNumber = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeParcelable(image, flags);
        dest.writeInt(imageNumber);
    }
}
