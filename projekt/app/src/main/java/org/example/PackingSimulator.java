package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Simulates packing packages into containers using a best-fit heuristic.
 * Thread-safe and validated for edge cases.
 */
public class PackingSimulator {
    private static final double HEIGHT_WEIGHT = 0.5; // Configurable heuristic weight

    private final List<Container> containers = new ArrayList<>();
    private final List<Package> pendingPackages = new ArrayList<>();
    private final double containerWidth;
    private final double containerHeight;
    private final double containerDepth;

    // Default container size
    public PackingSimulator() {
        this(2.0, 3.0, 5.0);
    }

    public PackingSimulator(double width, double height, double depth) {
        validateDimensions(width, height, depth);
        this.containerWidth = width;
        this.containerHeight = height;
        this.containerDepth = depth;
    }

    private void validateDimensions(double width, double height, double depth) {
        if (width <= 0 || height <= 0 || depth <= 0) {
            throw new IllegalArgumentException("Container dimensions must be positive.");
        }
    }

    /**
     * Runs the packing simulation for all pending packages.
     */
    public void runSimulation() {
        List<Package> toProcess = new ArrayList<>(pendingPackages);
        pendingPackages.clear();

        for (Package pkg : toProcess) {
            Container bestFit = findBestFitContainer(pkg);
            if (bestFit != null) {
                bestFit.addPackage(pkg);
            } else {
                createNewContainer(pkg);
            }
        }
    }

    private Container findBestFitContainer(Package pkg) {
        return containers.stream()
            .filter(c -> c.canAccommodate(pkg))
            .min(Comparator.comparingDouble(c -> 
                c.getRemainingVolume() + (Math.abs(c.getCurrentHeight() - pkg.getHeight()) * HEIGHT_WEIGHT)))
            .orElse(null);
    }

    private void createNewContainer(Package pkg) {
        Container newContainer = new Container(
            "CNT-" + (containers.size() + 1),
            containerWidth, containerHeight, containerDepth
        );
        if (newContainer.addPackage(pkg)) {
            containers.add(newContainer);
        } else {
            pendingPackages.add(pkg);
        }
    }

    /**
     * Adds a package to the pending list for simulation.
     */
    public void addPackage(Package pkg) {
        pendingPackages.add(Objects.requireNonNull(pkg, "Package cannot be null."));
    }

    /**
     * Generates random packages within container dimensions.
     */
    public void generatePackages(int count) {
        if (count <= 0) throw new IllegalArgumentException("Count must be positive.");
        for (int i = 0; i < count; i++) {
            double w = 0.1 + Math.random() * (containerWidth - 0.1);
            double h = 0.1 + Math.random() * (containerHeight - 0.1);
            double d = 0.1 + Math.random() * (containerDepth - 0.1);
            pendingPackages.add(new Package("PKG-" + (i + 1), w, h, d));
        }
    }

    /**
     * Prints simulation statistics (containers used, utilization, etc.).
     */
    public void printStatistics() {
        System.out.println("\n=== Packing Simulation Statistics ===");
        System.out.printf("Container Size: %.2fm x %.2fm x %.2fm%n",
            containerWidth, containerHeight, containerDepth);

        int totalPacked = containers.stream().mapToInt(c -> c.getPackages().size()).sum();
        System.out.printf("Packages: %d (Packed: %d | Unpacked: %d)%n",
            totalPacked + pendingPackages.size(), totalPacked, pendingPackages.size());

        if (!containers.isEmpty()) {
            System.out.println("\nContainer Utilization:");
            containers.forEach(c -> System.out.printf(
                "  %s: %.2f%% used, %d packages%n",
                c.getId(), c.getUtilization(), c.getPackages().size()
            ));
            double avgUtilization = containers.stream()
                .mapToDouble(Container::getUtilization)
                .average()
                .orElse(0.0);
            System.out.printf("\nAverage Utilization: %.2f%%%n", avgUtilization);
        }

        if (!pendingPackages.isEmpty()) {
            System.out.println("\nUnpacked Packages:");
            pendingPackages.forEach(pkg -> System.out.printf(
                "  %s (%.2fm x %.2fm x %.2fm)%n",
                pkg.getId(), pkg.getWidth(), pkg.getHeight(), pkg.getDepth()
            ));
        }
    }

    // Getters
    public List<Container> getContainers() { return new ArrayList<>(containers); }
    public List<Package> getPendingPackages() { return new ArrayList<>(pendingPackages); }
    public void reset() { containers.clear(); pendingPackages.clear(); }
}