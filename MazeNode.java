package com.example.ilsar.mazeapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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


    public MazeNode (int id, Context context){
        super(context);
        this.nodeId = id;
        ufWrapper = new UF(this);
    }

    public UF getUF(){
        return this.ufWrapper;
    }

    public void removeWall(MazeWall wall){
        if (wall == this.up)
            this.up = null;
        else if (wall == this.down)
            this.down = null;
        else if (wall == this.left)
            this.left = null;
        else if (wall == this.right)
            this.right = null;
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

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int mes = (Math.min(parentHeight, parentWidth))/(Maze.size+2);
        this.setMeasuredDimension(mes,mes);
        this.setLayoutParams(new RelativeLayout.LayoutParams(mes ,mes));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        //get the XY values
        int width = this.getWidth();
        float x = this.getX();
        float y = this.getY();
        Log.e("LOGGER", "x = " + x);
        Log.e("LOGGER", "y = " + y);

        //print top
        if (this.up != null)
            canvas.drawLine(x, y, x+width, y, paint);
        //print right
        if (this.right != null)
            canvas.drawLine(x+width, y, x+width, y+width, paint);
        //print bot
        if (this.down != null)
            canvas.drawLine(x, y+width, x+width, y+width, paint);
        //print left
        if (this.left != null)
            canvas.drawLine(x, y, x, y+width, paint);
    }

    public void resetUF(){ this.ufWrapper = new UF(this); }

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
