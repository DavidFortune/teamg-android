package com.example.teamg_plantproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DatabaseHelper db;
    private PlantListViewAdapter plantListViewAdapter;
    private ImageView plantPicture;
    private Button addImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
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