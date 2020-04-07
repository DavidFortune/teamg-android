package com.example.teamg_plantproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;

public class PlantType_Custom extends AppCompatActivity {
    protected static final String TAG = "_PLANT TYPE CUSTOM";
    private static final String KEY_PLANTTYPE = "planttype_key";
    private static final String KEY_AIRHUMIDITY = "airhumidity_key";
    private static final String KEY_AIRTEMPERATURE = "airtemperature_key";
    private static final String KEY_SOILMOISTURE = "soilmoisture_key";
    private TextView mrUser;
    private EditText editPlantType;
    private EditText editAirHumidity;
    private EditText editAirTemperature;
    private EditText editSoilMoisture;
    private Button buttonShowType;
    private Button buttonCancelType;
    private Button buttonConfirmType;
    private TextView textViewPlantType;
    private TextView textViewAirHumidity;
    private TextView textViewAirTemperature;
    private TextView textViewSoilMoisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_type__custom);

        mrUser = findViewById(R.id.user_welcome);
        buttonShowType = findViewById(R.id.show_new_plant_type);
        buttonCancelType = findViewById(R.id.cancel_new_plant_type);
        buttonConfirmType = findViewById(R.id.confirm_goback);
        editPlantType = findViewById(R.id.edit_new_plant_type);
        editAirHumidity = findViewById(R.id.edit_new_air_humidity);
        editAirTemperature = findViewById(R.id.edit_new_air_temperature);
        editSoilMoisture = findViewById(R.id.edit_new_soil_moisture);
        textViewPlantType = findViewById(R.id.tv_new_plant_type);
        textViewAirHumidity = findViewById(R.id.tv_new_air_humidity);
        textViewAirTemperature = findViewById(R.id.tv_new_air_temperature);
        textViewSoilMoisture = findViewById(R.id.tv_new_soil_moisture);


        if (savedInstanceState != null) {
            String savedPlantType = savedInstanceState.getString(KEY_PLANTTYPE);
            textViewPlantType.setText(savedPlantType);

            String savedAirHumidity = savedInstanceState.getString(KEY_AIRHUMIDITY);
            textViewAirHumidity.setText(savedAirHumidity);

            String savedAirTemperature = savedInstanceState.getString(KEY_AIRTEMPERATURE);
            textViewAirTemperature.setText(savedAirTemperature);

            String savedSoilMoisture = savedInstanceState.getString(KEY_SOILMOISTURE);
            textViewSoilMoisture.setText(savedSoilMoisture);
        } else {
            Toast.makeText(this, "Welcome to Add Your New Plant Type", Toast.LENGTH_SHORT).show();
        }

        buttonShowType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(editPlantType.getText().toString())
                        || TextUtils.isEmpty(editAirHumidity.getText().toString())
                        || TextUtils.isEmpty(editAirTemperature.getText().toString())
                        || TextUtils.isEmpty(editSoilMoisture.getText().toString())) {
                    Toast.makeText(PlantType_Custom.this, "Empty Input Is Not Allowed",
                            Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(PlantType_Custom.this, "New Plant Type Info Is Edited",
                            Toast.LENGTH_LONG).show();
                    textViewPlantType.setText("Added Plant Type is:  "
                            + editPlantType.getText().toString() + " ;");
                    textViewAirHumidity.setText("Added Air Humidity is:  "
                            + editAirHumidity.getText().toString() + " % ;");
                    textViewAirTemperature.setText("Added Air Temperature is:  "
                            + editAirTemperature.getText().toString() + " degrees ;");
                    textViewSoilMoisture.setText("Added Soil Moisture is:  "
                            + editSoilMoisture.getText().toString() + " % ;");
                }
            }
        });

        buttonCancelType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editPlantType.getText().toString())
                        && TextUtils.isEmpty(editAirHumidity.getText().toString())
                        && TextUtils.isEmpty(editAirTemperature.getText().toString())
                        && TextUtils.isEmpty(editSoilMoisture.getText().toString())) {
                    finish();
                } else {
                    editPlantType.getText().clear();
                    editAirHumidity.getText().clear();
                    editAirTemperature.getText().clear();
                    editSoilMoisture.getText().clear();
                    Toast.makeText(PlantType_Custom.this, "Input Data Erased; " +
                                    "Click Again to Return to the Last Page",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonConfirmType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(editPlantType.getText().toString())
                        || TextUtils.isEmpty(editAirHumidity.getText().toString())
                        || TextUtils.isEmpty(editAirTemperature.getText().toString())
                        || TextUtils.isEmpty(editSoilMoisture.getText().toString())) {
                    Toast.makeText(PlantType_Custom.this, "Please Click CANCEL to Go Back",
                            Toast.LENGTH_LONG).show();
                } else {

                    String plantTypeName = editPlantType.getText().toString();
                    String airHumidity = editAirHumidity.getText().toString();
                    String airTemperature = editAirTemperature.getText().toString();
                    String soilMoisture = editSoilMoisture.getText().toString();

                    //  Log.d("TAG", "onClick SOIL MOISTURE: "+soilMoisture);

                    DBHelper_PlantType dbHelper_plantType;
                    dbHelper_plantType = new DBHelper_PlantType(PlantType_Custom.this);
                    PlantType plantType = new PlantType();

                    plantType.setPlantType(plantTypeName);
                    int intAirH = Integer.parseInt(airHumidity);
                    plantType.setAirHumidity(intAirH);
                    int intAirT = Integer.parseInt(airTemperature);
                    plantType.setAirTemperature(intAirT);
                    int intSoilM = Integer.parseInt(soilMoisture);
                    plantType.setSoilMoisture(intSoilM);

                    dbHelper_plantType.createType(plantType);
                    //  Log.d(TAG, "onClick: " + dbHelper_plantType.getType(1));
                    Log.d("TAG", "onClick: FINISHED CUSTOM");
                    Intent intent = new Intent();
                    intent.putExtra("selectedChoice", plantTypeName);
                    // return the one we made so we can set it as our current selection
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();
                }
            }
        });
    }
}