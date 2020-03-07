package com.example.teamg_plantproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.teamg_plantproject.DatabaseHelper;
import com.example.teamg_plantproject.Plant;
import com.example.teamg_plantproject.PlantDialog;
import com.example.teamg_plantproject.PlantListViewAdapter;
import com.example.teamg_plantproject.R;
import com.example.teamg_plantproject.SensorActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DatabaseHelper db;
    private PlantListViewAdapter plantListViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fabPlant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });

        final ListView plantList = root.findViewById(R.id.plat_listview);
        db = new DatabaseHelper(getContext());
        ArrayList<Plant> plantArrayList = db.getAllPlants();
        plantListViewAdapter = new PlantListViewAdapter(this.getContext(), plantArrayList);
        plantListViewAdapter.notifyDataSetChanged();
        plantList.setAdapter(plantListViewAdapter);

        return root;
    }

    public void showDialog(View view) {

        FragmentManager fragmentManager = getFragmentManager();
        PlantDialog myPlantDialog = new PlantDialog();
        myPlantDialog.show(fragmentManager, "Plant create");
    }
}