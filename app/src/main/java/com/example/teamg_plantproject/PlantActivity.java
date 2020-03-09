package com.example.teamg_plantproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class PlantActivity extends AppCompatActivity {

    protected TextView plantName;
    protected TextView plantType;
    protected TextView plantTemp;
    protected ProgressBar waterBar;
    protected ProgressBar humidityBar;
    protected ProgressBar sunBar;
    protected int plantID;
    protected DatabaseHelper db;
    private static final String TAG = "_Plant_Indiv";
    private final int soilMax = 3000;
    private final int solarMax = 2000;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference sensorDataRef = fb.collection("sensors/z1QgZ1bVjYnUyrszlU9b/data");

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
        waterBar.setProgress(0);
        humidityBar.setProgress(0);
        sunBar.setProgress(0);

        sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(MainActivity.class.getName(), "Listen failed.", e);
                            return;
                        }

                        String sensorDataText = "";

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("rawHumidity") != null) {

                                String i = Objects.requireNonNull(doc.get("rawSoilValue")).toString();
                                int ii = (int) Math.floor(Double.parseDouble(i));
                                String j = Objects.requireNonNull(doc.get("rawHumidity")).toString();
                                int jj = (int) Math.floor(Double.parseDouble(j));
                                String k = Objects.requireNonNull(doc.get("rawSolarValue")).toString();
                                int kk = (int) Math.floor(Double.parseDouble(k));
                                String l = Objects.requireNonNull(doc.get("rawTemp")).toString();
                                int ll = (int) Math.floor(Double.parseDouble(l));

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
        plantTemp = findViewById(R.id.temerature_i);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
