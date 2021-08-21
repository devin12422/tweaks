package com.devin12422.tweaks.util;

public class Pair <L, R> {
    L left;
    R right;

    public Pair(){
        this.left = null;
        this.right = null;
    }
    public Pair(L left, R right){
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }
    public R getRight() {
        return right;
    }

    public Pair<L, R> setLeft(L left){
        this.left = left;
        return this;
    }
    public Pair<L, R> setRitht(R right){
        this.right = right;
        return this;
    }
}