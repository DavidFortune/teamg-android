package com.example.teamg_plantproject;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDisplayActivity extends AppCompatActivity {
    protected String path;
    protected TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        date = findViewById(R.id.dateStamp);
        SimpleDateFormat s = new SimpleDateFormat("MMM-dd-yyyy");
        String format = s.format(new Date());
        date.setText("Created on " + format);

        // Get Image Path
        path = getIntent().getExtras().getString("path");
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        // Get Image
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions. inJustDecodeBounds = false;
        bmOptions. inSampleSize = 4;
        bmOptions. inPurgeable = true ;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        // Display Image
        imageView.setImageBitmap(bitmap);
    }
}
