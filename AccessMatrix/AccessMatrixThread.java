package com.main.AccessMatrix;

import java.util.Arrays;

public class AccessMatrixThread extends Thread {
    int[][] matrix;

    public AccessMatrixThread(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " in control");
        for (int[] strings : matrix) {
            System.out.println(Arrays.toString(strings));
        }
        yieldMultipleTimes();
    }

    public void yieldMultipleTimes() {
        for (int i = 0; i<getRandom(3,8); i++) {
            currentThread().interrupt();
        }
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}