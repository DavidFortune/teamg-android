package com.example.teamg_plantproject;

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
    }

    @Override
    protected void onBindViewHolder(@NonNull SensorDataHolder holder, int position, @NonNull SensorData model) {

        holder.textViewTimestamp.setText(model.getCreatedAt());

        String sensorDataText = "Humidity:    " + Double.toString(model.getRawHumidity())
                            + "\nSolar:       " + Double.toString(model.getRawSolarValue())
                            + "\nTemperature: " + Double.toString(model.getRawTemp());
        holder.textViewSensorData.setText(sensorDataText);
    }

    @NonNull
    @Override
    public SensorDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_data_item,
                parent, false);
        return new SensorDataHolder(v);
    }

    class SensorDataHolder extends RecyclerView.ViewHolder{

        TextView textViewSensorData;
        TextView textViewTimestamp;

        public SensorDataHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorData = itemView.findViewById(R.id.text_view_sensordata);
            textViewTimestamp = itemView.findViewById(R.id.text_view_timestamp);
        }
    }


}
