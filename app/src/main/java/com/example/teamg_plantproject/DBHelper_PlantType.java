package com.example.teamg_plantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DBHelper_PlantType extends SQLiteOpenHelper {

    private static final String KEY_TYPE_ID = "type_id";
    private static final String TYPE_NAME = "type_name";
    private static final int AIR_HUMIDITY = 1;
    private static final int AIR_TEMPERATURE = 1;
    private static final int SOIL_MOISTURE = 1;

    private static final String TAG = "DB_PT CREATOR";

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "plantType.db";
    private static final String TABLE_TYPES = "PLANT TYPE SETTINGS";

    //Creating Table Query
    private static final String CREATE_TABLE_TYPES = "create table " +
            TABLE_TYPES + "("
            + KEY_TYPE_ID + " INTEGER PRIMARY KEY,"
            + TYPE_NAME + " TEXT,"
            + AIR_HUMIDITY + " INTEGER,"
            + AIR_TEMPERATURE + " INTEGER,"
            + SOIL_MOISTURE + " INTEGER" + ")";

    public DBHelper_PlantType(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Executing the Query
        db.execSQL(CREATE_TABLE_TYPES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
        onCreate(db);
    }

    public long createType(PlantType plantType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE_NAME, plantType.getTypeName());
        contentValues.put(String.valueOf(AIR_HUMIDITY), plantType.getAirHumidity());
        contentValues.put(String.valueOf(AIR_TEMPERATURE), plantType.getAirTemperature());
        contentValues.put(String.valueOf(SOIL_MOISTURE), plantType.getSoilMoisture());

        long type_id = db.insert(TABLE_TYPES, null, contentValues);

        Log.d(TAG, "CREATED TYPE");
        return type_id;
//        db.insert(TABLE_TYPES, null, contentValues);
//        db.close();
    }

    //get 1 type ONLY from Type Table
    public PlantType getType(int type_ID){

        String selectQuery = "SELECT  * FROM " + TABLE_TYPES + " WHERE "
                + KEY_TYPE_ID + " = " + type_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
/*        Cursor cursor = db.query(TABLE_TYPES, new String[]{TABLE_TYPES, AIR_HUMIDITY, AIR_TEMPERATURE,
                        SOIL_MOISTURE, SUNLIGHT}, type_ID + "=?",
        new String[]{String.valueOf(type_ID)}, null, null, null, null);*/

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        if (cursor.moveToFirst()) {
            PlantType plantType = new PlantType();

            plantType.setTypeID(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_ID)));
            plantType.setTypeName(cursor.getString(cursor.getColumnIndex(TYPE_NAME)));
            plantType.setAirHumidity(cursor.getInt(cursor.getColumnIndex(String.valueOf(AIR_HUMIDITY))));
            plantType.setAirTemperature(cursor.getInt(cursor.getColumnIndex(String.valueOf(AIR_TEMPERATURE))));
            plantType.setSoilMoisture(cursor.getInt(cursor.getColumnIndex(String.valueOf(SOIL_MOISTURE))));
            cursor.close();
            return plantType;

        } else {
            cursor.close();
            return null;
        }
    }
/*        if(cursor != null){
            cursor.moveToFirst();
        }
        Plant plant = new Plant();
        plant.setPlantName(cursor.getString(0));
        plant.setPlantType(cursor.getString(1));
        plant.setSensorId(cursor.getString(2));
        return plant;*/

    //get all plant types from the table
    public ArrayList<PlantType> getAllTypes(){
        ArrayList<PlantType> types = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do{
                PlantType plantType = new PlantType();
                plantType.setTypeID(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_ID)));
                plantType.setTypeName(cursor.getString(cursor.getColumnIndex(TYPE_NAME)));
                plantType.setAirHumidity(cursor.getInt(cursor.getColumnIndex(String.valueOf(AIR_HUMIDITY))));
                plantType.setAirTemperature(cursor.getInt(cursor.getColumnIndex(String.valueOf(AIR_TEMPERATURE))));
                plantType.setSoilMoisture(cursor.getInt(cursor.getColumnIndex(String.valueOf(SOIL_MOISTURE))));

                types.add(plantType);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return types;
    }

    public void deleteType(int typeID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TYPES, KEY_TYPE_ID + "=?",
                new String[]{String.valueOf(typeID)});
//        db.close();
    }

    public long updateType(PlantType plantType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE_NAME, plantType.getTypeName());
        contentValues.put(String.valueOf(AIR_HUMIDITY), plantType.getAirHumidity());
        contentValues.put(String.valueOf(AIR_TEMPERATURE), plantType.getAirTemperature());
        contentValues.put(String.valueOf(SOIL_MOISTURE), plantType.getSoilMoisture());

        return db.update(TABLE_TYPES, contentValues, TYPE_NAME + "=?",
                new String[]{String.valueOf(plantType.getTypeName())});
    }

    public int getTypesCount(){
        String countQuery = "SELECT FROM" + TABLE_TYPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

}
