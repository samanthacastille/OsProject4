package com.main.AccessMatrix;

import com.main.ObjectOperations;

public class AccessMatrixThread extends Thread {
    private int[][] matrix;
    private final int domains;
    private final int objects;
    private final ObjectOperations fileOperations;
    private final String[][] objectList;

    public AccessMatrixThread(int[][] matrix, int domains, int objects, ObjectOperations fileOperations, String[][] filePathList) {
        this.matrix = matrix;
        this.domains = domains;
        this.objects = objects;
        this.fileOperations = fileOperations;
        this.objectList = filePathList;
    }

    /*
    RUN
    randomly decides how many actions a thread will attempt to do
    for each attempt, if it's trying to access a file, it will send the current thread as the row,
        and the column number of the file it's trying to access to 'attemptObjectAction'
    if it's trying to switch domains, it will call 'attemptDomainSwitch' with the current thread
        as the row, and the column number of the domain it's trying to switch to
    yields a random number of times after it's done running
     */
    @Override
    public void run() {
        int currentDomain = Integer.parseInt(Thread.currentThread().getName());
        int numAttempts = getRandom(5,20);
        for (int i=0; i<numAttempts; i++) {
            int objectOrDomain = getRandom(0, (objects+domains));
            if (objectOrDomain<objects) {
                attemptObjectAction(currentDomain, objectOrDomain);
            } else {
                while (currentDomain==(objectOrDomain-objects)) {
                    System.out.println("Thread " + currentDomain + ": Attempted to switch to it's own domain, which is pointless. "
                            + "Randomly generating a different domain to attempt to switch to.");
                    objectOrDomain = getRandom(objects, (objects+domains));
                }
                attemptDomainSwitch(currentDomain, objectOrDomain);
            }
        }
    }

    /*
    ATTEMPT OBJECT ACTION
    generates random action being attempted (1=read, 2=write)
    takes in row for current domain, and column for the file attempting to read/write to
    if access is granted, calls read() or write()
     */
    public void attemptObjectAction(int row, int column) {
        boolean accessGranted;
        int action = getRandom(1,3);
        if (action==1) {
            System.out.println("Thread " + row + ": Attempting to read from object " + column);
            accessGranted = objectArbitrator(row, column, action);
            if (accessGranted) {
                System.out.println("Thread " + row + ": Read access granted!");
                fileOperations.read(column);
            } else {
                System.out.println("Thread " + row + ": Read access denied.");
            }
            yieldMultipleTimes();
        } else if (action==2) {
            System.out.println("Thread " + row + ": Attempting to write to object " + column);
            accessGranted = objectArbitrator(row, column, action);
            if (accessGranted) {
                System.out.println("Thread " + row + ": Write access granted!");
                fileOperations.write(column);
            } else {
                System.out.println("Thread " + row + ": Write access denied.");
            }
            yieldMultipleTimes();
        }
    }

    /*
    OBJECT ARBITRATOR
    takes in the current domain as the row, the object being written/read from as the column, and the action attempted
    checks the matrix to see if it has the exact permission (1/2) or if it's 3 which has both read and write permissions
     */
    public boolean objectArbitrator(int row, int column, int action) {
        int permission = matrix[row][column];
        return permission == action || permission == 3;
    }

    /*
    ATTEMPT DOMAIN SWITCH
    takes in row for current domain, and column for the domain it's trying to switch to
    calls 'domainSwitchingArbitrator' to see if it's allowed to switch or not, prints 'access denied if it is not
        allow to switch to that domain, or if it is already in that domain
    if access is granted, it calls 'switchDomain' with the domain it's trying to switch to
     */
    public void attemptDomainSwitch(int row, int column) {
        int domain = column-objects;
        boolean accessGranted;
        System.out.println("Thread " + row + ": Attempting to switch to domain " + domain);
        accessGranted = domainSwitchingArbitrator(row, column);
        if (accessGranted) {
            System.out.println("Thread " + row + ": Domain switch access granted!");
            switchDomain(row, domain);
        } else {
            System.out.println("Thread " + row + ": Domain switch access denied.");
        }
        yieldMultipleTimes();
    }

    /*
    DOMAIN SWITCHING ARBITRATOR
    checks access matrix to see if the current domain 'row' is allowed to switch to another domain 'column'
     */
    public boolean domainSwitchingArbitrator(int row, int column) {
        int permission = matrix[row][column];
        return permission == 1;
    }

    /*
    SWITCH DOMAIN
    take the permissions row values from the domain we are trying to switch to and update
        the current domains values to match them (only the permissions relating to read/write to files)
     */
    public void switchDomain(int row, int domain) {
        for (int i=0; i<objects; i++) {
            int temp = matrix[domain][i];
            matrix[row][i] = temp;
        }
    }

    /*
    YIELD MULTIPLE TIMES
     */
    public void yieldMultipleTimes() {
        int numYields = getRandom(3,8);
        System.out.println("Thread " + Thread.currentThread().getName() + ": Yielding " + numYields + " times.");
        for (int i = 0; i<numYields; i++) {
            Thread.yield();
        }
    }

    /*
    GET RANDOM
    generate a random number in a provided range, not inclusive of the upper bound
     */
    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}