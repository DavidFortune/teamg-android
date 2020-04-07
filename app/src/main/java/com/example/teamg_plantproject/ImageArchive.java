package com.example.teamg_plantproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImageArchive extends AppCompatActivity {

    private static final String TAG = "My Activity";
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    protected Button addImageButton;
    protected DatabaseHelper db;
    protected String sensorID;
    protected int PlantID;
    protected GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_archive);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addImageButton = findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageArchive.this);
                builder.setTitle("Select image from");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Camera")) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        } else if (items[which].equals("Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser
                                    (intent, "Select file"), PICK_FROM_GALLERY);
                        } else if (items[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        sensorID = getIntent().getStringExtra("SENSOR_ID");
        PlantID = getIntent().getIntExtra("PlantID", 0);

        Log.d(TAG, "Archive: PLANT ID" + PlantID);
        db = new DatabaseHelper(getApplicationContext());
        Log.d(TAG, "onCreate, sensor ID: " + sensorID);

        gridView = findViewById(R.id.gridView);

        // plantPictures = db.getArchivePictures(sensorID);
        ArrayList<Image> images = new ArrayList<>();
        Log.d(TAG, "ARCHIVE sensorID :"+sensorID);
        images = db.getAllImages(sensorID);

        ImageAdapter imageAdapter = new ImageAdapter(this, images, sensorID, PlantID);
        gridView.setAdapter(imageAdapter);

    }   //end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "in onActivity for Result");
        super.onActivityResult(requestCode, resultCode, data);
        //db = new DatabaseHelper(getApplicationContext());
        SimpleDateFormat s = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss");
        String format = s.format(new Date());
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Log.d("TAG", "onActivityResult: getting there");
                Bundle bundle = data.getExtras();
                Bitmap myImage = bundle.getParcelable("data");
                // convert bitmap to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageInByte = stream.toByteArray();


                //Adding plant picture
                db.addPlantPicture(imageInByte, sensorID, format);
                Log.d(TAG, "onActivityResult: inserted picture");
                Log.d(TAG, "onActivityResult: sensor ID: " + sensorID + "date " + format);
                Intent i = new Intent(ImageArchive.this, ImageArchive.class);
                i.putExtra("PlantID", PlantID);
                startActivity(i);
                finish();
            }

            if (requestCode == PICK_FROM_GALLERY) {
                    /*Uri imageUri = data.getData();
                    Bitmap myImage = null;
                    try
                    {
                        myImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }*/
                Uri IMAGE_URI = data.getData();
                InputStream image_stream = null;
                try {
                    image_stream = getContentResolver().openInputStream(IMAGE_URI);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap myImage = BitmapFactory.decodeStream(image_stream, null, options);
                //Bundle bundle = data.getExtras();
                //Bitmap myImage = bundle.getParcelable("data");
                // convert bitmap to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                db.addPlantPicture(imageInByte, sensorID, format);
                Log.d(TAG, "Added picture from gallery");
                Intent i = new Intent(ImageArchive.this, ImageArchive.class);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, PlantActivity.class);
        intent.putExtra("PlantID", PlantID);
        this.startActivity(intent);
        return true;
    }
}


