package com.example.teamg_plantproject.ui.dashboard;

import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DatabaseHelper db;
    private PlantListViewAdapter plantListViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
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