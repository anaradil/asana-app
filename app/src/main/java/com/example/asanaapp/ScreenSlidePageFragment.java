package com.example.asanaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Usser on 14.11.2016.
 */
public class ScreenSlidePageFragment extends Fragment {
    int pageNum;
    ArrayList<PoseObject> poses;
    static ScreenSlidePageFragment newInstance(int page, ArrayList<PoseObject> p) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt("Number",page);
        args.putParcelableArrayList("Poses", p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = getArguments().getInt("Number");
        poses = getArguments().getParcelableArrayList("Poses");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_practice,container,false);
        TextView tv = (TextView)rootView.findViewById(R.id.poseDes);
        tv.setMovementMethod(new ScrollingMovementMethod());
        ImageView iv = (ImageView)rootView.findViewById(R.id.posePicture);

        for(int i = 0; i < poses.size();i++) {
            if(pageNum == i) {
                tv.setText(poses.get(i).getDetail());
                try {
                    FileInputStream inputStream = getActivity().getApplicationContext().openFileInput(poses.get(i).getObjectId());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    iv.setImageBitmap(bitmap);
                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        return rootView;
    }
}

