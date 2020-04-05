package com.example.teamg_plantproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlantType_Custom extends AppCompatActivity implements PlantTypeAddDialog.PlantTypeAddDialogListener {
    private TextView mrUser;
    private TextView textViewPlantType;
    private TextView textViewAirHumidity;
    private TextView textViewAirTemperature;
    private TextView textViewSoilMoisture;
    private Button inputButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_type__custom);

        mrUser = (TextView) findViewById(R.id.mr_user);
        inputButton = (Button) findViewById(R.id.input_custom);


        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

        });
    }

    public void openDialog(){
        PlantTypeAddDialog plantTypeAddDialog = new PlantTypeAddDialog();
        plantTypeAddDialog.show(getSupportFragmentManager(),"PlantType_Add");
    }
    @Override
    public void applyTexts(String plantType, String airHumidity, String airTemperature, String soilMoisture) {
        textViewPlantType.setText(plantType);
        textViewAirHumidity.setText(airHumidity);
        textViewAirTemperature.setText(airTemperature);
        textViewSoilMoisture.setText(soilMoisture);


    }
}
