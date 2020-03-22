package com.example.teamg_plantproject;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpinnerList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button saveButton = (Button) findViewById(R.id.save);
    Button cancelButton = (Button) findViewById(R.id.cancel);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

            // database handler
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            // Spinner Drop down elements
            ArrayList<Plant> plants = db.getAllPlants();

            Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//            EditText plantTypeEdit = (EditText) findViewById(R.id.plant_type);

            String spinnerSelectedItem = spinner.getSelectedItem().toString();
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_choices, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

        }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}