package com.example.teamg_plantproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlantType_Custom extends AppCompatActivity implements PlantTypeAddDialog.PlantTypeAddDialogListener{
    private TextView mrUser;
    private TextView textViewPlantType;
    private TextView textViewAirHumidity;
    private TextView textViewAirTemperature;
    private TextView textViewSoilMoisture;
    private Button inputButton;
    private Button cancelTypeButton;
    private Button saveTypeButton;
    private EditText plantTypeEdit;
    private EditText airHumidityEdit;
    private EditText airTemperatureEdit;
    private EditText soilMoistureEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_type__custom);

        mrUser = (TextView) findViewById(R.id.mr_user);
        TextView textViewPlantType = (TextView) findViewById(R.id.tv_new_plant_type);
        TextView textViewAirHumidity = (TextView) findViewById(R.id.tv_new_air_humidity);
        TextView textViewAirTemperature = (TextView) findViewById(R.id.tv_new_air_temperature);
        TextView textViewSoilMoisture = (TextView) findViewById(R.id.tv_new_soil_moisture);
        inputButton = (Button) findViewById(R.id.input_custom);
/*        saveTypeButton = (Button) findViewById(R.id.save_new_plant_type);
        cancelTypeButton = (Button) findViewById(R.id.cancel_new_plant_type);
        plantTypeEdit = findViewById(R.id.plant_type_edit);
        airHumidityEdit = findViewById(R.id.air_humidity_edit);
        airTemperatureEdit = findViewById(R.id.air_temperature_edit);
        soilMoistureEdit = findViewById(R.id.soil_moisture_edit);*/

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

        });
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
                cancelTypeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss/cancel the alert dialog
                        //dialog.cancel();
                        dialog.dismiss();
                        Toast.makeText(getApplication(),
                                "No button clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                // Display the custom alert dialog on interface
                dialog.show();
            }
        });*/

    public void openDialog(){
        PlantTypeAddDialog plantTypeAddDialog = new PlantTypeAddDialog();
        plantTypeAddDialog.show(getSupportFragmentManager(),"PlantType_Add");
    }
    @Override
    public void applyTexts(String plantType, String airHumidity, String airTemperature,
                String soilMoisture) {
        textViewPlantType.setText(plantType);
        textViewAirHumidity.setText(airHumidity);
        textViewAirTemperature.setText(airTemperature);
        textViewSoilMoisture.setText(soilMoisture);
    }
}
