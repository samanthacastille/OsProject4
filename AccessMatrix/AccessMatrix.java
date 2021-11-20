package com.main.AccessMatrix;

import com.main.ObjectOperations;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

// code by Samantha Castille
public class AccessMatrix {
    private final int domains;
    private final int objects;
    private Semaphore[] accessMatrixSemaphore;

    public AccessMatrix(int domains, int objects) {
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
    public int[][] createMatrix() {
        accessMatrixSemaphore = new Semaphore[1];
        accessMatrixSemaphore[0] = new Semaphore(1);
        System.out.println("\nAn access matrix with " + domains + " domains and " + objects +  " objects is being created!");
        System.out.println("\nFor the File objects, permissions are:\n1 - Read\n2 - Write\n3 - Read/Write\n4 - None\n\n"
                + "For the domain switching, permissions are:\n0 - Already in that domain\n1 - Allowed\n2 - Not Allowed\n");
        int[][] matrix = new int[domains][objects+domains];
        for (int i=0; i<domains; i++){
            for (int j = 0; j<objects+domains; j++) {
                int permission;
                if (j >= objects) {
                    if (j==objects+i) {
                        permission = 0;
                    } else {
                        permission = getRandom(1, 3);
                    }
                } else {
                    permission = getRandom(1, 5);
                }
                matrix[i][j] = permission;
            }
        }
        for (int i=0; i<domains; i++) {
            System.out.println("Domain: " + i + " " + Arrays.toString(matrix[i]));
        }
        System.out.println();
        return matrix;
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

    public void forkThreads(int numThreads, int[][] matrix, ObjectOperations objectOperations, String[] objectList) {
        for (int i = 0; i < numThreads; i++) {
            AccessMatrixThread thread = new AccessMatrixThread(matrix, domains, objects, objectOperations, objectList, accessMatrixSemaphore);
            thread.setName(Integer.toString(i));
            thread.start();
        }
    }
}
// end code by Samantha Castille