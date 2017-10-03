package com.example.asanaapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.text.DecimalFormat;

/**
 * Created by Usser on 13.11.2016.
 */
public class StatisticsFragment extends Fragment {
    private String title;
    private int page;

    public static StatisticsFragment newInstance() {
        Bundle args = new Bundle();
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat, container, false);
        TextView levelView = (TextView)view.findViewById(R.id.level);
        TextView asanaNum = (TextView)view.findViewById(R.id.asanasNum);
        TextView total = (TextView)view.findViewById(R.id.totalPracticed);
        TextView progress = (TextView)view.findViewById(R.id.progress);
        TextView xp= (TextView)view.findViewById(R.id.favAreas);
        Backendless.Data.mapTableToClass("Poses", Poses.class);
        BackendlessUser user = Backendless.UserService.CurrentUser();
        xp.setText("XP: " + user.getProperty("xp").toString());
        total.setText("Total Practiced Sequences: " + String.valueOf(user.getProperty("sequences")));
        int xpInt = (Integer)user.getProperty("xp");
        String level = null;
        if(xpInt < 300)
            level = "Beginner";
        else if(xpInt >=300 && xpInt < 700)
            level = "Intermediate";
        else
            level = "Advanced";
        levelView.setText("Level: " + level);
        Object[] obj = (Object[]) user.getProperty("Pose");
        if(obj != null) {
            int l = obj.length;
            asanaNum.setText("Number of Asanas Completed: " + String.valueOf(l));
            double prog = (double)l/(double)81 * 100.00;
            DecimalFormat df = new DecimalFormat("#.##");
            progress.setText("Total Progress: " + String.valueOf(df.format(prog)) + "%");
        } else {
            asanaNum.setText("Number of Asanas Completed: " + String.valueOf(0));
            progress.setText("Total Progress: " + String.valueOf(0) + "%");
        }

        return view;
    }
}
