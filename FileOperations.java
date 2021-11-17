package com.main;

import java.io.File;

public class FileOperations {

    private final int numFiles;
    String[] list;
    String[] writeOptions;


    FileOperations(int numFiles) {
        this.numFiles = numFiles;
        writeOptions = new String[10];
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

    public String[] createFiles() {
        list = new String[numFiles];
        try {
            for (int i = 0; i < numFiles; i++) {
                File file = File.createTempFile("temp", null);
                list[i] = file.getAbsolutePath();
                file.deleteOnExit();
            }
        } catch (Exception e) {
            System.out.println("Error creating files.");
            System.exit(1);
        }
        return list;
    }

    public void read(int fileIndex) {
        System.out.println("Thread " + Thread.currentThread().getName() + ": Read " + " from object " + fileIndex);
    }

    public void write(int fileIndex) {
        System.out.println("Thread " + Thread.currentThread().getName() + ": Wrote '" + writeOptions[getRandom(0,10)] + "' to object " + fileIndex);
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}
