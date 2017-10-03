package com.example.asanaapp;

/**
 * Created by Usser on 13.11.2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.Iterator;

public class LoggedInActivity extends AppCompatActivity
{
    public static BackendlessUser user;
    public static User appUser;
    public static boolean musicSwitch = true;
    final String TAG = "TABS";
    public static TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggedin);
        tv = (TextView)findViewById(R.id.textView);
        Backendless.Data.mapTableToClass("Poses", Poses.class);
        //retrieve the current user
        user = Backendless.UserService.CurrentUser();
        //if user is not null retrieve Poses that he already knows and store them in a custom User class
        if(user != null) {
            PoseObject[] knownPos = null;
            Object[] obj = (Object[]) user.getProperty("Pose");
            if (obj != null && obj.length > 0) {
                knownPos = new PoseObject[obj.length];
                Poses[] backendlessPoses = (Poses[])obj;
                for (int i = 0; i < obj.length; i++) {
                    Poses known = (Poses) obj[i];
                    PoseObject abc = new PoseObject(known.getPoses(), known.getImages(), known.getDifficulty(),known);
                    knownPos[i] = abc;
                }
                appUser = new User((String)user.getProperty("name"), (String)user.getProperty("gender"), (int)user.getProperty("xp"), knownPos, backendlessPoses);
            } else {
                appUser = new User((String)user.getProperty("name"), (String)user.getProperty("gender"), (int)user.getProperty("xp"), null, null);
            }

            tv.setText("Welcome, " + Backendless.UserService.CurrentUser().getProperty("name"));

        }
        //if the app is loaded for the first time for the current user, then load all images to the internal storage using AsyncTask
        boolean isFirst = (boolean)Backendless.UserService.CurrentUser().getProperty("InitialFlag");
        if(isFirst) {
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            QueryOptions queryOptions = new QueryOptions();
            queryOptions.setPageSize(85);
            queryOptions.setOffset(0);
            dataQuery.setQueryOptions(queryOptions);
            Backendless.Data.of(Poses.class).find(dataQuery,
                    new AsyncCallback<BackendlessCollection<Poses>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<Poses> response) {
                            // the "response" object is a collection of Person objects.
                            // each item in the collection represents an object from the "Person" table
                            Iterator<Poses> iterator = response.getCurrentPage().iterator();
                            int i = 0;
                            while (iterator.hasNext()) {
                                Poses pose = iterator.next();
                                LoadImagesBackground lib = new LoadImagesBackground(getApplicationContext(), String.valueOf(pose.getObjectId()));
                                i++;
                                lib.execute(pose.getImages());
                            }
                        }


                        @Override
                        public void handleFault(BackendlessFault fault) {
                            // use the getCode(), getMessage() or getDetail() on the fault object
                            // to see the details of the error
                        }
                    });
            LoggedInActivity.user.setProperty("InitialFlag",false);
            Backendless.UserService.update( LoggedInActivity.user, new AsyncCallback<BackendlessUser>()
            {
                public void handleResponse( BackendlessUser user )
                {


                }

                public void handleFault( BackendlessFault fault )
                {
                    // user update failed
                }
            });
        }
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        //if Logout button is pressed return to MainActivity
        findViewById( R.id.logoutButton ).setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Backendless.UserService.logout(new DefaultCallback<Void>( LoggedInActivity.this )
                {
                    @Override
                    public void handleResponse( Void response )
                    {
                        super.handleResponse( response );
                        startActivity( new Intent( getBaseContext(), MainActivity.class ) );
                        finish();
                    }
                } );
            }
        } );

    }



}

