package com.main.AccessMatrix;

public class AccessMatrixThread extends Thread {
    int[][] matrix;

    public AccessMatrixThread(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        yieldMultipleTimes();
    }

    public void yieldMultipleTimes() {
        int numYields = getRandom(3,8);
        for (int i = 0; i<numYields; i++) {
            currentThread().interrupt();
        }
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}