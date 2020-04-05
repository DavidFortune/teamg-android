package com.example.teamg_plantproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class PlantType_Add extends DialogFragment {
    private static final String TAG = "PlantType_Add";
    private Button cancelTypeButton;
    private Button saveTypeButton;
    private EditText plantTypeEdit;
    private EditText airHumidityEdit;
    private EditText airTemperatureEdit;
    private EditText soilMoistureEdit;
    private DBHelper_PlantType dbHelper_plantType;
    private int typeID;
    Bundle bundle;
    String value;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_plant_type, container, false);

        saveTypeButton = view.findViewById(R.id.save_new_plant_type);
        cancelTypeButton = view.findViewById(R.id.cancel_new_plant_type);
        plantTypeEdit = view.findViewById(R.id.plant_type_edit);
        airHumidityEdit = view.findViewById(R.id.air_humidity_edit);
        airTemperatureEdit = view.findViewById(R.id.air_temperature_edit);
        soilMoistureEdit = view.findViewById(R.id.soil_moisture_edit);
        dbHelper_plantType = new DBHelper_PlantType(getContext());
        typeID = getArguments().getInt("TypeId");

        bundle = new Bundle();

        saveTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(plantTypeEdit.getText().toString())
                        && !TextUtils.isEmpty(airHumidityEdit.getText().toString())
                        && !TextUtils.isEmpty(airTemperatureEdit.getText().toString())
                        && !TextUtils.isEmpty(soilMoistureEdit.getText().toString())) {

                    DBHelper_PlantType dbHelper_plantType = new DBHelper_PlantType(getActivity());
                    dbHelper_plantType.updateType(typeID, plantTypeEdit.getText().toString());
                    dbHelper_plantType.updateType(typeID, airHumidityEdit.getText().toString());
                    dbHelper_plantType.updateType(typeID, airTemperatureEdit.getText().toString());
                    dbHelper_plantType.updateType(typeID, soilMoistureEdit.getText().toString());

                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();

                } else if (!TextUtils.isEmpty(plantTypeEdit.getText().toString())) {
                    dbHelper_plantType.updateType(typeID, plantTypeEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                }else if (!TextUtils.isEmpty(airHumidityEdit.getText().toString())) {
                    dbHelper_plantType.updateAirHumidity(typeID, airHumidityEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                }else if (!TextUtils.isEmpty(airTemperatureEdit.getText().toString())) {
                    dbHelper_plantType.updateAirTemperature(typeID, airTemperatureEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                } else if (!TextUtils.isEmpty(soilMoistureEdit.getText().toString())) {
                    dbHelper_plantType.updateSoilMoisture(typeID, soilMoistureEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                } else
                    dismiss();
                value = plantTypeEdit.getText().toString();
                bundle.putString("new plant type to add", value);
                Intent intent = new Intent(PlantType_Add.this.getActivity(), PlantType_Custom.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        cancelTypeButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Log.d(TAG, "onClick: cancel button");
                    getDialog().dismiss();
                }
            });

        return view;

    }

}







