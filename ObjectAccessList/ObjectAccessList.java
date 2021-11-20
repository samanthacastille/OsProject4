package com.main.ObjectAccessList;

import com.main.ObjectOperations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

// code by James Thierry
public class ObjectAccessList {
    private final int domains;
    private final int objects;
    private Semaphore[] objectAccessListSemaphore;

    public ObjectAccessList(int domains, int objects) {
        this.domains = domains;
        this.objects = objects;
    }

    /*
    For the File objects, permissions are:
    1 - Read
    2 - Write
    3 - Read/Write

    For the domain switching, permissions are:
    1 - Allow
    */

    public LinkedList[] createObjectAccessList() {
        objectAccessListSemaphore = new Semaphore[1];
        objectAccessListSemaphore[0] = new Semaphore(1);
        System.out.println("\nAn object access list with " + domains + " domains and " + objects +  " objects is being created!");
        System.out.println("\nFor the File objects, permissions are:\n1 - Read\n2 - Write\n3 - Read/Write\n\n"
                + "For the domain switching, permissions are:\n1 - Allowed\n");
        System.out.println("\nDomains are represented by map keys with the permissions represented by map values.\n");
        int outerListLength = domains+objects;
        LinkedList[] objectAccessList = new LinkedList[outerListLength];
        for (int i=0; i<outerListLength; i++) {                         // i is the outer list which is objects + domains
            objectAccessList[i] = new LinkedList<>();
            for (int j=0; j<domains; j++) {                             // j is the domains
                if (i<objects) {
                    int permission = getRandom(1,5);
                    if (permission!=4) {
                        HashMap<Integer, Integer> map = new HashMap<>();
                        map.put(j, permission);
                        objectAccessList[i].add(map);
                    }
                } else {
                    int permission = getRandom(0,3);
                    if (permission==1 && !((i-objects)==j)) {
                        HashMap<Integer, Integer> map = new HashMap<>();
                        map.put(j, permission);
                        objectAccessList[i].add(map);
                    }
                }
            }
        }
        for (int i=0; i<outerListLength; i++) {
            if (i<objects) {
                System.out.println("Object " + i + ": " + objectAccessList[i].toString());
            } else {
                System.out.println("Domain " + (i-objects) + ": " + objectAccessList[i].toString());
            }
        }
        System.out.println("\n");
        return objectAccessList;
    }

    public void forkThreads(int numThreads, LinkedList[] objectAccessList, int domains, int objects, ObjectOperations objectOperations, String[] objectList) {
        for (int i = 0; i < numThreads; i++) {
            ObjectAccessListThread thread = new ObjectAccessListThread(objectAccessList, domains, objects, objectOperations, objectList, objectAccessListSemaphore);
            thread.setName(Integer.toString(i));
            thread.start();
        }
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}
// end code by James Thierry