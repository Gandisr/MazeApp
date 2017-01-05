package com.example.ilsar.mazeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void generateMaze(View view){
        int size = 5;

        Intent generateIntent = new Intent(this, RunningActivity.class)
               .putExtra(Intent.EXTRA_TEXT, Integer.toString(size));
        startActivity(generateIntent);
    }

}
