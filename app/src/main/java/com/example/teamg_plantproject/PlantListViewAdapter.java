package com.example.teamg_plantproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlantListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Plant> plantArrayList;
    protected final static String TAG = "PLANT LISTVIEW ADAPTER";

    public PlantListViewAdapter(Context context, ArrayList<Plant> plantArrayList) {
        this.context = context;
        this.plantArrayList = plantArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Plant> getPlantArrayList() {
        return plantArrayList;
    }

    public void setPlantArrayList(ArrayList<Plant> plantArrayList) {
        this.plantArrayList = plantArrayList;
    }

    @Override
    public int getCount() {
        return plantArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DatabaseHelper db = new DatabaseHelper(context);

        convertView = LayoutInflater.from(context).inflate(R.layout.plant_layout, parent, false);

        TextView plantName = convertView.findViewById(R.id.plant_name_listview);
        TextView plantType = convertView.findViewById(R.id.plant_type_listview);
        TextView plantSensor = convertView.findViewById(R.id.plant_sensor_listview);


        //Link and Fill in values
        plantName.setText("  Plant name: " + plantArrayList.get(position).getPlantName());
        plantType.setText("  Plant type: " + plantArrayList.get(position).getPlantType());
        plantSensor.setText("  Plant sensor ID: " + plantArrayList.get(position).getSensorId());

        //Setup listener for when use click on any of the listView entities to redirect to
        //plant activity of that plant

        return convertView;
    }
}