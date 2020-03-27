package com.example.teamg_plantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String KEY_PLANT_ID = "plant_id";
    private static final String PLANT_NAME = "plant_name";
    private static final String PLANT_TYPE = "plant_type";
    private static final String SENSOR_ID = "sensor_id";
    private static final String PLANT_PIC = "plant_picture";

/*    private static final String KEY_TYPE_ID = "type_id";*/
//    private static final String TYPE_NAME = "type_name";
/*    private static final int AIR_HUMIDITY = 1;
    private static final int AIR_TEMPERATURE = 1;
    private static final int SOIL_MOISTURE = 1;*/

    private static final String TAG = "DB CREATOR";

    private static final int DATABASE_VERSION = 1;

    private static final String DATA_BASE_NAME = "PlantsList.db";
    private static final String TABLE_PLANTS = "Plants_Table";
//    private static final String TABLE_TYPES = "PLANT TYPE SETTINGS";

    //create plants table
    private static final String CREATE_TABLE_PLANTS = "CREATE TABLE "
            + TABLE_PLANTS + "("
            + KEY_PLANT_ID + " INTEGER PRIMARY KEY,"
            + PLANT_NAME + " TEXT,"
            + PLANT_TYPE + " TEXT,"
            + SENSOR_ID + " TEXT,"
            + PLANT_PIC + " BLOB" + ")";

/*    private static final String CREATE_TABLE_TYPES = "create table " +
            TABLE_TYPES + "("
            + KEY_TYPE_ID + " INTEGER PRIMARY KEY,"
            + PLANT_TYPE + " TEXT,"
            + AIR_HUMIDITY + " INTEGER,"
            + AIR_TEMPERATURE + " INTEGER,"
            + SOIL_MOISTURE + " INTEGER" + ")";*/

    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLANTS);
//        db.execSQL(CREATE_TABLE_TYPES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
    }

    public long createPlant(Plant plant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_NAME, plant.getPlantName());
        contentValues.put(PLANT_TYPE, plant.getPlantType());
        contentValues.put(SENSOR_ID, plant.getSensorId());
        contentValues.put(PLANT_PIC, (Byte) null);

        long plant_id = db.insert(TABLE_PLANTS, null, contentValues);

        Log.d(TAG, "CREATED PLANT");
        return plant_id;
    }

/*    public long createType(PlantType plantType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_TYPE, plantType.getTypeName());
        contentValues.put(String.valueOf(AIR_HUMIDITY), plantType.getAirHumidity());
        contentValues.put(String.valueOf(AIR_TEMPERATURE), plantType.getAirTemperature());
        contentValues.put(String.valueOf(SOIL_MOISTURE), plantType.getSoilMoisture());

        long type_id = db.insert(TABLE_TYPES, null, contentValues);

        Log.d(TAG, "CREATED TYPE");
        return type_id;
    }*/

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

/*    //get 1 type ONLY from Type Table
    public PlantType getType(int type_ID){

        String selectQuery = "SELECT  * FROM " + TABLE_TYPES + " WHERE "
                + KEY_TYPE_ID + " = " + type_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
*//*        Cursor cursor = db.query(TABLE_TYPES, new String[]{TABLE_TYPES, AIR_HUMIDITY, AIR_TEMPERATURE,
                        SOIL_MOISTURE, SUNLIGHT}, type_ID + "=?",
        new String[]{String.valueOf(type_ID)}, null, null, null, null);*//*

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        if (cursor.moveToFirst()) {
            PlantType plantType = new PlantType();

            plantType.setTypeName(cursor.getString(cursor.getColumnIndex(PLANT_TYPE)));
            plantType.setAirHumidity(cursor.getInt(cursor.getColumnIndex(String.valueOf(AIR_HUMIDITY))));
            plantType.setAirTemperature(cursor.getInt(cursor.getColumnIndex(String.valueOf(AIR_TEMPERATURE))));
            plantType.setSoilMoisture(cursor.getInt(cursor.getColumnIndex(String.valueOf(SOIL_MOISTURE))));
            cursor.close();
            return plantType;

        } else {
            cursor.close();
            return null;
        }
    }*/

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

    public void addImage(byte[] image, int plant_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_PIC, image);
        db.update(TABLE_PLANTS, contentValues, KEY_PLANT_ID + " = ?",
                new String[]{String.valueOf(plant_id)});
    }

    public Bitmap getImage(int plant_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLANTS + " WHERE "
                + KEY_PLANT_ID + " = " + plant_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        if (cursor.moveToFirst()) {
            byte[] myImage;
            myImage = cursor.getBlob(4);
            Log.d(TAG, "getImage: " + myImage);
            if (myImage == null) {
                cursor.close();
                return null;
            }
            cursor.close();
            return BitmapFactory.decodeByteArray(myImage, 0, myImage.length);

        } else {
            cursor.close();
            return null;
        }
    }

    public int updatePlantName(int plantID, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLANT_NAME, newName);
        return db.update(TABLE_PLANTS, values, KEY_PLANT_ID + " = ?", new String[]{String.valueOf(plantID)});
    }

    public int updatePlantSensorId(int plantID, String newID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENSOR_ID, newID);
        return db.update(TABLE_PLANTS, values, KEY_PLANT_ID + " = ?", new String[]{String.valueOf(plantID)});
    }
   /* public void deleteType(int typeID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TYPES, KEY_TYPE_ID + "=?",
                new String[]{String.valueOf(typeID)});
//        db.close();
    }

    public long updateType(PlantType plantType){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_TYPE, plantType.getTypeName());
        contentValues.put(String.valueOf(AIR_HUMIDITY), plantType.getAirHumidity());
        contentValues.put(String.valueOf(AIR_TEMPERATURE), plantType.getAirTemperature());
        contentValues.put(String.valueOf(SOIL_MOISTURE), plantType.getSoilMoisture());

        return db.update(TABLE_TYPES, contentValues, PLANT_TYPE + "=?",
                new String[]{String.valueOf(plantType.getTypeName())});
    }

    public int getTypesCount(){
        String countQuery = "SELECT FROM" + TABLE_TYPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }*/

}

