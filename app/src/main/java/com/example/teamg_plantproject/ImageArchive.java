package com.example.teamg_plantproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

public class ImageArchive extends AppCompatActivity {

    protected Button addImageButton;
    protected ImageView image;
    protected TextView timeStamp;

    protected DatabaseHelper db;
    protected int plantID;

    private static final int IMAGE_CAPTURE_CODE = 1001;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_archive);

        addImageButton = findViewById(R.id.add_image_button);
        image = findViewById(R.id.imageView2);
        timeStamp = findViewById(R.id.date_textView);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        //if (!(db.getImage(plantID) == null))
         //   image.setImageBitmap(db.getImage(plantID));

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image is captured by camera
        super.onActivityResult(requestCode, resultCode, data);
        db = new DatabaseHelper(getApplicationContext());
        if (resultCode == RESULT_OK) {
            //set the image capture to our ImageView
            image.setDrawingCacheEnabled(true);
            image.buildDrawingCache();
            image.setImageURI(image_uri);
            Bitmap bitmap = image.getDrawingCache();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] dataMy = byteArrayOutputStream.toByteArray();
            db.addImage(dataMy, plantID);

            long date = System.currentTimeMillis();

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            String dateString = sdf.format(date);
            timeStamp.setText(dateString);
        }
    }
}
