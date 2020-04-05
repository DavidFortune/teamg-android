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
    private static final String PLANT_PICTURES = "plant_pictures";

    private static final String TAG = "DB CREATOR";
    private static final int DATABASE_VERSION = 1;

    private static final String DATA_BASE_NAME = "PlantsList.db";
    private static final String TABLE_PLANTS = "Plants_Table";
    private static final String TABLE_PLANT_PICTURES = "Plant_Pictures_Table";

    //create plants table
    private static final String CREATE_TABLE_PLANTS = "CREATE TABLE "
            + TABLE_PLANTS + "("
            + KEY_PLANT_ID + " INTEGER PRIMARY KEY,"
            + PLANT_NAME + " TEXT,"
            + PLANT_TYPE + " TEXT,"
            + SENSOR_ID + " TEXT,"
            + PLANT_PIC + " BLOB" + ")";

    //create plant pictures table
    private static final String CREATE_PLANT_PICTURES = "CREATE TABLE "
            + TABLE_PLANT_PICTURES + "("
            + KEY_PLANT_ID + " INTEGER PRIMARY KEY,"
            + PLANT_PICTURES + " BLOB" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLANTS);
        db.execSQL(CREATE_PLANT_PICTURES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT_PICTURES);
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

    public long createPlantPicture(Plant plant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_PICTURES, (Byte) null);

        long plant_id = db.insert(TABLE_PLANT_PICTURES, null, contentValues);

        Log.d(TAG, "CREATED PLANT PICTURE");
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

    //get 1 plant picture
    public Plant getPlantPicture(int plant_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLANT_PICTURES + " WHERE "
                + KEY_PLANT_ID + " = " + plant_id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        if (cursor.moveToFirst()) {
            Plant plant = new Plant();

            plant.setPlantID(cursor.getInt(cursor.getColumnIndex(KEY_PLANT_ID)));
            plant.setPlantName(cursor.getString(cursor.getColumnIndex(PLANT_PICTURES)));

            cursor.close();
            return plant;

        } else {
            cursor.close();
            return null;
        }
    }


    //Get all plants from plant table
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

    //delete a plant picture inside the plant picture table
    public void deletePlantPicture(int plantID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANT_PICTURES, KEY_PLANT_ID + " = ?",
                new String[]{String.valueOf(plantID)});
    }


    public void addImage(byte[] image, int plant_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_PIC, image);
        db.update(TABLE_PLANTS, contentValues, KEY_PLANT_ID + " = ?",
                new String[]{String.valueOf(plant_id)});
    }

    public void addPlantPicture(byte[] image, int plant_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_PICTURES, image);
        db.update(TABLE_PLANT_PICTURES, contentValues, KEY_PLANT_ID + " = ?",
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

    public Bitmap getPlantPictures(int plant_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLANT_PICTURES + " WHERE "
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
}