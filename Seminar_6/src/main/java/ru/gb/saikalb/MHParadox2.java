package ru.gb.saikalb;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MHParadox2 {
    private static final int TRIALS = 1000;

    public static void main(String[] args) {
        int winsWithSwitch = 0;
        int winsWithoutSwitch = 0;
        Map<Integer, Boolean> resultsWithSwitch = new HashMap<>();
        Map<Integer, Boolean> resultsWithoutSwitch = new HashMap<>();

        for (int i = 0; i < TRIALS; i++) {
            boolean winWithSwitch = simulateMontyHall(true);
            boolean winWithoutSwitch = simulateMontyHall(false);

            resultsWithSwitch.put(i, winWithSwitch);
            resultsWithoutSwitch.put(i, winWithoutSwitch);

            if (winWithSwitch) {
                winsWithSwitch++;
            }
            if (winWithoutSwitch) {
                winsWithoutSwitch++;
            }
        }

        System.out.println("Wins with switch: " + winsWithSwitch + " out of " + TRIALS + " ("
                + (winsWithSwitch / (double) TRIALS * 100) + "%)");
        System.out.println("Wins without switch: " + winsWithoutSwitch + " out of " + TRIALS + " ("
                + (winsWithoutSwitch / (double) TRIALS * 100) + "%)");

        System.out.println("\nResults with switch:");
        printResults(resultsWithSwitch);

        System.out.println("\nResults without switch:");
        printResults(resultsWithoutSwitch);
    }

    private static boolean simulateMontyHall(boolean switchDoor) {
        int[] doors = {0, 0, 1}; // 0 - goat, 1 - car
        shuffleArray(doors);

        Random rand = new Random();
        int initialChoice = rand.nextInt(3);

        int openedDoor;
        do {
            openedDoor = rand.nextInt(3);
        } while (openedDoor == initialChoice || doors[openedDoor] == 1);

        int finalChoice;
        if (switchDoor) {
            finalChoice = 3 - initialChoice - openedDoor; // switch to the remaining door
        } else {
            finalChoice = initialChoice;
        }

        return doors[finalChoice] == 1;
    }

    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private static void printResults(Map<Integer, Boolean> results) {
        int wins = 0;
        int losses = 0;
        for (Map.Entry<Integer, Boolean> entry : results.entrySet()) {
            if (entry.getValue()) {
                wins++;
            } else {
                losses++;
            }
        }
        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
    }
}