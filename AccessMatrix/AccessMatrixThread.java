package com.main.AccessMatrix;

public class AccessMatrixThread extends Thread {
    int[][] matrix;
    int domains;
    int objects;
    String[] filePathList;

    public AccessMatrixThread(int[][] matrix, int domains, int objects, String[] filePathList) {
        this.matrix = matrix;
        this.domains = domains;
        this.objects = objects;
        this.filePathList = filePathList;
    }

    @Override
    public void run() {
        int numAttempts = getRandom(1,5);
        for (int i=0; i<numAttempts; i++) {
            int objectOrDomain = getRandom(0, (objects+domains));
            if (objectOrDomain<objects) {
                attemptObjectAction(Integer.parseInt(Thread.currentThread().getName()), objectOrDomain);
            } else {
                attemptDomainSwitch(Integer.parseInt(Thread.currentThread().getName()), objectOrDomain);
            }
        }
        yieldMultipleTimes();
    }

    public void attemptObjectAction(int row, int column) {
        boolean accessGranted;
        int action = getRandom(1,3);
        if (action==1) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Attempting to read from object " + column);
            accessGranted = objectArbitrator(row, column, action);
            if (accessGranted) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": Access granted!");
                read();
            } else {
                System.out.println("Thread " + Thread.currentThread().getName() + ": Access denied.");
            }
            yieldMultipleTimes();
        } else if (action==2) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Attempting to write to object " + column);
            accessGranted = objectArbitrator(row, column, action);
            if (accessGranted) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": Access granted!");
                write();
            } else {
                System.out.println("Thread " + Thread.currentThread().getName() + ": Access denied.");
            }
            yieldMultipleTimes();
        }
    }

    public boolean objectArbitrator(int row, int column, int action) {
        int permission = matrix[row][column];
        return permission == action || permission == 3;
    }

    public void read() {

    }

    public void write() {

    }

    public void attemptDomainSwitch(int row, int column) {
        int domain = column-objects;
        boolean accessGranted;
        System.out.println("Thread " + Thread.currentThread().getName() + ": Attempting to switch to domain " + domain);
        accessGranted = domainSwitchingArbitrator(column);
        if (accessGranted) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Access granted!");
            switchDomain(row);
        } else {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Access denied.");
        }
        yieldMultipleTimes();
    }

    public boolean domainSwitchingArbitrator(int column) {
        int permission = matrix[Integer.parseInt(Thread.currentThread().getName())][column];
        return permission == 1;
    }

    public void switchDomain(int row) {
        int objectOrDomain = getRandom(0, (objects+domains));
        if (objectOrDomain<objects) {
            attemptObjectAction(row, objectOrDomain);
        } else {
            attemptDomainSwitch(row, objectOrDomain);
        }
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
}