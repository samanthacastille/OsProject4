package com.main.AccessMatrix;

public class AccessMatrixThread extends Thread {
    int[][] matrix;
    int domains;
    int objects;

    public AccessMatrixThread(int[][] matrix, int domains, int objects) {
        this.matrix = matrix;
        this.domains = domains;
        this.objects = objects;
    }

    @Override
    public void run() {
        int numAttempts = getRandom(1,5);
        for (int i=0; i<numAttempts; i++) {
            int objectOrDomain = getRandom(1, (objects+domains+1));
            if (objectOrDomain<objects) {
                int action = getRandom(1,3);
                if (action==1) {
                    System.out.println(Thread.currentThread().getId() + ": Attempting to read from object " + objectOrDomain);
                    yieldMultipleTimes();
                } else if (action==2) {
                    System.out.println(Thread.currentThread().getName() + ": Attempting to write to object " + objectOrDomain);
                    yieldMultipleTimes();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + ": Attempting to switch to domain " + objectOrDomain);
                yieldMultipleTimes();
            }
        }
        yieldMultipleTimes();
    }

    public void yieldMultipleTimes() {
        int numYields = getRandom(3,8);
        System.out.println(Thread.currentThread().getName() + ": Yielding " + numYields + " times.");
        for (int i = 0; i<numYields; i++) {
            Thread.yield();
        }
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

    public boolean arbitrator(int column, int action) {
        int row = Integer.parseInt(Thread.currentThread().getName());
        
        return false;
    }

    public void read() {

    }

    public void write() {

    }
}