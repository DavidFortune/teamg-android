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

        db = new DBHelper_PlantType(getApplicationContext());
        /*plantType1.setTypeID(1);
        plantType2.setTypeID(2);
        plantType3.setTypeID(3);
        plantType4.setTypeID(4);
        plantType5.setTypeID(5);
        plantType6.setTypeID(6);
        plantType7.setTypeID(7);
        plantType1.setTypeName("Bulbous");
        plantType2.setTypeName("Cactus");
        plantType3.setTypeName("Common House");
        plantType4.setTypeName("Fern");
        plantType5.setTypeName("Flowering");
        plantType6.setTypeName("Foliage");
        plantType7.setTypeName("Succulent");
        plantType1.setAirHumidity(55);
        plantType2.setAirHumidity(20);
        plantType3.setAirHumidity(40);
        plantType4.setAirHumidity(40);
        plantType5.setAirHumidity(45);
        plantType6.setAirHumidity(40);
        plantType7.setAirHumidity(20);
        plantType1.setAirTemperature(4);
        plantType2.setAirTemperature(18);
        plantType3.setAirTemperature(16);
        plantType4.setAirTemperature(21);
        plantType5.setAirTemperature(14);
        plantType6.setAirTemperature(20);
        plantType7.setAirTemperature(16);
        plantType1.setSoilMoisture(75);
        plantType2.setSoilMoisture(30);
        plantType3.setSoilMoisture(50);
        plantType4.setSoilMoisture(50);
        plantType5.setSoilMoisture(55);
        plantType6.setSoilMoisture(45);
        plantType7.setSoilMoisture(30);
*/
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