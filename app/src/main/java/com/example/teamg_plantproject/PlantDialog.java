package com.example.teamg_plantproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PlantDialog extends DialogFragment {
    private Button saveButton;
    private Button cancelButton;
    private EditText plantNameEdit;
    private EditText plantStateEdit;
    private EditText plantFaveEdit;
    private EditText plantWaterEdit;

    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant_dialog, container, false);
        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        plantNameEdit = view.findViewById(R.id.plant_name);
        plantStateEdit = view.findViewById(R.id.plant_state);
        plantFaveEdit = view.findViewById(R.id.plant_fave);
        plantWaterEdit = view.findViewById(R.id.plant_watertime);

        //on save open DataBase and store new course, on cancel return to activity
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(plantNameEdit.getText().toString())
                        || TextUtils.isEmpty(plantFaveEdit.getText().toString())
                        || TextUtils.isEmpty(plantStateEdit.getText().toString())
                        || TextUtils.isEmpty(plantWaterEdit.getText().toString())) {
                    Toast.makeText(getContext(), "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
                } else {
                    DatabaseHelper db;
                    db = new DatabaseHelper(getContext());
                    final Plant plant;
                    plant = new Plant();

                    Toast.makeText(getActivity(), "Plant Saved", Toast.LENGTH_LONG).show();
                    String plantName = plantNameEdit.getText().toString();
                    String plantState = plantStateEdit.getText().toString();
                    String plantFave = plantFaveEdit.getText().toString();
                    String plantWater = plantWaterEdit.getText().toString();
                    plant.setPlantName(plantName);
                    plant.setPlantState(plantState);
                    plant.setPlantFave(Integer.parseInt(plantFave));
                    plant.setWaterTime(plantWater);
                    db.createPlant(plant);

                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(getActivity().getIntent(), 10);
                dismiss();
            }
        });

        return view;
    }
}
