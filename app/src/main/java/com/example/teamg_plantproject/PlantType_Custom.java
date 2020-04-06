package com.example.teamg_plantproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlantType_Custom extends AppCompatActivity{
    private TextView mrUser;
    private EditText editPlantType;
    private EditText editAirHumidity;
    private EditText editAirTemperature;
    private EditText editSoilMoisture;
    private Button buttonSaveType;
    private Button buttonCancelType;
    private Button buttonConfirmType;
    private TextView textViewPlantType;
    private TextView textViewAirHumidity;
    private TextView textViewAirTemperature;
    private TextView textViewSoilMoisture;
    private static final String KEY_PLANTTYPE = "planttype_key";
    private static final String KEY_AIRHUMIDITY = "airhumidity_key";
    private static final String KEY_AIRTEMPERATURE = "airtemperature_key";
    private static final String KEY_SOILMOISTURE = "soilmoisture_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_type__custom);

            mrUser = (TextView) findViewById(R.id.user_welcome);
            buttonSaveType = (Button) findViewById(R.id.save_new_plant_type);
            buttonCancelType = (Button) findViewById(R.id.cancel_new_plant_type);
            buttonConfirmType = (Button) findViewById(R.id.confirm_goback);
            editPlantType = (EditText) findViewById(R.id.edit_new_plant_type);
            editAirHumidity = (EditText) findViewById(R.id.edit_new_air_humidity);
            editAirTemperature = (EditText) findViewById(R.id.edit_new_air_temperature);
            editSoilMoisture = (EditText) findViewById(R.id.edit_new_soil_moisture);
            textViewPlantType = (TextView) findViewById(R.id.tv_new_plant_type);
            textViewAirHumidity = (TextView) findViewById(R.id.tv_new_air_humidity);
            textViewAirTemperature = (TextView) findViewById(R.id.tv_new_air_temperature);
            textViewSoilMoisture = (TextView) findViewById(R.id.tv_new_soil_moisture);


        if (savedInstanceState != null){
            String savedPlantType = savedInstanceState.getString(KEY_PLANTTYPE);
            textViewPlantType.setText(savedPlantType);

            String savedAirHumidity = savedInstanceState.getString(KEY_AIRHUMIDITY);
            textViewAirHumidity.setText(savedAirHumidity);

            String savedAirTemperature = savedInstanceState.getString(KEY_AIRTEMPERATURE);
            textViewAirTemperature.setText(savedAirTemperature);

            String savedSoilMoisture = savedInstanceState.getString(KEY_SOILMOISTURE);
            textViewSoilMoisture.setText(savedSoilMoisture);
        } else {
            Toast.makeText(this, "New Entry", Toast.LENGTH_SHORT).show();
        }

        buttonSaveType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(editPlantType.getText().toString())
                        || TextUtils.isEmpty(editAirHumidity.getText().toString())
                        || TextUtils.isEmpty(editAirTemperature.getText().toString())
                        || TextUtils.isEmpty(editSoilMoisture.getText().toString())) {
                    Toast.makeText(PlantType_Custom.this, "Empty Input Is Not Allowed",
                            Toast.LENGTH_LONG).show();
                }
                else{

                    DBHelper_PlantType dbHelper_plantType;
                    dbHelper_plantType = new DBHelper_PlantType(PlantType_Custom.this);
                    final PlantType plantType;
                    plantType = new PlantType();

                    Toast.makeText(PlantType_Custom.this, "New Plant Type Info Is Saved",
                            Toast.LENGTH_LONG).show();
                textViewPlantType.setText("Added Plant Type is: "
                        + editPlantType.getText().toString()+";");
                textViewAirHumidity.setText("Added Air Humidity is: "
                        + editAirHumidity.getText().toString()+";");
                textViewAirTemperature.setText("Added Air Temperature is:"
                        + editAirTemperature.getText().toString()+";");
                textViewSoilMoisture.setText("Added Soil Moisture is:"
                        + editSoilMoisture.getText().toString()+";");
                }
            }
        });

        buttonCancelType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(getApplication(),
                        "No button clicked", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(PlantType_Custom.this,
                            PlantDialog.class);
                    Bundle sendData = new Bundle();
                    startActivity(intent);
                }

            }
        });
    }

    public void onSavedInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(KEY_PLANTTYPE,textViewPlantType.getText().toString());
        savedInstanceState.putString(KEY_AIRHUMIDITY,textViewAirHumidity.getText().toString());
        savedInstanceState.putString(KEY_AIRTEMPERATURE,textViewAirTemperature.getText().toString());
        savedInstanceState.putString(KEY_SOILMOISTURE,textViewSoilMoisture.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }




 /*       inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(PlantType_Custom.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.plant_type_add_dialog, null);

                // Specify alert dialog is not cancelable/not ignorable
                builder.setCancelable(false);

                // Set the custom layout as alert dialog view
                builder.setView(dialogView);

                // Get the custom alert dialog view widgets reference
                Button saveTypeButton = (Button) dialogView.findViewById(R.id.save_new_plant_type);
                Button cancelTypeButton = (Button) dialogView.findViewById(R.id.cancel_new_plant_type);
                final EditText plantTypeEdit = (EditText) dialogView.findViewById(R.id.plant_type_edit);

                // Create the alert dialog
                final AlertDialog dialog = builder.create();

                // Set positive/yes button click listener
                saveTypeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the alert dialog
                        dialog.cancel();
                        String plantType = plantTypeEdit.getText().toString();
                        Toast.makeText(getApplication(),
                                "Submitted name : " + plantType, Toast.LENGTH_SHORT).show();
                        // Say hello to the submitter
                        textViewPlantType.setText("Your Added Plant Type" + plantType + "!");
                    }
                });

                // Set negative/no button click listener


                // Display the custom alert dialog on interface
                dialog.show();
            }
        });*/

}
