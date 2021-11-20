package com.main;

import com.main.AccessMatrix.AccessMatrix;

import java.util.Scanner;

// code by James Thierry
public class Main {

    public static void main(String[] args){
        int protectionSolution = getUserProtectionSolution();
        int domains;
        int objects;

        switch (protectionSolution) {
            case (1) -> {
                System.out.println("You chose to implement protection using an access matrix.");
                domains = getRandom(3, 8);
                objects = getRandom(3, 8);
                AccessMatrix accessMatrix = new AccessMatrix(domains, objects);
                ObjectOperations objectOperations = new ObjectOperations(objects);
                int[][] matrix = accessMatrix.createMatrix();
                String[] objectList = objectOperations.createObjects();
                accessMatrix.forkThreads(domains ,matrix, objectOperations, objectList);
            }
            case (2) -> {
                System.out.println("You chose to implement protection using an access list for objects.");
                domains = getRandom(3, 8);
                objects = getRandom(3, 8);
            }
            case (3) -> {
                System.out.println("You chose to implement protection using a capabilities list for domains.");
                domains = getRandom(3, 8);
                objects = getRandom(3, 8);
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
                System.out.println("That was not a correct input. Please input an integer (1/2/3)");
                protectionSolution = input.nextInt();
            }
        } catch (Exception e) {
            System.out.println("That was not a correct input.\nPlease input an integer in range next time (1/2/3).");
            protectionSolution = 0;
            System.exit(protectionSolution);
        }
        return protectionSolution;
    }

    public static int getRandom(int min, int max) {
        return (int)(Math.random() * (max-min) + min);
    }
}
// end code by James Thierry