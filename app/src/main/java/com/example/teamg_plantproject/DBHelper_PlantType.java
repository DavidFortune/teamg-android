package com.example.teamg_plantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper_PlantType extends SQLiteOpenHelper {

    //Database Version
    private static final int DB_VERSION = 1;
    //Database Information
    private static final String DB_NAME = "plantTypeManager";
    //Table Name;
    private static final String TABLE_PLANTS = "PLANT TYPE SETTINGS";
    //Table Columns;
    private static final String PLANT_NAME = "plant_name";
    private static final String PLANT_TYPE = "plant_type";
    private static final String SENSOR_ID = "sensor_id";




    //Creating Table Query
    private static final String CREATE_TABLE = "create table " +
            TABLE_PLANTS + "(" + PLANT_NAME
            + " TEXT NOT NULL" + PLANT_TYPE
            + " TEXT NOT NULL" + SENSOR_ID
            + " TEXT " + ") ";

    //Constructor
    public  DBHelper_PlantType(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Executing the Query
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
        onCreate(db);
    }

    void addPlant(Plant plant){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLANT_NAME, plant.getPlantName());
        values.put(PLANT_TYPE, plant.getPlantType());
        values.put(SENSOR_ID, plant.getSensorId());

        db.insert(TABLE_PLANTS, null, values);
        db.close();
    }

    Plant getPlant(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLANTS, new String[]{PLANT_NAME, PLANT_TYPE, SENSOR_ID}, PLANT_NAME + "=?",
        new String[]{String.valueOf(name)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        Plant plant = new Plant();
        plant.setPlantName(cursor.getString(0));
        plant.setPlantType(cursor.getString(1));
        plant.setSensorId(cursor.getString(2));
        return plant;
    }

    public List<Plant> getAllPlants(){
        List<Plant> plantList = new ArrayList<>();

        String selectQuery = "SELECT FROM" + TABLE_PLANTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Plant plant = new Plant();
                plant.setPlantName(cursor.getString(0));
                plant.setPlantType(cursor.getString(1));
                plant.setSensorId(cursor.getString(2));

                plantList.add(plant);
            } while (cursor.moveToNext());
        }

        return plantList;
    }

    public int updatePlant(Plant plant){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLANT_NAME, plant.getPlantName());
        values.put(PLANT_TYPE, plant.getPlantType());
        values.put(SENSOR_ID, plant.getSensorId());

        return db.update(TABLE_PLANTS, values, PLANT_NAME + "=?",
                new String[]{String.valueOf(plant.getPlantName())});
    }

    public void deletePlant(Plant plant){
        SQLiteDatabase db = this. getWritableDatabase();
        db.delete(TABLE_PLANTS, PLANT_NAME + "=?",
                new String[]{String.valueOf(plant.getPlantName())});
        db.close();
    }

    public int getPlantsCount(){
        String countQuery = "SELECT FROM" + TABLE_PLANTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }


}
