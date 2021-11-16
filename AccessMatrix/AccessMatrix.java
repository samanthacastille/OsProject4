package com.main.AccessMatrix;

import java.util.Arrays;

public class AccessMatrix {
    private final int domains;
    private final int objects;

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
        System.out.println("An access matrix with " + domains + " domains and " + objects +  " objects is being created!");
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
        for (int[] strings : matrix) {
            System.out.println(Arrays.toString(strings));
        }
        return matrix;
    }

    public int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

    public void forkThreads(int numThreads, int[][] matrix) {
        for (int i = 0; i < numThreads; i++) {
            AccessMatrixThread thread = new AccessMatrixThread(matrix);
            thread.start();
        }
    }
}
