package com.example.asanaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Usser on 14.11.2016.
 */
public class ShowDetail extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail);
        tv = (TextView)findViewById(R.id.detail);
        tv.setText(getIntent().getStringExtra("Detail"));
    }
}
