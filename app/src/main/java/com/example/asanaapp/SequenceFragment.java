package com.example.asanaapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Usser on 13.11.2016.
 */
public class SequenceFragment extends Fragment implements View.OnClickListener {

    private String title;
    private int page;
    private ArrayList<PoseObject> pos;
    Button daily;
    Button abs;
    Button legs;
    Button arms;
    Button power;
    Button flex;
    String whereClause;
    int xpToAdd;
    String level;
    public static SequenceFragment newInstance() {
        Bundle args = new Bundle();
        SequenceFragment fragment = new SequenceFragment();
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
        View view = inflater.inflate(R.layout.fragment_seq, container, false);
        abs = (Button)view.findViewById(R.id.button2);
        abs.setOnClickListener(this);
        legs = (Button)view.findViewById(R.id.button3);
        legs.setOnClickListener(this);
        arms = (Button)view.findViewById(R.id.button4);
        arms.setOnClickListener(this);
        power = (Button)view.findViewById(R.id.button5);
        power.setOnClickListener(this);
        flex = (Button)view.findViewById(R.id.button6);
        flex.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        //LoggedInActivity.appUser.setXp((int)Backendless.UserService.CurrentUser().getProperty("xp"));
        level = LoggedInActivity.appUser.getLevel();
        if(view.equals(abs)) {
            whereClause = "Sequence LIKE '%abs" + level + "%'";
            if(level.equals(1))
                xpToAdd = 100;
            else if(level.equals(2))
                xpToAdd = 150;
            else
                xpToAdd = 200;
        }
         else if(view.equals(legs)) {
            whereClause = "Sequence LIKE '%legs" + level + "%'";
            if(level.equals(1))
                xpToAdd = 60;
            else if(level.equals(2))
                xpToAdd = 80;
            else
                xpToAdd = 100;
        }
        else if(view.equals(arms)) {
            whereClause = "Sequence LIKE '%arms" + level + "%'";
            if(level.equals(1))
                xpToAdd = 60;
            else if(level.equals(2))
                xpToAdd = 80;
            else
                xpToAdd = 100;
        }
        else if(view.equals(power)) {
            System.out.println("POWER is pressed " + level);
            whereClause = "Sequence LIKE '%power" + level + "%'";
            if(level.equals(1))
                xpToAdd = 150;
            else if(level.equals(2))
                xpToAdd = 200;
            else
                xpToAdd = 250;
        }
        if(view.equals(flex)) {
            whereClause = "Sequence LIKE '%flex" + level + "%'";
            if(level.equals(1))
                xpToAdd = 60;
            else if(level.equals(2))
                xpToAdd = 80;
            else
                xpToAdd = 100;
        }
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        Backendless.Persistence.of( Poses.class ).find( dataQuery,
                new AsyncCallback<BackendlessCollection<Poses>>(){
                    @Override
                    public void handleResponse( BackendlessCollection<Poses> found )
                    {
                        pos = new ArrayList<>();
                        Iterator<Poses> iterator=found.getCurrentPage().iterator();

                        int i = 0;
                        while( iterator.hasNext() )
                        {
                            Poses pose=iterator.next();
                            PoseObject obj = new PoseObject(pose.getPoses(),pose.getImages(),pose.getDifficulty(), pose);
                            pos.add(i,obj);
                            i++;
                        }
                        int seqNum = (Integer)LoggedInActivity.user.getProperty("sequences") + 1;
                        xpToAdd = (Integer) LoggedInActivity.user.getProperty("xp") + xpToAdd;
                        LoggedInActivity.user.setProperty("xp",xpToAdd);
                        LoggedInActivity.user.setProperty("sequences",seqNum);
                        Backendless.UserService.update( LoggedInActivity.user, new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {

                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // user update failed, to get the error code call fault.getCode()
                            }
                        });
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("obj", pos);
                        Intent in = new Intent(getActivity(),PracticeActivity.class);
                        in.putParcelableArrayListExtra("obj",pos);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                    }
                });
    }
}
