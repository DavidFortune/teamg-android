package com.example.teamg_plantproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context)
    {
        sharedPreferences = context.getSharedPreferences("PlantPreferences", Context.MODE_PRIVATE);
    }


    public void savePlantName(String plantName)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("plantName", plantName);
        editor.commit();
    }

    public void savePlantID(String plantID)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("plantId", plantID);
        editor.commit();
    }

    public void savePlantType(String plantType)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("plantType", plantType);
        editor.commit();
    }

    public void savePlant(Plant plant)
    {
        savePlantName(plant.getPlantName());
        savePlantID(Long.toString(plant.getPlantID()));
        savePlantType(plant.getPlantType());
    }

    public String getPlantName()
    {
        return sharedPreferences.getString("plantName", null);
    }

    public String getPlantID()
    {
        return sharedPreferences.getString("plantID", null);
    }

    public String PlantType()
    {
        return sharedPreferences.getString("plantType", null);
    }
    // </editor-fold>
}
