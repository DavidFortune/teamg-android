package com.example.teamg_plantproject;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class PlantActivity extends AppCompatActivity {

    protected TextView plantName;
    protected TextView plantType;
    protected TextView plantTemp;

    protected ImageView plantPicture;
    protected Button deletePlant;
    protected Button takePictureButton;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001 ;
    Uri image_uri;

    protected ProgressBar waterBar;
    protected ProgressBar humidityBar;
    protected ProgressBar sunBar;
    protected int plantID;
    protected DatabaseHelper db;
    private static final String TAG = "_Plant_Indiv";
    protected String plantSensorID;
    private final int soilMax = 3000;
    private final int solarMax = 2000;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference sensorDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        setUpUI();

        plantPicture = findViewById(R.id.plant_image_i);
        takePictureButton = findViewById(R.id.capture_image_btn);

        //button click listener
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system is >= Marshmallow, request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //permission not enabled, request it
                        String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request user permission
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permission is already granted
                        openCamera();
                    }
                }
                else {
                    //system < marshmallow
                    openCamera();
                }
            }
        });

        Intent intent = getIntent();
        plantID = intent.getIntExtra("PlantID", 0);
        db = new DatabaseHelper(getApplicationContext());
        plantSensorID = db.getPlant(plantID).getSensorId();
        sensorDataRef = fb.collection("sensors/" + plantSensorID + "/data");


        plantName.setText(db.getPlant(plantID).getPlantName());
        plantType.setText(db.getPlant(plantID).getPlantType());
        waterBar.setProgress(25);
        sunBar.setProgress(50);
        humidityBar.setProgress(75);
        if (!(db.getImage(plantID) == null))
            plantPicture.setImageBitmap(db.getImage(plantID));


        sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(MainActivity.class.getName(), "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("rawHumidity") != null) {

                                String i = Objects.requireNonNull(doc.get("rawSoilValue")).toString();
                                int ii = (int) Math.floor(Double.parseDouble(i));
                                String j = Objects.requireNonNull(doc.get("rawHumidity")).toString();
                                int jj = (int) Math.floor(Double.parseDouble(j));
                                String k = Objects.requireNonNull(doc.get("rawSolarValue")).toString();
                                int kk = (int) Math.floor(Double.parseDouble(k));
                                String l = Objects.requireNonNull(doc.get("rawTemp")).toString();

                                waterBar.setProgress((ii * 100) / soilMax);
                                Log.d(TAG, "onEvent: " + ((ii * 100) / soilMax));
                                humidityBar.setProgress(jj);
                                Log.d(TAG, "onEvent: " + (jj));
                                sunBar.setProgress((kk * 100) / solarMax);
                                Log.d(TAG, "onEvent: " + ((kk * 100) / solarMax));
                                plantTemp.setText(l + "*C");
                                Log.d(TAG, "onEvent: " + l);

                            }
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        deletePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletePlant(plantID);
                goToPlantList();
            }
        });
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
            plantPicture.setDrawingCacheEnabled(true);
            plantPicture.buildDrawingCache();
            plantPicture.setImageURI(image_uri);
            Bitmap bitmap = plantPicture.getDrawingCache();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] dataMy = byteArrayOutputStream.toByteArray();
            db.addImage(dataMy, plantID);
        }
    }

    //to handle permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called when the user presses allow or deny from Permission request popup
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    openCamera();
                }
                else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        return true;
    }

    protected void setUpUI() {

        plantName = findViewById(R.id.plant_name_i);
//        plantType = findViewById(R.id.plant_type_i);
        waterBar = findViewById(R.id.water_progress_i);
        humidityBar = findViewById(R.id.humidity_progress_i);
        sunBar = findViewById(R.id.sunshine_progress_i);
        plantTemp = findViewById(R.id.temperature_i);
        deletePlant = findViewById(R.id.delete_plant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void goToPlantList() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
