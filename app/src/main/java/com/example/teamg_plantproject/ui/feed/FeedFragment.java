package com.example.teamg_plantproject.ui.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamg_plantproject.MainActivity;
import com.example.teamg_plantproject.R;
import com.example.teamg_plantproject.SensorData;
import com.example.teamg_plantproject.SensorDataAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FeedFragment extends Fragment {

    private FeedViewModel feedViewModel;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference sensorDataRef = fb.collection("sensors/z1QgZ1bVjYnUyrszlU9b/data");
    private SensorDataAdapter adapter;
    protected View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel =
                ViewModelProviders.of(this).get(FeedViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_feed, container, false);



        fb.collection("sensors/z1QgZ1bVjYnUyrszlU9b/data")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(MainActivity.class.getName(), "Listen failed.", e);
                        return;
                    }

                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("rawHumidity") != null) {
                            Log.d(MainActivity.class.getName(), "Current sensor data: " + doc.get("rawHumidity"));
                        }
                    }

                }
            });


        setUpRecyclerView();

        return this.root;
    }

    private void setUpRecyclerView(){
        Query query = sensorDataRef.orderBy("rawHumidity", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<SensorData> options = new FirestoreRecyclerOptions.Builder<SensorData>()
                .setQuery(query, SensorData.class)
                .build();

        adapter = new SensorDataAdapter(options);

        RecyclerView recyclerView = this.root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
        recyclerView.setAdapter(adapter);

        Log.w(MainActivity.class.getName(), "Recyclier View setup completed.");
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}