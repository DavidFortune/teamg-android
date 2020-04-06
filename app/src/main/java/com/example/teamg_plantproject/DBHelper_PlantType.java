package com.example.teamg_plantproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBHelper_PlantType extends SQLiteOpenHelper {

    private static final String KEY_TYPE_ID = "type_id";
    private static final String PLANT_TYPE = "plant_type";
    private static final String AIR_HUMIDITY = "air_humidity";
    private static final String AIR_TEMPERATURE = "air_temperature";
    private static final String SOIL_MOISTURE = "soil_moisture";
    private static final String TABLE_TYPES = "Plant_Types_Table";

    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "DB_PT CREATOR";

    private static final String DATA_BASE_NAME = "PlantType.db";


    //Creating Table Query
    private static final String CREATE_TABLE_TYPES = "CREATE TABLE " +
            TABLE_TYPES + "("
            + KEY_TYPE_ID + " INTEGER PRIMARY KEY,"
            + PLANT_TYPE + " TEXT NOT NULL,"
            + AIR_HUMIDITY + " INTEGER NOT NULL,"
            + AIR_TEMPERATURE + " INTEGER NOT NULL,"
            + SOIL_MOISTURE + " INTEGER NOT NULL" + ")";

    public DBHelper_PlantType(Context context) {
        super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Executing the Query
        db.execSQL(CREATE_TABLE_TYPES);
        //Add initial seed of hardcoded plant types


        createType(db,"Bulbous", 55, 4, 75);
        createType(db,"Cactus", 20, 18, 30);
        createType(db,"Common House", 40, 16, 50);
        createType(db,"Fern", 40, 21, 50);
        createType(db,"Flowering", 45, 14, 55);
        createType(db,"Foliage", 40, 20, 45);
        createType(db,"Succulent", 20, 16, 30);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
        onCreate(db);
    }
    public int createType(SQLiteDatabase db,String plant_Name, int humiditypercent, int temperaturepercent, int soilmoisturepercent) {


        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_TYPE, plant_Name);
        contentValues.put(AIR_HUMIDITY, humiditypercent);
        contentValues.put(AIR_TEMPERATURE, temperaturepercent);
        contentValues.put(SOIL_MOISTURE, soilmoisturepercent);
        db.insert(TABLE_TYPES, null, contentValues);

        Log.d(TAG, "createType: "+plant_Name+""+humiditypercent+""+temperaturepercent+""+soilmoisturepercent);
        return 1;
    }

    public long createType(PlantType plantType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_TYPE, plantType.getPlantType());
        contentValues.put(AIR_HUMIDITY, plantType.getAirHumidity());
        contentValues.put(AIR_TEMPERATURE, plantType.getAirTemperature());
        contentValues.put(SOIL_MOISTURE, plantType.getSoilMoisture());

        long type_id = db.insert(TABLE_TYPES, null, contentValues);

        Log.d(TAG, "CREATED TYPE" +plantType.getAirHumidity() + "  " +plantType.getAirTemperature() + "  " +  plantType.getSoilMoisture() );
        return type_id;
    }

    //get 1 type ONLY from Type Table
    public PlantType getType(int type_ID) {

        String selectQuery = "SELECT  * FROM " + TABLE_TYPES + " WHERE "
                + KEY_TYPE_ID + " = " + type_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        assert cursor != null;
        if (cursor.moveToFirst()) {
            PlantType plantType = new PlantType();

            plantType.setPlantType(cursor.getString(cursor.getColumnIndex(PLANT_TYPE)));
            plantType.setAirHumidity(cursor.getInt(cursor.getColumnIndex(AIR_HUMIDITY)));
            plantType.setAirTemperature(cursor.getInt(cursor.getColumnIndex(AIR_TEMPERATURE)));
            plantType.setSoilMoisture(cursor.getInt(cursor.getColumnIndex(SOIL_MOISTURE)));
            cursor.close();
            return plantType;

        } else {
            cursor.close();
            return null;
        }
    }

    //Get all plant types from plant table
    public ArrayList<PlantType> getAllTypes() {
        ArrayList<PlantType> types = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PlantType plantType = new PlantType();
                plantType.setTypeID(cursor.getInt(cursor.getColumnIndex(KEY_TYPE_ID)));
                plantType.setPlantType(cursor.getString(cursor.getColumnIndex(PLANT_TYPE)));
                plantType.setAirHumidity(cursor.getInt(cursor.getColumnIndex(AIR_HUMIDITY)));
                plantType.setAirTemperature(cursor.getInt(cursor.getColumnIndex(AIR_TEMPERATURE)));
                plantType.setSoilMoisture(cursor.getInt(cursor.getColumnIndex(SOIL_MOISTURE)));

                types.add(plantType);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return types;
    }

    public void deleteType(int typeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TYPES, KEY_TYPE_ID + "=?",
                new String[]{String.valueOf(typeID)});
//        db.close();
    }


    public long updatePlantType(PlantType plantType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLANT_TYPE, plantType.getPlantType());
        contentValues.put(AIR_HUMIDITY, plantType.getAirHumidity());
        contentValues.put(AIR_TEMPERATURE, plantType.getAirTemperature());
        contentValues.put(SOIL_MOISTURE, plantType.getSoilMoisture());

        return db.update(TABLE_TYPES, contentValues, PLANT_TYPE + "=?",
                new String[]{String.valueOf(plantType.getPlantType())});
    }

    public int updateType(int typeID, String newPlantType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLANT_TYPE, newPlantType);
        return db.update(TABLE_TYPES, values, KEY_TYPE_ID + " = ?", new String[]{String.valueOf(typeID)});
    }

    public int updateAirHumidity(int typeID, String newAirHumidity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLANT_TYPE, newAirHumidity);
        return db.update(TABLE_TYPES, values, KEY_TYPE_ID + " = ?", new String[]{String.valueOf(typeID)});
    }

    public int updateAirTemperature(int typeID, String newAirTemperature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLANT_TYPE, newAirTemperature);
        return db.update(TABLE_TYPES, values, KEY_TYPE_ID + " = ?", new String[]{String.valueOf(typeID)});
    }

    public int updateSoilMoisture(int typeID, String newSoilMoisture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLANT_TYPE, newSoilMoisture);
        return db.update(TABLE_TYPES, values, KEY_TYPE_ID + " = ?", new String[]{String.valueOf(typeID)});
    }

    public int getTypesCount() {
        String countQuery = "SELECT FROM" + TABLE_TYPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }



    public class TypeSetting extends AppCompatActivity {

        private DBHelper_PlantType db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_plant_dialog);


/*
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
*/
/*

            if (plantTypes != null) {
                String[] plantItems = new String[plantTypes.size()];

                for (int i = 0; i < plantTypes.size(); i++) {
                    plantItems[i] = plantTypes.get(i).toString();
                }
*/
                // display like string instances
            db = new DBHelper_PlantType(this);
            ArrayList<PlantType> plants = new ArrayList<>();

            plants = db.getAllTypes();

            if (plants != null) {
                String[] plantItems = new String[plants.size()];

                for (int i = 0; i < plants.size(); i++) {
                    plantItems[i] = plants.get(i).getPlantType();
                }
            ListView list = (ListView) findViewById(R.id.plant_type_threshold);
                list.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.list, plantItems));


            }

        }

    }
}
