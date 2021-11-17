package com.main;

public class ObjectOperations {

    private final int numFiles;
    String[][] objectList;
    String[] writeOptions;


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

    public String[][] createObjects() {
        objectList = new String[numFiles][];
        return objectList;
    }

    public void read(int objectIndex) {
        System.out.println("Thread " + Thread.currentThread().getName() + ": Read " + " from object " + objectIndex);
    }

    public void write(int objectIndex) {
        System.out.println("Thread " + Thread.currentThread().getName() + ": Wrote '" + writeOptions[getRandom(0,10)] + "' to object " + objectIndex);
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}
