package com.example.teamg_plantproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditDialog extends DialogFragment {
    private Button saveButton;
    private EditText nameEdit;
    private EditText sesonrIDEdit;
    private DatabaseHelper db;
    private int plantID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_plant, container, false);
        saveButton = view.findViewById(R.id.save_changes);
        nameEdit = view.findViewById(R.id.plant_name_edit);
        sesonrIDEdit = view.findViewById(R.id.sensor_id_edit);
        db = new DatabaseHelper(getContext());
        plantID = getArguments().getInt("PlantId");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sesonrIDEdit.getText().toString())
                        && !TextUtils.isEmpty(nameEdit.getText().toString())) {

                    db.updatePlantSensorId(plantID, sesonrIDEdit.getText().toString());
                    db.updatePlantName(plantID, nameEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                } else if (!TextUtils.isEmpty(sesonrIDEdit.getText().toString())) {
                    db.updatePlantSensorId(plantID, sesonrIDEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                } else if (!TextUtils.isEmpty(nameEdit.getText().toString())) {
                    db.updatePlantName(plantID, nameEdit.getText().toString());
                    getActivity().startActivityForResult(getActivity().getIntent(), 10);
                    dismiss();
                } else
                    dismiss();
            }
        });

        return view;
    }

}
