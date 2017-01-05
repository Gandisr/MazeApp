package com.example.ilsar.mazeapp;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ilsar on 29/10/2016.
 */
public class Maze {

    protected static int size;
    private MazeNode[][]theMaze;
    private UF[][] mazeUF;
    private List<MazeWall> wallsList = new ArrayList<MazeWall>();
    private Context context;


    public Maze(int size, Context context){
        this.size = size;
        this.theMaze = new MazeNode[size][size];
        this.mazeUF = new UF[size][size];
        this.context = context;

        this.mazeInit();
    }

    public void mazeInit(){
        this.nodeInit();
        this.wallInit();
        this.mazeRandomizer();
        this.resetUF();
    }

    private void nodeInit(){
        int id = 1;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                this.theMaze[i][j] = new MazeNode(id, context);
                this.theMaze[i][j].generateViewId();
                id++;
            }
        }
    }

    private void wallInit(){

        MazeWall leftWall;
        MazeWall upWall;
        MazeWall rightWall;
        MazeWall downWall;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                //left for the left border
                if (j ==0){
                    leftWall = new MazeWall(null, theMaze[i][j]);
                    theMaze[i][j].setLeftWall(leftWall);
                    wallsList.add(leftWall);
                }

                //add the up
                if (i == 0){
                    upWall = new MazeWall(null, theMaze[i][j]);
                    theMaze[i][j].setUpWall(upWall);
                    wallsList.add(upWall);
                }
                else{
                    upWall = new MazeWall(theMaze[i-1][j], theMaze[i][j]);
                    theMaze[i][j].setUpWall(upWall);
                    theMaze[i-1][j].setDownWall(upWall);
                    wallsList.add(upWall);

                }

                //add the right
                if (j < (size-1)){
                    rightWall = new MazeWall(theMaze[i][j], theMaze[i][j+1]);
                    theMaze[i][j].setRightWall(rightWall);
                    theMaze[i][j+1].setLeftWall(rightWall);
                    wallsList.add(rightWall);
                }
                else{
                    rightWall = new MazeWall(theMaze[i][j], null);
                    theMaze[i][j].setRightWall(rightWall);
                    wallsList.add(rightWall);
                }

                //add bottom for the bottom border
                if (i == (size - 1)){
                    downWall = new MazeWall(theMaze[i][j], null );
                    theMaze[i][j].setDownWall(downWall);
                    wallsList.add(downWall);
                }
            }
        }
    }

    private void mazeRandomizer(){

        //shuffle the list
        Collections.shuffle(wallsList);

        //create UF set on each of the MazeNoded
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                this.mazeUF[i][j] = new UF(this.theMaze[i][j]);
            }
        }

        for (MazeWall wall: this.wallsList){ //Iterate the walls at random order
            if (!wall.isBorder()){ //we remove only the walls inside
                //check if both sides are the same
                if (UF.find(wall.getSide1().getUF()).getValue().getNodeId() != UF.find(wall.getSide2().getUF()).getValue().getNodeId() ){
                    // Union the two sides

                    UF.union(wall.getSide1().getUF(), wall.getSide2().getUF());
                    //remove from the maze
                    wall.getSide1().removeWall(wall);
                    wall.getSide2().removeWall(wall);
                }
            }
        }

        //create the entry and exit points
        Random rand = new Random();
        boolean vertical = rand.nextBoolean();
        if (vertical){
            int locTop = rand.nextInt(size);
            int locBot = rand.nextInt(size);
            theMaze[0][locTop].removeWall(theMaze[0][locTop].getUpWall());
            theMaze[size-1][locBot].removeWall(theMaze[size-1][locBot].getDownWall());
        } else{
            int locLeft = rand.nextInt(size);
            int locRight = rand.nextInt(size);
            theMaze[locLeft][0].removeWall(theMaze[locLeft][0].getLeftWall());
            theMaze[locRight][size-1].removeWall(theMaze[locRight][size-1].getRightWall());
        }

    }

    private void resetUF(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                this.mazeUF[i][j] = new UF(this.theMaze[i][j]);
                theMaze[i][j].resetUF();
            }
        }

    }

    public int getSize() {
        return size;
    }

    public MazeNode[][] getTheMaze() {
        return theMaze;
    }

    public List<MazeWall> getWallsList() {
        return wallsList;
    }
}
