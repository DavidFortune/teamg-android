package com.example.teamg_plantproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlantActivity extends AppCompatActivity {

    protected TextView plantName;
    protected TextView plantType;
    protected ProgressBar waterBar;
    protected ProgressBar humidityBar;
    protected ProgressBar sunBar;
    protected int plantID;
    protected DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        setUpUI();

        Intent intent = getIntent();
        plantID = intent.getIntExtra("PlantID", 0);
        db = new DatabaseHelper(getApplicationContext());

        plantName.setText(db.getPlant(plantID).getPlantName());
        plantType.setText(db.getPlant(plantID).getPlantType());
        waterBar.setProgress(50);
        humidityBar.setProgress(10);
        sunBar.setProgress(90);

    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        return true;
    }

    protected void setUpUI() {

        plantName = findViewById(R.id.plant_name_i);
        plantType = findViewById(R.id.plant_type_i);
        waterBar = findViewById(R.id.water_progress_i);
        humidityBar = findViewById(R.id.humidity_progress_i);
        sunBar = findViewById(R.id.sunshine_progress_i);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
