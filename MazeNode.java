package com.example.ilsar.mazeapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    public MazeNode (int id, Context context){
        super(context);
        this.context = context;
        this.nodeId = id;
        ufWrapper = new UF(this);

        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
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
        Log.e("LOGGER", "x = " + x);
        Log.e("LOGGER", "y = " + y);

        Log.e("LOGGER", "can x = " + canvas.getClipBounds().centerX());
        if(this.isPassed())
            this.setBackgroundColor(Color.RED);
        else
            this.setBackgroundColor(Color.WHITE);

        if (this.up != null)
            canvas.drawLine(0, 0, canvas.getWidth(), 0, paint);
        //print right
        if (this.right != null)
            canvas.drawLine(canvas.getWidth() ,0 , canvas.getWidth(), canvas.getWidth(), paint);
        //print bot
        if (this.down != null)
            canvas.drawLine(0,canvas.getWidth(),canvas.getWidth(), canvas.getWidth(), paint);
        //print left
        if (this.left != null)
            canvas.drawLine(0, 0, 0 ,canvas.getWidth(), paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        this.setPassed(true);
        this.invalidate();
        return true;
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
