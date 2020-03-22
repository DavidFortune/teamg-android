package com.example.teamg_plantproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class TypeSetting extends AppCompatActivity {

    private DBHelper_PlantType db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plant_dialog);

        db = new DBHelper_PlantType(this);

        PlantType plantType1 = new PlantType("Bulbous", 55, 4, 75);
        PlantType plantType2 = new PlantType("Cactus", 20, 18, 30);
        PlantType plantType3 = new PlantType("Common House", 40, 16, 50);
        PlantType plantType4 = new PlantType("Fern", 40, 21, 50);
        PlantType plantType5 = new PlantType("Flowering", 45, 14, 55);
        PlantType plantType6 = new PlantType("Foliage", 40, 20, 45);
        PlantType plantType7 = new PlantType("Succulent", 20, 16, 30);


        db.createType(plantType1);
        db.createType(plantType2);
        db.createType(plantType3);
        db.createType(plantType4);
        db.createType(plantType5);
        db.createType(plantType6);
        db.createType(plantType7);

        db = new DBHelper_PlantType(getApplicationContext());

        ArrayList<PlantType> plantTypes = db.getAllTypes();
        plantTypes.add(plantType1);
        plantTypes.add(plantType2);
        plantTypes.add(plantType3);
        plantTypes.add(plantType4);
        plantTypes.add(plantType5);
        plantTypes.add(plantType6);
        plantTypes.add(plantType7);


        if (plantTypes != null) {
            String[] plantItems = new String[plantTypes.size()];

            for (int i = 0; i < plantTypes.size(); i++) {
                plantItems[i] = plantTypes.get(i).toString();
            }

            // display like string instances
            ListView list = (ListView) findViewById(R.id.plant_type_threshold);
            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.list, plantItems));


        }

    }
}