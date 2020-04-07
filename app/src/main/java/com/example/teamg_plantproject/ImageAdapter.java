package com.example.teamg_plantproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Image> imagesAll;
    private String sensorID;
    private int plantId;

    public ImageAdapter(Context c, ArrayList<Image> images, String sensorID, int plantId) {
        context = c;
        imagesAll = images;
        this.sensorID = sensorID;
        this.plantId = plantId;
    }

    public int getCount() {
        return imagesAll.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final DatabaseHelper db = new DatabaseHelper(context);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.gridview_layout, null);
        }

        final ImageView plantPic = convertView.findViewById(R.id.imageview_plant_pic);
        final TextView date = convertView.findViewById(R.id.textview_date);

        plantPic.setImageBitmap(imagesAll.get(position).getImage());
        date.setText(imagesAll.get(position).getImageDate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDisplayActivity.class);
                intent.putExtra("Image", imagesAll.get(position));
                System.out.println(plantId + "++++++++++++++++++++++++++++" + "Image Adapter");
                intent.putExtra("PlantID", plantId);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
