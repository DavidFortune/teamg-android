package com.example.teamg_plantproject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImageDisplayActivity extends AppCompatActivity {
    protected String path;
    protected TextView date;
    //protected Button deleteImgButton;
    protected String sensorID;
    protected DatabaseHelper db;
     Image image;
    String image_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        //deleteImgButton = findViewById(R.id.delete_img_button);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        date = findViewById(R.id.dateStamp);

        image=    getIntent().getExtras().getParcelable("Image");
//do anything with data
      //  image = getIntent().getParcelableExtra("Image");
        image_date = getIntent().getStringExtra("Date");
        sensorID = getIntent().getStringExtra("SensorID");

        imageView.setImageBitmap(image.getImage());
        date.setText(image_date);

        /*deleteImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 //Deleting records from database

                db = new DatabaseHelper(getApplicationContext());
                db.deletePlantPicture(sensorID);

                //after deleting data go to main page
                Intent intent = new Intent(ImageDisplayActivity.this, ImageArchive.class);
                startActivity(intent);
                finish();
            }
        });*/
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, ImageArchive.class);
        this.startActivity(intent);
        return true;
    }
}
