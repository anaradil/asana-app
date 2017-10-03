package com.example.asanaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Usser on 13.11.2016.
 */
public class AsanasFragment extends Fragment {
    final String TAG = "ASANA_FRAGMENT";
    private Button beginner;
    private Button inter;
    private Button advanced;

    public static AsanasFragment newInstance() {
        Bundle args = new Bundle();
        AsanasFragment fragment = new AsanasFragment();
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

        View view = inflater.inflate(R.layout.fragment_asanas, container, false);
        beginner = (Button)view.findViewById(R.id.beginner);
        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Beginner is selected");
                Intent i = new Intent(getActivity(), BeginnerAsanas.class);
                i.putExtra("Level",1);
                startActivity(i);
            }
        });
        inter = (Button)view.findViewById(R.id.intermed);
        inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Intermediate is selected");
                Intent i = new Intent(getActivity(), BeginnerAsanas.class);
                i.putExtra("Level",2);
                startActivity(i);
            }
        });
        advanced = (Button)view.findViewById(R.id.advanced);
        advanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Advanced is selected");
                Intent i = new Intent(getActivity(), BeginnerAsanas.class);
                i.putExtra("Level",3);
                startActivity(i);
            }
        });
        return view;
    }


}
