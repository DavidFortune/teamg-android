package com.example.teamg_plantproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

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
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        final int soilMax = 3000;
        final int solarMax = 2000;
        final int[] ii = new int[1];
        final int[] jj = new int[1];
        final int[] kk = new int[1];

        CollectionReference sensorDataRef = fb.collection("sensors/" + plantArrayList.get(position).getSensorId() + "/data");

        Log.d(TAG, "getView: " + sensorDataRef);
        convertView = LayoutInflater.from(context).inflate(R.layout.plant_layout, parent, false);

        final View finalConvertView = convertView;
        sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(MainActivity.class.getName(), "Listen failed.", e);
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("rawHumidity") != null) {

                                String i = Objects.requireNonNull(doc.get("rawSoilValue")).toString();
                                ii[0] = (int) Math.floor(Double.parseDouble(i));
                                String j = Objects.requireNonNull(doc.get("rawHumidity")).toString();
                                jj[0] = (int) Math.floor(Double.parseDouble(j));
                                String k = Objects.requireNonNull(doc.get("rawSolarValue")).toString();
                                kk[0] = (int) Math.floor(Double.parseDouble(k));
                                ProgressBar state = finalConvertView.findViewById(R.id.overall_state);
                                TextView percentage = finalConvertView.findViewById(R.id.overall_percentage);
                                int finalValue = (((ii[0] * 100) / soilMax)
                                        + jj[0]
                                        + ((kk[0] * 100) / solarMax))
                                        / 3;
                                state.setProgress(finalValue);
                                if (finalValue < 100 && finalValue >= 75)
                                    state.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
                                else if (finalValue < 75 && finalValue >= 50)
                                    state.getProgressDrawable().setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
                                else
                                    state.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

                                percentage.setText(finalValue + "%");
                            }
                        }
                    }
                });

        TextView plantName = convertView.findViewById(R.id.plant_name_listview);
        TextView plantType = convertView.findViewById(R.id.plant_type_listview);
        TextView plantSensor = convertView.findViewById(R.id.plant_sensor_listview);
        ImageView plantPicture = convertView.findViewById(R.id.plant_image_view);


        //Link and Fill in values
        plantName.setText("  Plant name: " + plantArrayList.get(position).getPlantName());
        plantType.setText("  Plant type: " + plantArrayList.get(position).getPlantType());
        plantSensor.setText("  Plant sensor ID: " + plantArrayList.get(position).getSensorId());

        //Setup listener for when use click on any of the listView entities to redirect to
        //plant activity of that plant

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlantActivity.class);
                intent.putExtra("PlantID", plantArrayList.get(position).getPlantID());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


}