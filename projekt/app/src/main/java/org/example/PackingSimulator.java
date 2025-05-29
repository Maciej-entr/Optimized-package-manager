package org.example;

import java.util.*;

public class PackingSimulator {
    private final List<Container> containers;
    private final List<Package> pendingPackages;
    private final Random random;
    private static final double DEFAULT_CONTAINER_SIZE = 100.0;

    public PackingSimulator() {
        this.containers = new ArrayList<>();
        this.pendingPackages = new ArrayList<>();
        this.random = new Random();
    }

    public void generatePackages(int count) {
        for (int i = 0; i < count; i++) {
            pendingPackages.add(new Package(
                "PKG-" + (i + 1),
                random.nextDouble() * 50,
                random.nextDouble() * 50,
                random.nextDouble() * 50
            ));
        }
    }

    public void runSimulation() {
        for (Package pkg : new ArrayList<>(pendingPackages)) {
            boolean packed = false;
            
            // Try existing containers
            for (Container container : containers) {
                if (container.addPackage(pkg)) {
                    packed = true;
                    pendingPackages.remove(pkg);
                    break;
                }
            }

            // Create new container if needed
            if (!packed) {
                Container newContainer = new Container(
                    "CNT-" + (containers.size() + 1),
                    DEFAULT_CONTAINER_SIZE,
                    DEFAULT_CONTAINER_SIZE,
                    DEFAULT_CONTAINER_SIZE
                );
                if (newContainer.addPackage(pkg)) {
                    containers.add(newContainer);
                    pendingPackages.remove(pkg);
                }
            }
        }
    }

    public void printStatistics() {
        System.out.println("\n=== Packing Simulation Statistics ===");
        System.out.println("Total containers used: " + containers.size());
        System.out.println("Packages not packed: " + pendingPackages.size());
        
        double avgUtilization = containers.stream()
            .mapToDouble(Container::getUtilization)
            .average()
            .orElse(0.0);
        
        System.out.printf("Average container utilization: %.2f%%\n", avgUtilization);
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<Package> getPendingPackages() {
        return pendingPackages;
    }
}
