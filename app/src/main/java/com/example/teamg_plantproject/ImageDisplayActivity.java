package com.example.teamg_plantproject;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDisplayActivity extends AppCompatActivity {
    protected String path;
    protected TextView date;
    protected Button deleteImgButton;
    protected int plantId;
    Bitmap theImage;
    int imageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        deleteImgButton = findViewById(R.id.delete_img_button);

        date = findViewById(R.id.dateStamp);
        SimpleDateFormat s = new SimpleDateFormat("MMM-dd-yyyy");
        String format = s.format(new Date());
        date.setText("Created on " + format);

        // Get Image Path
        //path = getIntent().getExtras().getString("path");
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Intent intnt = getIntent();
        imageView = (ImageView) intnt.getParcelableExtra("imagename");
        imageId = intnt.getIntExtra("imageid", 20);
        imageView.setImageBitmap(theImage);

        // Get Image
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions. inJustDecodeBounds = false;
        bmOptions. inSampleSize = 4;
        bmOptions. inPurgeable = true ;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        // Display Image
        imageView.setImageBitmap(bitmap);

        deleteImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 //create DatabaseHandler object

                DatabaseHelper db = new DatabaseHelper(ImageDisplayActivity.this);
                 //Deleting records from database
                db.deletePlantPicture(plantId);
                // /after deleting data go to main page
                Intent intent = new Intent(ImageDisplayActivity.this, ImageArchive.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
