package com.example.ilsar.mazeapp;

/**
 * Created by Ilsar on 29/10/2016.
 */
public class MazeWall {

    private MazeNode side1 = null;
    private MazeNode side2 = null;
    private boolean removed = false;

    public MazeWall(MazeNode side1, MazeNode side2){
        this.side1 = side1;
        this.side2 = side2;
    }

    public boolean isBorder(){
        if (this.side1 == null || this.side2 == null)
            return true;
        return false;
    }

    public MazeNode getSide1() {
        return side1;
    }

    public MazeNode getSide2() {
        return side2;
    }

    public void setRemoved(){ this.removed = true; }

    public boolean isRemoved() { return this.removed; }

    public MazeNode getNear(MazeNode node){
        if (node == side1)
            return side2;
        else
            return side1;
    }

    @Override
    public String toString(){
        String part1 = "border";
        String part2 = "border";
        if (side1 != null)
            part1 = Integer.toString(side1.getId());
        if (side2 != null)
            part2 = Integer.toString(side2.getId());
        return (part1 + "  " + part2);
    }
}
