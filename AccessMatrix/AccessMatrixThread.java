package com.main.AccessMatrix;

public class AccessMatrixThread extends Thread {
    int[][] matrix;
    int domains;
    int objects;
    String filePathList[];

    public AccessMatrixThread(int[][] matrix, int domains, int objects, String filePathList[]) {
        this.matrix = matrix;
        this.domains = domains;
        this.objects = objects;
        this.filePathList = filePathList;
    }

    @Override
    public void run() {
        boolean accessGranted;
        int numAttempts = getRandom(1,5);
        for (int i=0; i<numAttempts; i++) {
            int objectOrDomain = getRandom(0, (objects+domains));
            if (objectOrDomain<objects) {
                int action = getRandom(1,3);
                if (action==1) {
                    System.out.println("Thread " + Thread.currentThread().getName() + ": Attempting to read from object " + objectOrDomain);
                    accessGranted = objectArbitrator(objectOrDomain, action);
                    if (accessGranted) {
                        System.out.println("Thread " + Thread.currentThread().getName() + ": Access granted!");
                        read();
                    } else {
                        System.out.println("Thread " + Thread.currentThread().getName() + ": Access denied.");
                    }
                    yieldMultipleTimes();
                } else if (action==2) {
                    System.out.println("Thread " + Thread.currentThread().getName() + ": Attempting to write to object " + objectOrDomain);
                    accessGranted = objectArbitrator(objectOrDomain, action);
                    if (accessGranted) {
                        System.out.println("Thread " + Thread.currentThread().getName() + ": Access granted!");
                        write();
                    } else {
                        System.out.println("Thread " + Thread.currentThread().getName() + ": Access denied.");
                    }
                    yieldMultipleTimes();
                }
            } else {
                System.out.println("Thread " + Thread.currentThread().getName() + ": Attempting to switch to domain " + (objectOrDomain-objects));
                accessGranted = domainSwitchingArbitrator(objectOrDomain);
                if (accessGranted) {
                    System.out.println("Thread " + Thread.currentThread().getName() + ": Access granted!");
                    // TODO: figure out how a context switch works
                } else {
                    System.out.println("Thread " + Thread.currentThread().getName() + ": Access denied.");
                }
                yieldMultipleTimes();
            }
        }
        yieldMultipleTimes();
    }

    public void yieldMultipleTimes() {
        int numYields = getRandom(3,8);
        System.out.println("Thread " + Thread.currentThread().getName() + ": Yielding " + numYields + " times.");
        for (int i = 0; i<numYields; i++) {
            Thread.yield();
        }
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

    public boolean objectArbitrator(int column, int action) {
        int row = Integer.parseInt(Thread.currentThread().getName());
        int permission = matrix[row][column];
        return permission == action || permission == 3;
    }

    public boolean domainSwitchingArbitrator(int column) {
        int row = Integer.parseInt(Thread.currentThread().getName());
        int permission = matrix[row][column];
        return permission == 1;
    }

    public void read() {

    }

    public void write() {

    }
}