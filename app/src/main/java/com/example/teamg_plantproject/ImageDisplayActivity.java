package com.example.teamg_plantproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ImageDisplayActivity extends AppCompatActivity {
    protected String path;
    protected TextView date;
    protected DatabaseHelper db;
    int pictureNumber;
    Image image;
    private int plantId;
    private String sensorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.imageView);
        date = findViewById(R.id.dateStamp);

        image = getIntent().getParcelableExtra("Image");
        plantId = getIntent().getIntExtra("PlantID", 0);

        sensorID = getIntent().getStringExtra("SensorID");
        Log.d("TAG", "onCreate sensorid: "+sensorID);
        imageView.setImageBitmap(image.getImage());

        date.setText(image.getImageDate());
        Log.d("TAG", "onCreate: IMAGE NUMBER: " + image.getImageNumber());
        pictureNumber = image.getImageNumber();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.individual_image_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_delete_image) {
            Intent intent = new Intent(this, ImageArchive.class);
            intent.putExtra("PlantID", plantId);
            intent.putExtra("SENSOR_ID", sensorID);
            db = new DatabaseHelper(getApplicationContext());
            db.deletePlantPicture(pictureNumber);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, ImageArchive.class);
        intent.putExtra("PlantID", plantId);
        intent.putExtra("SENSOR_ID", sensorID);
        this.startActivity(intent);
        return true;
    }
}
