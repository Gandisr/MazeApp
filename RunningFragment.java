package com.example.ilsar.mazeapp;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class RunningFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "size";

    // TODO: Rename and change types of parameters
    private int size;


    public RunningFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            size = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final String LOG_TAG = RunningFragment.class.getSimpleName();

        View rootView = inflater.inflate(R.layout.fragment_running, container, false);

        RelativeLayout myLayout = (RelativeLayout) rootView.findViewById(R.id.runningFragment);

        Maze maze = new Maze(size, this.getContext());

        int ww = (myLayout.getWidth()/(size + 2));
        int hh = (myLayout.getHeight()/(size + 2));

        int mes = Math.min(ww, hh); //mesure of the node
        int nodeId = 1;

        // add the nodes
        MazeNode leftNode = null;
        MazeNode topNode = null;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                MazeNode node = maze.getTheMaze()[i][j];
                node.setId(nodeId);
                nodeId++;

                if (i == 0 && j == 0) {
                    topNode = node;
                    leftNode = node;
                    params.leftMargin = mes;
                    params.topMargin = mes;
                } else {
                    if (i == 0)
                        params.topMargin = mes;
                    if (j == 0) {
                        params.leftMargin = mes;
                        params.addRule(RelativeLayout.BELOW, topNode.getId());
                        topNode = node;
                        leftNode = node;
                    } else {
                        params.addRule(RelativeLayout.RIGHT_OF, leftNode.getId());
                        leftNode = node;
                    }
                }
                node.setBackgroundColor(Color.WHITE);
                myLayout.addView(node, params);
            }
        }

        return rootView;
    }

}
