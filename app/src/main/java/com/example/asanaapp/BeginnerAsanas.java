package com.example.asanaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Usser on 13.11.2016.
 */
public class BeginnerAsanas extends AppCompatActivity{
    final String TAG = "BEGINNER_ASANAS";
    PoseObject[] pos;
    ListView lv;
    ListAdapter adapter;
    String whereClause;
    ArrayList<Poses> setKnownPoses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginner_asanas);
        Bundle extras = getIntent().getExtras();
        //retrieve known poses from backendless and store them
        int level = extras.getInt("Level");
        lv = (ListView)findViewById(R.id.listView);
        System.out.println("Level is " + level);
        BackendlessUser user = Backendless.UserService.CurrentUser();
        if (user.getProperty("Pose") != null) {
            Object[] obj = (Object[]) user.getProperty("Pose");
            if (obj != null && obj.length > 0) {
                Poses[] backendlessPoses = (Poses[]) obj;
                setKnownPoses = new ArrayList<>(Arrays.asList(backendlessPoses));
            }
        }
        if(level == 1)
            whereClause = "Difficulty = 1";
        else if(level == 2)
            whereClause = "Difficulty = 2";
        else if(level == 3)
            whereClause = "Difficulty = 3";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize( 85 );
        queryOptions.setOffset( 0 );
        dataQuery.setQueryOptions( queryOptions );
        Backendless.Persistence.of( Poses.class ).find( dataQuery,
                new AsyncCallback<BackendlessCollection<Poses>>(){
                    @Override
                    public void handleResponse( BackendlessCollection<Poses> foundContacts )
                    {

                        System.out.println(foundContacts.getTotalObjects());
                        pos = new PoseObject[foundContacts.getTotalObjects()];

                            Iterator<Poses> iterator = foundContacts.getCurrentPage().iterator();
                            int i = 0;

                            while (iterator.hasNext()) {
                                Poses pose = iterator.next();
                                //if user knows the pose set known=true
                                PoseObject obj = new PoseObject(pose.getPoses(), pose.getImages(), pose.getDifficulty(), pose);
                                if(setKnownPoses.contains(pose)) {
                                    obj.setKnown();
                                }
                                pos[i] = obj;
                                i++;
                            }
                        adapter = new ListAdapter(BeginnerAsanas.this,0,pos,lv);
                        lv.setAdapter(adapter);


                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred
                    }
                });


    }


}
