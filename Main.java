package com.main;

import com.main.AccessMatrix.AccessMatrix;
import com.main.DomainCapabilityList.DomainCapabilitiesList;
import com.main.ObjectAccessList.ObjectAccessList;

import java.util.LinkedList;
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
                ObjectOperations objectOperations = new ObjectOperations(objects);
                String[] objectList = objectOperations.createObjects();
                ObjectAccessList objectAccessList = new ObjectAccessList(domains, objects);
                LinkedList[] list = objectAccessList.createObjectAccessList();
                objectAccessList.forkThreads(domains, list, domains, objects, objectOperations, objectList);
            }
            case (3) -> {
                System.out.println("You chose to implement protection using a capabilities list for domains.");
                domains = getRandom(3, 8);
                objects = getRandom(3, 8);
                DomainCapabilitiesList domainCapabilitiesList = new DomainCapabilitiesList(domains, objects);
                LinkedList[] list = domainCapabilitiesList.createDomainCapabilitiesList();
                ObjectOperations objectOperations = new ObjectOperations(objects);
                String[] objectList = objectOperations.createObjects();
                domainCapabilitiesList.forkThreads(domains, list, domains, objects, objectOperations, objectList);
            }
        }
    }

    public static int getUserProtectionSolution() {
        Scanner input = new Scanner(System.in);
        int protectionSolution;
        System.out.println("Which protection implementation solution would you prefer? (1/2/3)\n"
        + "1 - Access Matrix\n2 - Access List for Objects\n3 - Capability List for Domains");
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