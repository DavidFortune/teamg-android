package com.example.teamg_plantproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class PlantDialog extends DialogFragment {
    private Button saveButton;
    private Button cancelButton;
    private EditText plantNameEdit;
    private EditText plantTypeEdit;
    private Spinner spinnerEdit;
    private EditText plantSensorEdit;
    private CollectionReference sensorDataRef;
    private String sensorID;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    protected static final String TAG = "_PLANT DIALOG";

    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant_dialog, container, false);
        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        plantNameEdit = view.findViewById(R.id.plant_name);
//        plantTypeEdit = view.findViewById(R.id.plant_type);

        plantSensorEdit = view.findViewById(R.id.plant_sensor_id);

        SpinnerList spinnerList;
        spinnerEdit = view.findViewById(R.id.spinner1);


        //on save open DataBase and store new course, on cancel return to activity
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(plantNameEdit.getText().toString())
                        || TextUtils.isEmpty(spinnerEdit.getSelectedItem().toString())
                        || TextUtils.isEmpty(plantSensorEdit.getText().toString())) {
                    Toast.makeText(getContext(), "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
                }
                // TODO: 2020-03-17    Check if sensor exists on firebase
//                else if (!(sensorDataRef = fb.collection("sensors/" + plantSensorEdit.getText().toString() + "/data")).get().isCanceled()) {
//                    Toast.makeText(getContext(), "Incorrect Sensor ID", Toast.LENGTH_LONG).show();
//                }
                else {

                    DatabaseHelper db;
                    db = new DatabaseHelper(getContext());
                    final Plant plant;
                    plant = new Plant();

                    Toast.makeText(getActivity(), "Plant Saved", Toast.LENGTH_LONG).show();
                    String plantName = plantNameEdit.getText().toString();
                    String plantType = spinnerEdit.getSelectedItem().toString();
                    String plantId = plantSensorEdit.getText().toString();
                    plant.setPlantName(plantName);
                    plant.setPlantType(plantType);
                    plant.setSensorId(plantId);
                    db.createPlant(plant);
                    Log.d(TAG, "onClick: " + db.getPlant(1));

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
