package com.example.teamg_plantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String KEY_PLANT_ID = "plant_id";
    private static final String PLANT_NAME = "plant_name";
    private static final String PLANT_TYPE = "plant_type";
    private static final String SENSOR_ID = "plant_sensor";

    private static final String TAG = "DB CREATOR";

    private static final int DATABASE_VERSION = 1;

    private static final String DATA_BASE_NAME = "PlantsList.db";
    private static final String TABLE_PLANTS = "Plants_Table";

    //create plants table
    private static final String CREATE_TABLE_PLANTS = "CREATE TABLE "
            + TABLE_PLANTS + "("
            + KEY_PLANT_ID + " INTEGER PRIMARY KEY,"
            + PLANT_NAME + " TEXT,"
            + PLANT_TYPE + " TEXT,"
            + SENSOR_ID + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PLANTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
    }

    public long createPlant(Plant plant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_NAME, plant.getPlantName());
        contentValues.put(PLANT_TYPE, plant.getPlantType());
        contentValues.put(SENSOR_ID, plant.getSensorId());

        long plant_id = db.insert(TABLE_PLANTS, null, contentValues);

        Log.d(TAG, "CREATED PLANT");
        return plant_id;
    }

    //get 1 plant
    public Plant getPlant(int plant_id) {
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery = "SELECT  * FROM " + TABLE_PLANTS + " WHERE "
                + KEY_PLANT_ID + " = " + plant_id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        if (cursor.moveToFirst()) {
            Plant plant = new Plant();

            plant.setPlantID(cursor.getInt(cursor.getColumnIndex(KEY_PLANT_ID)));
            plant.setPlantName(cursor.getString(cursor.getColumnIndex(PLANT_NAME)));
            plant.setPlantType(cursor.getString(cursor.getColumnIndex(PLANT_TYPE)));
            plant.setSensorId(cursor.getString(cursor.getColumnIndex(SENSOR_ID)));
            cursor.close();
            return plant;

        } else {
            cursor.close();
            return null;
        }
    }

    //Get all plant from plant table
    public ArrayList<Plant> getAllPlants() {
        ArrayList<Plant> plants = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLANTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Plant plant = new Plant();
                plant.setPlantID(cursor.getInt(cursor.getColumnIndex(KEY_PLANT_ID)));
                plant.setPlantName(cursor.getString(cursor.getColumnIndex(PLANT_NAME)));
                plant.setPlantType(cursor.getString(cursor.getColumnIndex(PLANT_TYPE)));
                plant.setSensorId(cursor.getString(cursor.getColumnIndex(SENSOR_ID)));

                plants.add(plant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return plants;
    }

    //delete a plant inside the plant table
    public void deletePlant(int plantID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANTS, KEY_PLANT_ID + " = ?",
                new String[]{String.valueOf(plantID)});
    }
}

