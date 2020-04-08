package com.example.teamg_plantproject.ui.feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.teamg_plantproject.R;

import java.util.ArrayList;

public class FeedAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<String> datemessage;
    private ArrayList<String> notificationmessage;
    LayoutInflater inflater;
    public FeedAdapter(Context context, ArrayList<String> date, ArrayList<String> notification) {

        this.context = context;
        this.datemessage = date;
        this.notificationmessage = notification;


    }

    @Override
    public int getCount() {
        return notificationmessage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(context).inflate(R.layout.activity_feed_list, parent, false);
        TextView textviewnotification = convertView.findViewById(R.id.textView_notificationmessage);
        TextView textviewdatecreated = convertView.findViewById(R.id.textView_datecreated);

        Log.d("TAG", "getView:  date"+datemessage.get(position));
        textviewnotification.setText(notificationmessage.get(position));
        textviewdatecreated.setText(datemessage.get(position));

        return convertView;

    }
}
