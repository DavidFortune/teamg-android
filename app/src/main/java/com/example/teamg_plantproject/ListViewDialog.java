package com.example.teamg_plantproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ListViewDialog extends DialogFragment {

    private Button delete;
    private DatabaseHelper db;
    private int plantID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_listview, container, false);

        db = new DatabaseHelper(getContext());
        delete = view.findViewById(R.id.delete_listview_option);
        plantID = getArguments().getInt("PlantID");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletePlant(plantID);
                getActivity().startActivityForResult(getActivity().getIntent(), 10);
                dismiss();
            }
        });

        return view;
    }
}
