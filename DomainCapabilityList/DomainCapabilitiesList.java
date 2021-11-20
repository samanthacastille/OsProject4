package com.main.DomainCapabilityList;

import com.main.ObjectOperations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

// code by James Thierry
public class DomainCapabilitiesList {
    private final int domains;
    private final int objects;
    private Semaphore[] domainCapabilitiesSemaphore;

    public DomainCapabilitiesList(int domains, int objects) {
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

    public LinkedList[] createDomainCapabilitiesList () {
        domainCapabilitiesSemaphore = new Semaphore[1];
        domainCapabilitiesSemaphore[0] = new Semaphore(1);
        System.out.println("\nAn object access list with " + domains + " domains and " + objects +  " objects is being created!");
        System.out.println("\nFor the File objects, permissions are:\n1 - Read\n2 - Write\n3 - Read/Write\n\n"
                + "For the Domain switching, permissions are:\n1 - Allowed\n");
        System.out.println("Objects are represented by map keys 0-" + (objects-1) + " with the permissions represented by the map values.");
        System.out.println("Domains 0-" + (domains-1) + " are represented by map keys " + objects + "-" + (objects+domains-1) + " with the permissions represented by the map values." + "\n");
        int outerListLength = domains;
        LinkedList[] domainCapabilitiesList = new LinkedList[outerListLength];
        for (int i=0; i<outerListLength; i++) {                             // i represents domains
            domainCapabilitiesList[i] = new LinkedList<>();
            for (int j=0; j<domains+objects; j++) {                         // j represents object or domain
                if (j<objects) {
                    int permission = getRandom(1, 5);
                    if (permission != 4) {
                        HashMap<Integer, Integer> map = new HashMap<>();
                        map.put(j, permission);
                        domainCapabilitiesList[i].add(map);
                    }
                } else {
                    int permission = getRandom(0,3);
                    if (permission==1) {
                        HashMap<Integer, Integer> map = new HashMap<>();
                        map.put(j, permission);
                        domainCapabilitiesList[i].add(map);
                    }
                }
            }
        }
        for (int i=0; i<outerListLength; i++) {
            System.out.println("Domain " + i + ": " + domainCapabilitiesList[i].toString());
        }
        System.out.println("\n");
        return domainCapabilitiesList;
    }

    public void forkThreads(int numThreads, LinkedList[] domainCapabilitiesList, int domains, int objects, ObjectOperations objectOperations, String[] objectList) {
        for (int i=0; i<numThreads; i++) {
            DomainCapabilitiesListThread thread = new DomainCapabilitiesListThread(domainCapabilitiesList, domains, objects, objectOperations, objectList, domainCapabilitiesSemaphore);
            thread.setName(Integer.toString(i));
            thread.start();
        }
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}

// end code by James Thierry
