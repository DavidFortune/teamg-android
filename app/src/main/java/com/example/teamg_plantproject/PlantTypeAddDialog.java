package com.example.teamg_plantproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PlantTypeAddDialog extends AppCompatDialogFragment {
    private Button cancelTypeButton;
    private Button saveTypeButton;
    private EditText plantTypeEdit;
    private EditText airHumidityEdit;
    private EditText airTemperatureEdit;
    private EditText soilMoistureEdit;
    private PlantTypeAddDialogListener addListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState ){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.plant_type_add_dialog, null);
        builder.setView(view)
                .setTitle("Input Info:")
                .setNegativeButton(("cancel"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String plantType = plantTypeEdit.getText().toString();
                        String airHumidity = airHumidityEdit.getText().toString();
                        String airTemperature = airTemperatureEdit.getText().toString();
                        String soilMoisture = soilMoistureEdit.getText().toString();
                        addListener.applyTexts(plantType, airHumidity, airTemperature, soilMoisture);
                    }
                });
        saveTypeButton = view.findViewById(R.id.save_new_plant_type);
        cancelTypeButton = view.findViewById(R.id.cancel_new_plant_type);
        plantTypeEdit = view.findViewById(R.id.plant_type_edit);
        airHumidityEdit = view.findViewById(R.id.air_humidity_edit);
        airTemperatureEdit = view.findViewById(R.id.air_temperature_edit);
        soilMoistureEdit = view.findViewById(R.id.soil_moisture_edit);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addListener = (PlantTypeAddDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException((context.toString() +
                    "must implement PlantTypeAddDialogListener"));
        }
    }

    public interface PlantTypeAddDialogListener{
            void applyTexts(String plantType, String airHumidity, String airTemperature,
                            String soilMoisture);
        }
}
