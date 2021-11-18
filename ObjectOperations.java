package com.main;

import java.util.concurrent.Semaphore;

public class ObjectOperations {

    private final int numFiles;
    String[] objectList;
    String[] writeOptions;
    Semaphore[] semaphoreList;


    ObjectOperations(int numFiles) {
        this.numFiles = numFiles;
        writeOptions = new String[10];
        // Lyrics to Private Eyes by Hall & Oats
        writeOptions[0] = "You can't escape my private eyes. ";
        writeOptions[1] = "They're watching you. ";
        writeOptions[2] = "They see your every move. ";
        writeOptions[3] = "I see you. ";
        writeOptions[4] = "You see me. ";
        writeOptions[5] = "You play with words. ";
        writeOptions[6] = "You play with love. ";
        writeOptions[7] = "You can twist it around. ";
        writeOptions[8] = "Baby, that ain't enough. ";
        writeOptions[9] = "I'm a spy. ";
    }

    public String[] createObjects() {
        objectList = new String[numFiles];
        semaphoreList = new Semaphore[numFiles];
        for (int i = 0; i < numFiles; i++) {
            semaphoreList[i] = new Semaphore(1);
        }
        return objectList;
    }

    public void read(int objectIndex) {
        try {
            semaphoreList[objectIndex].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String readString = objectList[objectIndex];
        semaphoreList[objectIndex].release();
        if (readString==null) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Tried to read from object " + objectIndex + " but it was empty.");
        } else {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Read '" + readString + "' from object " + objectIndex);
        }
    }

    public void write(int objectIndex) {
        try {
            semaphoreList[objectIndex].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String random = writeOptions[getRandom(0,10)];
        if (objectList[objectIndex]==null){
            objectList[objectIndex] = random;
        } else {
            objectList[objectIndex] += random;
        }
        semaphoreList[objectIndex].release();
        System.out.println("Thread " + Thread.currentThread().getName() + ": Wrote '" + random + "' to object " + objectIndex);
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}