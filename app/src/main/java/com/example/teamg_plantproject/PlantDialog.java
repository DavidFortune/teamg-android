package com.example.teamg_plantproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class PlantDialog<sharedPreferencesHelper> extends DialogFragment {
    private Button saveButton;
    private Button cancelButton;
    private EditText plantNameEdit;
  //  private EditText plantTypeEdit;
//    private Spinner spinnerEdit;

    private Spinner spinEdit;
    DBHelper_PlantType dbHelper_plantType;
    private EditText plantSensorEdit;
    private CollectionReference sensorDataRef;
    private String sensorID;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    protected static final String TAG = "_PLANT DIALOG";

    ArrayList<Plant> plants = null;
    ArrayList<Type> types = null;

    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant_dialog, container, false);
        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        plantNameEdit = view.findViewById(R.id.plant_name);
//      plantTypeEdit = view.findViewById(R.id.plant_type);
//      spinnerEdit = view.findViewById(R.id.spinner1);
        plantSensorEdit = view.findViewById(R.id.plant_sensor_id);

        spinEdit = view.findViewById(R.id.sp_Text);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(plantNameEdit.getText().toString())

                        || TextUtils.isEmpty(spinEdit.getSelectedItem().toString())
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

                    String plantType = spinEdit.getSelectedItem().toString();
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

        plantSensorEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QRCodeScanActivity.class);
                startActivityForResult(intent,0);

            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data!=null)
                {
                    Barcode barcode = data.getParcelableExtra("qrcode");
                    plantSensorEdit.setText(barcode.displayValue);
                }
                else{

                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
