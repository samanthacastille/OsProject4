package com.main;

import com.main.AccessMatrix.AccessMatrix;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        int protectionSolution = getUserProtectionSolution();

        switch (protectionSolution) {
            case (1) -> {
                System.out.println("You chose to implement protection using an access matrix.");
                int domains = getRandom(3, 8);
                int objects = getRandom(3, 8);
                AccessMatrix accessMatrix = new AccessMatrix(domains, objects);
                int[][] matrix = accessMatrix.createMatrix();
                String[] filePathList = accessMatrix.createFile();
                accessMatrix.forkThreads(domains ,matrix, filePathList);
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