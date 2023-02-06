package com.qcl.datastructure.sort;

/**
 * 二分法
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/4
 */
public class BinarySearch {

    public static int binarySearch(int[] arr, int low, int high, int key) {
        int mid = (low + high) / 2;
        while (low <= high) {
            if (arr[mid] < key) {
                low = mid + 1;
            } else if (arr[mid] == key) {
                return mid;
            } else {
                high = mid - 1;
            }
            mid = (low + high) / 2;
        }
        if (low > high) {
            return -1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {8, 13, 17, 30, 44, 56, 88, 97};
        int key = 48;
        int n = arr.length - 1;
        int index = binarySearch(arr, 0, n, key);
        System.out.println("The sorted array is: ");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("\nElement to be searched: " + key);
        if (index == -1) {
            System.out.println("Unfortunately the Element is not found!");
        } else {
            System.out.println("The Element is found at the index: " + index);
        }
    }
}