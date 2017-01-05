package com.example.ilsar.mazeapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RunningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        int size = Integer.parseInt(getIntent().getExtras().getString(Intent.EXTRA_TEXT));

        RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.runningActivity);
        Maze maze = new Maze(size, this);

        int ww = (myLayout.getWidth()/(size + 2));
        int hh = (myLayout.getHeight()/(size + 2));

        int mes = Math.min(ww, hh); //mesure of the node


        MazeNode node = null;
        int nodeId = 1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                node = maze.getTheMaze()[i][j];
                node.setId(nodeId);
                nodeId++;

                if (i == 0 && j == 0) {

                    params.leftMargin = mes;
                    params.topMargin = mes;
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                } else {
                    if (i == 0){
                        params.topMargin = mes;
                        params.addRule(RelativeLayout.RIGHT_OF, maze.getTheMaze()[i][j-1].getId());

                    }
                    else{
                        if (j == 0) {
                            params.leftMargin = mes;
                            params.addRule(RelativeLayout.BELOW,  maze.getTheMaze()[i-1][j].getId());

                        } else {
                            params.addRule(RelativeLayout.RIGHT_OF,  maze.getTheMaze()[i][j-1].getId());
                            params.addRule(RelativeLayout.BELOW,  maze.getTheMaze()[i-1][j].getId());
                        }
                    }
                }
                node.setLayoutParams(params);
                myLayout.addView(node);

                node.resetUF();
            }
        }




/*
        Bundle bundle = new Bundle();
        bundle.putInt("size", Integer.parseInt(getIntent().getExtras().getString(Intent.EXTRA_TEXT)));

        RunningFragment runningFragment = new RunningFragment();
        runningFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(
                android.R.id.content, runningFragment).commit();
                */
    }
}
