package com.example.teamg_plantproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter
{
    private Context context;
    //ArrayList<Plant> plantArrayList;
    ArrayList<Bitmap> plantPictures;
    String sensorID;

    public ImageAdapter(Context c, ArrayList <Bitmap> plantPictures, String sensorID)
    {
        context = c;
        this.plantPictures = plantPictures;
        this.sensorID = sensorID;
    }
    public int getCount()
    {
        return plantPictures.size();
    }
    public Object getItem(int position)
    {
        return null;
    }
    public long getItemId(int position)
    {
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final DatabaseHelper db = new DatabaseHelper(context);
        //final Plant plant = plantArrayList.get(position);
        if (convertView == null)
        {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.gridview_layout, null);
        }

        final ImageView plantPic = (ImageView)convertView.findViewById(R.id.imageview_plant_pic);
        final TextView date = (TextView)convertView.findViewById(R.id.textview_date);
        plantPic.setImageBitmap(db.getPlantPictures(sensorID));
        //date.setText(plantArrayList.get(position));

        return convertView;
    }
}
