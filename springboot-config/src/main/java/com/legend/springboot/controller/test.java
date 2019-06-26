package com.legend.springboot.controller;

public class test {

    public static boolean tr() {
        int num = 2;
        if ((num&1) != 1){
            return false;
        }

        if ((num&2) != 2){
            return false;
        }

        if ((num&4) != 4){
            return false;
        }

        if ((num&8) != 8){
            return false;
        }
        return true;
    }

    public static void main(String[] args){
        boolean tr = tr();
        System.out.println(tr);
    }
}
