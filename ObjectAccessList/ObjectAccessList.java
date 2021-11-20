package com.main.ObjectAccessList;

import java.util.HashMap;
import java.util.LinkedList;

public class ObjectAccessList {
    private final int domains;
    private final int objects;

    public ObjectAccessList(int domains, int objects) {
        this.domains = domains;
        this.objects = objects;
    }

    /*
    For the File objects, permissions are:
    1 - Read
    2 - Write
    3 - Read/Write
    4 - None

    For the domain switching, permissions are:
    0 - Already in that domain
    1 - Allow
    2 - Not Allowed
    */

    public LinkedList[] createObjectAccessList() {
        System.out.println("An object access list with " + domains + " domains and " + objects +  " objects is being created!");
        System.out.println("\nFor the File objects, permissions are:\n1 - Read\n2 - Write\n3 - Read/Write\n4 - None\n\n"
                + "For the domain switching, permissions are:\n0 - Already in that domain\n1 - Allowed\n2 - Not Allowed\n");
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
                    if (permission==1) {
                        HashMap<Integer, Integer> map = new HashMap<>();
                        map.put(j, permission);
                        objectAccessList[i].add(map);
                    }
                }
            }
        }
        for (int i=0; i<outerListLength; i++) {
            System.out.println(objectAccessList[i].toString());
        }
        return objectAccessList;
    }

    public void forkThreads(int numThreads, LinkedList[] list) {


    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

}
