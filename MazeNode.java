package com.example.ilsar.mazeapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Ilsar on 29/10/2016.
 */
public class MazeNode extends View {

    private MazeWall up = null;
    private MazeWall down = null;
    private MazeWall left = null;
    private MazeWall right = null;
    private int nodeId;
    private UF ufWrapper;
    private boolean passed = false;
    private Context context;
    private Paint paint;
    private Maze maze;

    public MazeNode (int id, Maze maze, Context context){
        super(context);
        this.context = context;
        this.nodeId = id;
        this.maze = maze;
        ufWrapper = new UF(this);

        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
    }

    public UF getUF(){
        return this.ufWrapper;
    }

    public void removeWall(MazeWall wall){
        if (wall == this.up)
            this.up.setRemoved();
        else if (wall == this.down)
            this.down.setRemoved();
        else if (wall == this.left)
            this.left.setRemoved();
        else if (wall == this.right)
            this.right.setRemoved();
        else
            System.out.println("ERROR IN WALL REMOVAL");
        //TODO throws exepction
    }

    public boolean isPassed(){ return this.passed; }

    public void setPassed(boolean passed){
        this.passed = passed;
        invalidate();
        requestLayout();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize/6;
        int height = heightSize/6;

        int mes = Math.min(width, height);

        setMeasuredDimension(mes, mes);
        setMeasuredDimension(164, 164);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);


        //get the XY values
        float width =(float) this.getWidth();
        float x = this.getX();
        float y = this.getY();


        if(this.isPassed())
            this.setBackgroundColor(Color.RED);
        else
            this.setBackgroundColor(Color.WHITE);

        if (!this.up.isRemoved())
            canvas.drawLine(0, 0, canvas.getWidth(), 0, paint);
        //print right
        if (!this.right.isRemoved())
            canvas.drawLine(canvas.getWidth() ,0 , canvas.getWidth(), canvas.getWidth(), paint);
        //print bot
        if (!this.down.isRemoved())
            canvas.drawLine(0,canvas.getWidth(),canvas.getWidth(), canvas.getWidth(), paint);
        //print left
        if (!this.left.isRemoved())
            canvas.drawLine(0, 0, 0 ,canvas.getWidth(), paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        this.setPassed(true);
        this.invalidate();
        unify();
        if (checkWin()){
            winDialog();
            Log.e("LOGGER", "YOU WON");
        }


        return true;
    }


    public void resetUF(){ this.ufWrapper = new UF(this); }

    private void unify(){
        //up
        if (this.up.isRemoved()){
            MazeNode near =  up.getNear(this);
            if (near != null)
                if (near.isPassed())
                    UF.union(this.ufWrapper, near.getUF());
        }
        if (this.down.isRemoved()){
            MazeNode near =  down.getNear(this);
            if (near != null)
                if (near.isPassed())
                    UF.union(this.ufWrapper, near.getUF());
        }
        if (this.left.isRemoved()){
            MazeNode near =  left.getNear(this);
            if (near != null)
                if (near.isPassed())
                    UF.union(this.ufWrapper, near.getUF());
        }
        if (this.right.isRemoved()){
            MazeNode near =  right.getNear(this);
            if (near != null)
                if (near.isPassed())
                    UF.union(this.ufWrapper, near.getUF());
        }
    }

    private boolean checkWin(){
        return (UF.find(maze.entryNode().getUF()).getValue().getNodeId() == UF.find(maze.exitNode().getUF()).getValue().getNodeId());

    }

    public void winDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity) this.getContext());
        builder.setMessage("YOU ARE THE WINNER")
                .setTitle("YOU WINS");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.create().show();
    }

    public MazeWall getUpWall() {
        return up;
    }

    public void setUpWall(MazeWall up) {
        this.up = up;
    }


    public MazeWall getDownWall() {
        return down;
    }


    public void setDownWall(MazeWall down) {
        this.down = down;
    }


    public MazeWall getLeftWall() {
        return left;
    }


    public void setLeftWall(MazeWall left) {
        this.left = left;
    }


    public MazeWall getRightWall() {
        return right;
    }


    public void setRightWall(MazeWall right) {
        this.right = right;
    }


    public int getNodeId() {
        return this.nodeId;
    }


}
