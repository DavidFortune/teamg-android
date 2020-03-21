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

        PlantType plantType1 = new PlantType(1, "Bulbous", 55, 4, 75);
        PlantType plantType2 = new PlantType(2, "Cactus", 20, 18, 30);
        PlantType plantType3 = new PlantType(3, "Common House", 40, 16, 50);
        PlantType plantType4 = new PlantType(4, "Fern", 40, 21, 50);
        PlantType plantType5 = new PlantType(5, "Flowering", 45, 14, 55);
        PlantType plantType6 = new PlantType(6, "Foliage", 40, 20, 45);
        PlantType plantType7 = new PlantType(7, "Succulent", 20, 16, 30);

        db.createType(plantType1);
        db.createType(plantType2);
        db.createType(plantType3);
        db.createType(plantType4);
        db.createType(plantType5);
        db.createType(plantType6);
        db.createType(plantType7);

        ArrayList<PlantType> plantTypes = db.getAllTypes();

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