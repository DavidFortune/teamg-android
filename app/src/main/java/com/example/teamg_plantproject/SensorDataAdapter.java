package com.example.teamg_plantproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SensorDataAdapter extends FirestoreRecyclerAdapter<SensorData, SensorDataAdapter.SensorDataHolder> {

    public SensorDataAdapter(@NonNull FirestoreRecyclerOptions<SensorData> options) {
        super(options);

        Log.w(MainActivity.class.getName(), "Sensor data adapter called.");
    }

    @Override
    protected void onBindViewHolder(@NonNull SensorDataHolder holder, int position, @NonNull SensorData model) {

        holder.textViewTimestamp.setText("2020-03-08");

        String sensorDataText = "Humidity:    " + Double.toString(model.getrawHumidity())
                            + "\nSolar:       " + Double.toString(model.getrawSolarValue())
                            + "\nTemperature: " + Double.toString(model.getrawTemp());

        holder.textViewSensorData.setText(sensorDataText);

        Log.w(MainActivity.class.getName(), "View holder Binding.");
    }

    @NonNull
    @Override
    public SensorDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_data_item,
                parent, false);

        Log.w(MainActivity.class.getName(), "View Holder Creation.");
        return new SensorDataHolder(v);
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    class SensorDataHolder extends RecyclerView.ViewHolder {

        TextView textViewSensorData;
        TextView textViewTimestamp;

        public SensorDataHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorData = itemView.findViewById(R.id.text_view_sensordata);
            textViewTimestamp = itemView.findViewById(R.id.text_view_timestamp);
        }
    }


}
