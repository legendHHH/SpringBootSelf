package com.qcl.datastructure.sort;

import java.util.Arrays;

public class InsertSort {

    public static void insertSort(String[] array) {
        for (int i = 0; i < array.length; i++) {
            String tmp = array[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (array[j].compareTo(tmp) > 0) {
                    array[j + 1] = array[j];
                } else {
                    // array[j+1]=tmp;
                    break;
                }
            }
            array[j + 1] = tmp;
        }
    }

    public static void main(String[] args) {
        String[] array = {"d", "c", "v", "a"};
        insertSort(array);
        System.out.println(Arrays.toString(array));
    }
}
