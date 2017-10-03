package com.example.asanaapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Usser on 13.11.2016.
 */
public class SettingsFragment extends Fragment{
    private String title;
    private int page;

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        Switch music = (Switch)view.findViewById(R.id.switch1);
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    LoggedInActivity.musicSwitch = true;
                else
                    LoggedInActivity.musicSwitch = false;
            }
        });
        Button defProg = (Button)view.findViewById(R.id.defProgress);
        defProg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoggedInActivity.user.setProperty("xp", 0);
                LoggedInActivity.user.setProperty("Pose", null);
                LoggedInActivity.user.setProperty("sequences",0);
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
            }
        });
        Button chName = (Button)view.findViewById(R.id.chUsername);
        chName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());
                alert.setMessage("Enter New Username");
                alert.setTitle("Change Username");
                alert.setView(edittext);
                alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //OR
                        final String newName = edittext.getText().toString();
                        LoggedInActivity.user.setProperty("name",newName);
                        Backendless.UserService.update( LoggedInActivity.user, new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {
                                LoggedInActivity.tv.setText("Welcome, " + newName);
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // user update failed, to get the error code call fault.getCode()
                            }
                        });
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });
                alert.show();
            }
        });
        Button chPassword = (Button)view.findViewById(R.id.chPassword);
        chPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                final EditText edittext = new EditText(getActivity());
                alert.setMessage("Enter New Password");
                alert.setTitle("Change Password");
                alert.setView(edittext);
                alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        //OR
                        final String newName = edittext.getText().toString();
                        LoggedInActivity.user.setProperty("password",newName);
                        Backendless.UserService.update( LoggedInActivity.user, new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {
                                //LoggedInActivity.tv.setText("Welcome, " + newName);
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                // user update failed, to get the error code call fault.getCode()
                            }
                        });
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });
                alert.show();
            }
        });
        return view;
    }
}
