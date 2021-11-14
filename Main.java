package com.main;


import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        System.out.println("Which ");
        int domains = getRandom(3,8);
        int objects = getRandom(3,8);
        String[][] matrix = createMatrix(domains, objects);

        for (int i=0; i<domains;i++) {
            DomainThread thread = new DomainThread(matrix);
            thread.start();
        }
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }

    public static String[][] createMatrix(int domains, int objects) {
        System.out.println("Matrix is being created!");
        String[][] matrix = new String[domains][objects+domains];
        for (int i=0; i<domains; i++){
            for (int j = 0; j<objects; j++) {
                int permission = getRandom(1,5);
                switch (permission){
                    case(1) : {
                        matrix[i][j] = "read";
                        break;
                    }
                    case(2) : {
                        matrix[i][j] = "write";
                        break;
                    }
                    case(3) : {
                        matrix[i][j] = "read/write";
                        break;
                    }
                    case(4) : {
                        matrix[i][j] = "";
                        break;
                    }
                }
            }
            for (int j =objects; j<objects+domains; j++) {
                // TODO: add in case where domain is trying to switch to itself
                int permission = getRandom(1, 3);
                switch (permission) {
                    case (1): {
                        matrix[i][j] = "allow";
                        break;
                    }
                    case (2): {
                        matrix[i][j] = "";
                        break;
                    }
                }
            }
        }
        return matrix;
    }
}

class DomainThread extends Thread {
    String[][] matrix;
    DomainThread(String[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " in control");
        for (int i =0; i<matrix.length; i++)  {
            System.out.println(Arrays.toString(matrix[i]));
        }
        yieldMultipleTimes();
    }

    public void yieldMultipleTimes() {
        for (int i = 0; i<getRandom(3,8); i++) {
            currentThread().interrupt();
        }
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}
