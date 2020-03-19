package com.example.teamg_plantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DBHelper_PlantType dbHelper_plantType;
    private Context context;
    private SQLiteDatabase database;

    //Constructor
    public DBManager(Context c){
        context = c;
    }

    public DBManager open() throws SQLException{

        dbHelper_plantType = new DBHelper_PlantType(context);
        database = dbHelper_plantType.getWritableDatabase();
        return this;
    }

    public void close(){

        dbHelper_plantType.close();
    }

    public void insert(String name, String type, String sensorId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper_PlantType.PLANT_NAME, name);
        contentValues.put(DBHelper_PlantType.PLANT_TYPE, type);
        contentValues.put(DBHelper_PlantType.SENSOR_ID, sensorId);
        database.insert(DBHelper_PlantType.TABLE_PLANT, null, contentValues);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DBHelper_PlantType.PLANT_NAME, DBHelper_PlantType.PLANT_TYPE, DBHelper_PlantType.SENSOR_ID};

        Cursor cursor = database.query(DBHelper_PlantType.TABLE_PLANT,
                columns,
                null,
                null,
                null,
                null,
                null
        );


        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String name, String type, String sensorId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper_plantType.PLANT_NAME, name);
        contentValues.put(dbHelper_plantType.PLANT_TYPE, type);
        contentValues.put(dbHelper_plantType.SENSOR_ID, sensorId);

        int i = database.update(dbHelper_plantType.TABLE_PLANT, contentValues,
                dbHelper_plantType.PLANT_NAME + "=" + name, null);
        return i;
    }


    public void delete(String name){
        database.delete(dbHelper_plantType.TABLE_PLANT,
                dbHelper_plantType.PLANT_NAME + " = " + name, null);
    }


}
