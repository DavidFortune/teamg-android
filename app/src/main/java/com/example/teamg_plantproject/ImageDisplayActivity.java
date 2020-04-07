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
    protected String sensorID;
    protected DatabaseHelper db;
    int pictureNumber;
    Image image;
    String image_date;
    private int plantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.imageView);
        date = findViewById(R.id.dateStamp);

        image = getIntent().getParcelableExtra("Image");
        plantId = getIntent().getIntExtra("PlantID", 0);
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
            db = new DatabaseHelper(getApplicationContext());
            db.deletePlantPicture(pictureNumber);
            //after deleting data go to main page
            Intent intent = new Intent(this, ImageArchive.class);
            System.out.println(plantId + "++++++++++++++++++++++++++++" + "delete");
//            intent.putExtra("PlantId", plantId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, ImageArchive.class);
        intent.putExtra("PlantID", plantId);
        System.out.println(plantId + "++++++++++++++++++++++++++++" + "back");
        this.startActivity(intent);
        return true;
    }
}
