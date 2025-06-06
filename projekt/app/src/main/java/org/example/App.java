package org.example;

import org.example.Package;
import org.example.Container;
import org.example.PackingSimulator;

import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private PackingSimulator simulator = new PackingSimulator();

    private static final double MAX_WIDTH = 2.0;
    private static final double MAX_HEIGHT = 3.0;
    private static final double MAX_DEPTH = 5.0;

    public static void main(String[] args) {
        new App().start();
    }

    public void start() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getChoiceBetween(1, 3);

            switch (choice) {
                case 1 -> runSimulationFlow();
                case 2 -> showDetailedStatistics();
                case 3 -> running = false;
            }
        }

        System.out.println("Thank you for using the Package Simulator!");
    }

    private void displayMenu() {
        System.out.println("""
            \n=== Package Simulator Menu ===
            1. Run new simulation
            2. Display detailed statistics
            3. Exit
            Enter your choice (1-3): """);
    }

    private int getChoiceBetween(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } else {
                scanner.next(); // clear invalid input
            }
            System.out.print("Please enter a valid number between " + min + " and " + max + ": ");
        }
    }

    private void runSimulationFlow() {
        System.out.print("Enter number of packages to simulate: ");
        int packageCount = getPositiveInt();

        if (packageCount <= 0) {
            System.out.println("Number of packages must be greater than 0.");
            return;
        }

        simulator = new PackingSimulator(); // fresh state

        for (int i = 0; i < packageCount; i++) {
            System.out.printf("\nEnter dimensions for package %d:\n", i + 1);
            double width = getDimension("Width", MAX_WIDTH);
            double height = getDimension("Height", MAX_HEIGHT);
            double depth = getDimension("Depth", MAX_DEPTH);

            simulator.addPackage(new Package("PKG-" + (i + 1), width, height, depth));
        }

        simulator.runSimulation();
        simulator.printStatistics();
    }

    private int getPositiveInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int val = scanner.nextInt();
                if (val > 0) return val;
            } else {
                scanner.next(); // discard invalid
            }
            System.out.print("Please enter a valid positive integer: ");
        }
    }

    private double getDimension(String name, double max) {
        while (true) {
            System.out.printf("%s (max %.2f meters): ", name, max);
            if (scanner.hasNextDouble()) {
                double val = scanner.nextDouble();
                if (val > 0 && val <= max) return val;

                System.out.printf("%s must be > 0 and <= %.2f\n", name, max);
            } else {
                scanner.next(); // discard invalid
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void showDetailedStatistics() {
        if (simulator.getContainers().isEmpty()) {
            System.out.println("No simulation has been run yet.");
            return;
        }

        System.out.println("\n=== Detailed Statistics ===");

        for (Container container : simulator.getContainers()) {
            System.out.printf("Container %s (%.2fm x %.2fm x %.2fm, Volume: %.2f m³):\n",
                    container.getId(),
                    container.getWidth(),
                    container.getHeight(),
                    container.getDepth(),
                    container.getVolume());

            System.out.printf("  Utilization: %.2f%%\n", container.getUtilization());
            System.out.println("  Packages inside:");
            for (Package pkg : container.getPackages()) {
                System.out.printf("    - %s (%.2fm x %.2fm x %.2fm, Volume: %.2f m³)\n",
                        pkg.getId(),
                        pkg.getWidth(),
                        pkg.getHeight(),
                        pkg.getDepth(),
                        pkg.getVolume());
            }
            System.out.println();
        }

        if (!simulator.getPendingPackages().isEmpty()) {
            System.out.println("Unpacked Packages:");
            for (Package pkg : simulator.getPendingPackages()) {
                System.out.printf("  - %s (%.2fm x %.2fm x %.2fm, Volume: %.2f m³)\n",
                        pkg.getId(),
                        pkg.getWidth(),
                        pkg.getHeight(),
                        pkg.getDepth(),
                        pkg.getVolume());
            }
        }
    }
}
