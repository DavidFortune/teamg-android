package com.example.teamg_plantproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import androidx.fragment.app.FragmentManager;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PlantDialog<sharedPreferencesHelper> extends DialogFragment {
    private Button saveButton;
    private Button cancelButton;
    private EditText plantNameEdit;
  //  private EditText plantTypeEdit;
//    private Spinner spinnerEdit;
    private Spinner spinEdit;
    protected int typeID;
    DBHelper_PlantType dbHelper_plantType;
    private EditText plantSensorEdit;
    private CollectionReference sensorDataRef;
    private String sensorID;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    protected static final String TAG = "_PLANT DIALOG";
    protected SharedPreferencesHelper sharedPreferencesHelper;

    ArrayList<Plant> plants = null;
    ArrayList<Type> types = null;

    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_plant_dialog, container, false);
        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        plantNameEdit = view.findViewById(R.id.plant_name);
//      plantTypeEdit = view.findViewById(R.id.plant_type);
//      spinnerEdit = view.findViewById(R.id.spinner1);
        plantSensorEdit = view.findViewById(R.id.plant_sensor_id);
        plantSensorEdit.setText("z1QgZ1bVjYnUyrsz1U9b");
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
        String[] plantChoices = new String[]{
                "Plant Type",
                "Bulbous",
                "Cactus",
                "Common House",
                "Fern",
                "Flowering",
                "Foliage",
                "Succulent",
                "Create A New Plant Type..."
        };
/*        List<String> plantChoices = new ArrayList<>();
        plantChoices.add(0, "Plant Type:"); */


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_spinner_dropdown_item, plantChoices) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEdit.setAdapter(arrayAdapter);
        ArrayAdapter myAdapter = ((ArrayAdapter) spinEdit.getAdapter());

        myAdapter.notifyDataSetChanged();

        spinEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).equals("Plant Type:")){
                    // do nothing
                }
                else {

                    Toast.makeText(getActivity().getApplicationContext(), "Selected : "
                            + selectedItemText, Toast.LENGTH_SHORT).show();
                    if (parent.getItemAtPosition(position).equals("Create A New Plant Type...")) {
                        Intent intent = new Intent(PlantDialog.this.getActivity(),
                                PlantType_Custom.class);
//                        intent.putExtra("B",spinEdit.toString());
                        Bundle sendData = new Bundle();
                        intent.putExtra("TypeID", selectedItemText);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_plant_type, menu);
        return true;
    }

    protected void goToPlantDialog() {
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_plant_type) {
            Intent intent = new Intent(getActivity().getApplicationContext(), PlantDialog.class);
            intent.putExtra("TypeID", typeID);
            startActivity(intent);
        } else if (item.getItemId() == R.id.edit_plant_type_info) {
            Bundle sendData = new Bundle();
            sendData.putInt("TypeId", typeID);
            showDialog(this, sendData);
        }else if (item.getItemId() == R.id.cancel_new_plant_type) {

            goToPlantDialog();
        }
        return super.onOptionsItemSelected(item);
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

    private void showDialog(PlantDialog plantdialog, Bundle sendData) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        EditDialog editDialog = new EditDialog();
        editDialog.setArguments(sendData);
        editDialog.show(fragmentManager, "Plant Type Add");
    }
}
