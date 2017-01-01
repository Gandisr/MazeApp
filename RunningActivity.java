package com.example.ilsar.mazeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RunningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        Bundle bundle = new Bundle();
        bundle.putInt("size", Integer.parseInt(getIntent().getExtras().getString(Intent.EXTRA_TEXT)));

        RunningFragment runningFragment = new RunningFragment();
        runningFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(
                android.R.id.content, runningFragment).commit();
    }
}
