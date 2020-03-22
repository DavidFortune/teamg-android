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

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SpinnerList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

            // database handler
            DBHelper_PlantType db = new DBHelper_PlantType(getApplicationContext());
            // Spinner Drop down elements
            ArrayList<PlantType> types = db.getAllTypes();
            //1.get a reference to the spinner

//            String spinnerSelectedItem = spinner.getSelectedItem().toString();
            //2.create a simple static list of strings
            ArrayList<String> spinnerArray = new ArrayList<>();
            spinnerArray.add("Bulbous");
            spinnerArray.add("Cactus");
            spinnerArray.add("Common House");
            spinnerArray.add("Fern");
            spinnerArray.add("Flowering");
            spinnerArray.add("Foliage");
            spinnerArray.add("Succulent");

        String[] spinnerString = {"Bulbous","Cactus","Common House","Fern","Flowering",
                "Foliage","Succulent"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
            //3.create an adapter from the list
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerString);
        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.type_choices));

        //4. set the drop down view and the adapter on the spinner
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter1);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter2);
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter3);
/*            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.type_choices, android.R.layout.simple_spinner_item);*/
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