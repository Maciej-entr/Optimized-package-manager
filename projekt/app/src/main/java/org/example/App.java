package org.example;

import java.util.Scanner;

/**
 * CLI interface for the packing simulator.
 * Handles user input, simulation flow, and statistics display.
 */
public class App {
    private static final double MAX_WIDTH = 2.0;
    private static final double MAX_HEIGHT = 3.0;
    private static final double MAX_DEPTH = 5.0;

    private final Scanner scanner;
    private final PackingSimulator simulator;

    public App() {
        this.scanner = new Scanner(System.in);
        this.simulator = new PackingSimulator();
    }

  public static void main(String[] args) {
    App app = new App();
    try {
        app.start();
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    } finally {
        app.scanner.close();
    }
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
        System.out.println("Thank you for using the Packing Simulator!");
    }

    private void displayMenu() {
        System.out.println("""
            \n=== Packing Simulator Menu ===
            1. Run new simulation
            2. Display detailed statistics
            3. Exit
            Enter your choice (1-3):""");
    }

    private void runSimulationFlow() {
        System.out.print("Enter number of packages to simulate: ");
        int packageCount = getPositiveInt();

        simulator.reset(); // Clear previous state

        for (int i = 0; i < packageCount; i++) {
            System.out.printf("\nEnter dimensions for package %d:%n", i + 1);
            double width = getDimension("Width", MAX_WIDTH);
            double height = getDimension("Height", MAX_HEIGHT);
            double depth = getDimension("Depth", MAX_DEPTH);
            simulator.addPackage(new Package("PKG-" + (i + 1), width, height, depth));
        }

        simulator.runSimulation();
        simulator.printStatistics();
    }

    private void showDetailedStatistics() {
        if (simulator.getContainers().isEmpty()) {
            System.out.println("No simulation data available. Run a simulation first.");
            return;
        }

        System.out.println("\n=== Detailed Container Stats ===");
        simulator.getContainers().forEach(container -> {
            System.out.printf(
                "Container %s (%.2fm x %.2fm x %.2fm, %.2f%% used):%n",
                container.getId(),
                container.getWidth(),
                container.getHeight(),
                container.getDepth(),
                container.getUtilization()
            );
            container.getPackages().forEach(pkg -> System.out.printf(
                "  - %s (%.2fm x %.2fm x %.2fm, Vol: %.2fm³)%n",
                pkg.getId(), pkg.getWidth(), pkg.getHeight(), pkg.getDepth(), pkg.getVolume()
            ));
        });
    }

    // Helper methods for input validation
    private int getChoiceBetween(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    scanner.nextLine(); // Consume newline
                    return choice;
                }
            } else {
                scanner.next(); // Discard invalid input
            }
            System.out.printf("Please enter a number between %d and %d: ", min, max);
        }
    }

    private int getPositiveInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int val = scanner.nextInt();
                if (val > 0) {
                    scanner.nextLine(); // Consume newline
                    return val;
                }
            } else {
                scanner.next(); // Discard invalid input
            }
            System.out.print("Please enter a positive integer: ");
        }
    }

    private double getDimension(String name, double max) {
        while (true) {
            System.out.printf("%s (max %.2fm): ", name, max);
            if (scanner.hasNextDouble()) {
                double val = scanner.nextDouble();
                if (val > 0 && val <= max) {
                    scanner.nextLine(); // Consume newline
                    return val;
                }
                System.out.printf("%s must be between 0 and %.2f.%n", name, max);
            } else {
                scanner.next(); // Discard invalid input
                System.out.println("Please enter a valid number.");
            }
        }
    }
}