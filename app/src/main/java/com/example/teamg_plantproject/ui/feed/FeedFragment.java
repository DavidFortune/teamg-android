package com.example.teamg_plantproject.ui.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class FeedFragment extends Fragment {

    protected View root;
    private FeedViewModel feedViewModel;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference sensorDataRef;
    private DocumentReference sensorDataObjectRef;
    private SensorDataAdapter adapter;
    private TextView textView;
    private ListView notificationlist;
    private FeedAdapter feedAdapter ;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedViewModel =
                ViewModelProviders.of(this).get(FeedViewModel.class);
        root = inflater.inflate(R.layout.fragment_feed, container, false);

        textView = root.findViewById(R.id.text_notification_data);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String profileUID = auth.getCurrentUser().getUid();
        sensorDataRef = fb.collection("notifications");
        //  fb.collection("notifications/"+profileUID+"/");
        fb.document("notifications/" + profileUID + "/");
        sensorDataRef.orderBy("topic", Query.Direction.DESCENDING)
                //.whereArrayContains("topic",profileUID)
                .limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(MainActivity.class.getName(), "Listen failed.", e);
                            return;
                        }

                        String notificationbody = "";
                        Log.d("TAG", "onEvent: value " + value + "   ");
                        ArrayList<String> arrayofdates=new ArrayList<String>();;
                        ArrayList<String> arrayofnotifications=new ArrayList<String>();;
                        for (QueryDocumentSnapshot doc : value) {
                            Log.d("TAG", "onEvent: PASSING THROUGH NOTIFICATIONS ");
                            Map data = doc.getData();
                            Map notification = (Map) data.get("notification");
                            notificationbody = notificationbody + notification.get("body") + "\n";
                            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            Timestamp timestamp = (Timestamp) doc.get("timestamp");
                            String simpleformat = sfd.format(timestamp.toDate());
                            String bodytext = (notification.get("body")).toString();

                          //  Log.d("TAG", "what's the timestamp: " + sfd.format(timestamp.toDate()));
                            Log.d("TAG", "onCreateView: "+ (notification.get("body")).toString());

                            arrayofdates.add(simpleformat);
                            arrayofnotifications.add(bodytext);

                        }
                        feedAdapter = new FeedAdapter(root.getContext(), arrayofdates, arrayofnotifications);
                        // textView.setText(notificationbody);
                        notificationlist = root.findViewById(R.id.listview_activityfeed);


//        feedAdapter.notifyDataSetChanged();
                        notificationlist.setAdapter(feedAdapter);
                        //setUpRecyclerView();
                    }
                });


        return root;
    }

    private void setUpRecyclerView() {
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