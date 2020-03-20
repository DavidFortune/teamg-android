package com.example.teamg_plantproject.ui.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamg_plantproject.MainActivity;
import com.example.teamg_plantproject.R;
import com.example.teamg_plantproject.SensorData;
import com.example.teamg_plantproject.SensorDataAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    protected View root;
    private CollectionReference sensorDataRef = fb.collection("sensors/z1QgZ1bVjYnUyrszlU9b/data");
    private DocumentReference sensorDataObjectRef = fb.document("sensors/z1QgZ1bVjYnUyrszlU9b/data/HGt6aznsr96pnpVlrw7C");
    private SensorDataAdapter adapter;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel =
                ViewModelProviders.of(this).get(FeedViewModel.class);
        root = inflater.inflate(R.layout.fragment_feed, container, false);
        textView = root.findViewById(R.id.text_sensor_data);


        sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(50)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(MainActivity.class.getName(), "Listen failed.", e);
                        return;
                    }

                    String sensorDataText = "";

                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("rawHumidity") != null) {

                            Timestamp timestamp = (Timestamp) doc.get("createdAt");
                            Date date = timestamp.toDate();

                            sensorDataText = sensorDataText +
                                        date.toString()
                                        + "\nAir Humidity: " + doc.get("rawHumidity")
                                        + "    Solar: " + doc.get("rawSolarValue")
                                        + "\nSoil humidity: " + doc.get("rawSoilValue")
                                        + "    Temperature: " + doc.get("rawTemp")
                                        + "\n\n";

                            Log.d(MainActivity.class.getName(), sensorDataText);
                        }
                    }

                    textView.setText(sensorDataText);

                }
            });


        //setUpRecyclerView();

        return root;
    }

    private void setUpRecyclerView(){
        Query query = sensorDataRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(20);


        FirestoreRecyclerOptions<SensorData> options = new FirestoreRecyclerOptions.Builder<SensorData>()
                .setQuery(query, SensorData.class)
                .build();

        adapter = new SensorDataAdapter(options);

        RecyclerView recyclerView = this.root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        Log.w(MainActivity.class.getName(), "Recycler View setup completed.");
    }

    @Override
    public void onStart() {
        super.onStart();
        //adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        //if(adapter != null) {
            //adapter.stopListening();
       // }
    }
}