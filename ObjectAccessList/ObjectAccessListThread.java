package com.main.ObjectAccessList;

import com.main.ObjectOperations;

import java.util.HashMap;
import java.util.LinkedList;

public class ObjectAccessListThread extends Thread {
    private LinkedList[] objectAccessList;
    private final int domains;
    private final int objects;
    private final ObjectOperations objectOperations;
    private final String[] objectList;

    public ObjectAccessListThread(LinkedList[] objectAccessList, int domains, int objects, ObjectOperations objectOperations, String[] objectList) {
        this.objectAccessList = objectAccessList;
        this.domains = domains;
        this.objects = objects;
        this.objectOperations = objectOperations;
        this.objectList = objectList;
    }

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
                objectOperations.read(column);
            } else {
                System.out.println("Thread " + row + ": Read access denied.");
            }
            yieldMultipleTimes();
        } else if (action==2) {
            System.out.println("Thread " + row + ": Attempting to write to object " + column);
            accessGranted = objectArbitrator(row, column, action);
            if (accessGranted) {
                System.out.println("Thread " + row + ": Write access granted!");
                objectOperations.write(column);
            } else {
                System.out.println("Thread " + row + ": Write access denied.");
            }
            yieldMultipleTimes();
        }
    }

    /*
    OBJECT ARBITRATOR

     */
    public boolean objectArbitrator(int row, int column, int action) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(row, action);
        return objectAccessList[column].contains(map);
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
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(row, 1);
        return objectAccessList[column].contains(map);
    }

    /*
    SWITCH DOMAIN
    take the permissions row values from the domain we are trying to switch to and update
        the current domains values to match them (only the permissions relating to read/write to files)
     */
    public void switchDomain(int row, int domain) {
                
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
// end code by Samantha Castille

