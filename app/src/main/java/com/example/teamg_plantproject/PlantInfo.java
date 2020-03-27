package com.example.teamg_plantproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PlantInfo extends AppCompatActivity {
    private static final String TAG = "_Pinterest";
    protected int plantID;
    protected DatabaseHelper db;
    private String plantType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);

        Intent intent = getIntent();
        plantID = intent.getIntExtra("PlantID", 0);
        Log.d(TAG, "onCreate: " + plantID);
        db = new DatabaseHelper(this);
        plantType = db.getPlant(plantID).getPlantType();

        Log.d(TAG, "onCreate: " + plantType);

        String url = "https://www.google.com/search?q=" + plantType;

        WebView webView = findViewById(R.id.web_view);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, PlantActivity.class);
        intent.putExtra("PlantID", plantID);
        this.startActivity(intent);
        return true;
    }

}
