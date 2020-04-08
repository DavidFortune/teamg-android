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
import com.example.teamg_plantproject.Notification;
import com.example.teamg_plantproject.R;
import com.example.teamg_plantproject.SensorData;
import com.example.teamg_plantproject.SensorDataAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
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
    private RecyclerView nRecyclerView;
    private FirestoreRecyclerAdapter nAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        root = inflater.inflate(R.layout.fragment_feed, container, false);
        nRecyclerView = root.findViewById(R.id.firestore_list);
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        Query query = fb.collection("notifications").whereEqualTo("topic", auth.getUid()).orderBy("timestamp", Query.Direction.DESCENDING).limit(30);

        FirestoreRecyclerOptions<Notification> options = new FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification.class)
                .build();

        nAdapter = new FirestoreRecyclerAdapter<Notification, NotificationViewHolder>(options) {
            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().from(parent.getContext()).inflate(R.layout.fragment_feed_item, parent, false);
                return new NotificationViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull Notification model) {
                holder.list_body.setText(model.getNotification().get("body"));
                holder.list_title.setText(model.getNotification().get("title"));
                holder.list_timestamp.setText(model.getDate());
            }
        };


        nRecyclerView.setHasFixedSize(true);
        nRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        nRecyclerView.setAdapter(nAdapter);

        return root;
    }

    private class NotificationViewHolder extends RecyclerView.ViewHolder{

        private TextView list_title;
        private TextView list_body;
        private TextView list_timestamp;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            list_body = itemView.findViewById(R.id.list_body);
            list_title = itemView.findViewById(R.id.list_title);
            list_timestamp = itemView.findViewById(R.id.list_timestamp);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        nAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(nAdapter != null) {
            nAdapter.stopListening();
        }
    }
}