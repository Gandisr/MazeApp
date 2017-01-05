package com.example.ilsar.mazeapp;

/**
 * Created by Ilsar on 29/10/2016.
 */
public class UF {

    private UF father;
    private int rank = 0;
    private MazeNode value;

    public UF(MazeNode value){
        this.value = value;
        this.father = this;
    }

    public static UF find(UF x){

        if (x.getFather() != x){
            //go up the tree and compress the path
            x.setFather(UF.find(x.getFather()));
        }
        return x.getFather();
    }


    public static void link(UF x, UF y){
        if (x == y)
            return;
        if (x.getRank() > y.getRank())
            y.setFather(x);
        else{
            x.setFather(y);
            if (x.getRank() == y.getRank()){
                y.setRank( y.getRank() +1);
            }
        }
    }


    public static void union (UF x, UF y){
        UF.link(UF.find(x), UF.find(y));
    }

    private UF getFather() {
        return father;
    }

    private void setFather(UF father) {
        this.father = father;
    }

    private int getRank() {
        return rank;
    }

    private void setRank(int rank) {
        this.rank = rank;
    }

    public MazeNode getValue() {
        return value;
    }
}
