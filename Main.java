package com.main;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int protectionSolution = getUserProtectionSolution();

        switch (protectionSolution) {
            case (1) -> {
                System.out.println("You chose to implement protection using an access matrix.");
                int domains = getRandom(3, 8);
                int objects = getRandom(3, 8);
                String[][] matrix = createMatrix(domains, objects);

                for (int i = 0; i < domains; i++) {
                    DomainThread thread = new DomainThread(matrix);
                    thread.start();
                }
            }
            case (2) -> {
                System.out.println("You chose to implement protection using an access list for objects.");
            }
            case (3) -> {
                System.out.println("You chose to implement protection using a capabilities list for domains.");
            }
        }
    }

    public static int getUserProtectionSolution() {
        Scanner input = new Scanner(System.in);
        int protectionSolution;
        System.out.println("Which would you prefer? (1/2/3)");
        try {
            protectionSolution = input.nextInt();
            while ((protectionSolution<1) || (protectionSolution>3)) {
                System.out.println("That was not a correct input. Please input a number (1/2/3)");
                protectionSolution = input.nextInt();
            }
        } catch (Exception e) {
            System.out.println("That was not a correct input.\nPlease input a number in range next time (1/2/3).");
            protectionSolution = 0;
            System.exit(protectionSolution);
        }
        return protectionSolution;
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
                switch (permission) {
                    case (1) -> {
                        matrix[i][j] = "read";
                    }
                    case (2) -> {
                        matrix[i][j] = "write";
                    }
                    case (3) -> {
                        matrix[i][j] = "read/write";
                    }
                    case (4) -> {
                        matrix[i][j] = "";
                    }
                }
            }
            for (int j =objects; j<objects+domains; j++) {
                // TODO: add in case where domain is trying to switch to itself
                int permission = getRandom(1, 3);
                switch (permission) {
                    case (1) -> {
                        matrix[i][j] = "allow";
                    }
                    case (2) -> {
                        matrix[i][j] = "";
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
        for (String[] strings : matrix) {
            System.out.println(Arrays.toString(strings));
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
