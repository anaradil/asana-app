package com.example.asanaapp;

/**
 * Created by Usser on 13.11.2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RegisteredActivity extends Activity
{

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
    }

    @Override
    public void onBackPressed()
    {
        setResult( RESULT_OK, new Intent() );
        finish();
    }
}
