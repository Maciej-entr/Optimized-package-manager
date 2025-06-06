package org.example;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class PackingSimulator {
    private final List<Container> containers = new ArrayList<>();
    private final List<Package> pendingPackages = new ArrayList<>();
    private final Random random = new Random();

    private final double containerWidth;
    private final double containerHeight;
    private final double containerDepth;

    public PackingSimulator(double width, double height, double depth) {
        this.containerWidth = width;
        this.containerHeight = height;
        this.containerDepth = depth;
    }

    public void runSimulation() {
        List<Package> toProcess = new ArrayList<>(pendingPackages);
        pendingPackages.clear();

        for (Package pkg : toProcess) {
            Container bestFit = containers.stream()
                .filter(c -> c.canFit(pkg))
                .min(Comparator.comparingDouble(Container::getRemainingVolume))
                .orElse(null);

            if (bestFit != null) {
                bestFit.addPackage(pkg);
            } else {
                Container newContainer = new Container("CNT-" + (containers.size() + 1),
                                                       containerWidth, containerHeight, containerDepth);
                if (newContainer.addPackage(pkg)) {
                    containers.add(newContainer);
                } else {
                    pendingPackages.add(pkg);
                }
            }
        }
    }

    public void generatePackages(int count) {
        for (int i = 0; i < count; i++) {
            double w = 0.1 + random.nextDouble() * (containerWidth - 0.1);
            double h = 0.1 + random.nextDouble() * (containerHeight - 0.1);
            double d = 0.1 + random.nextDouble() * (containerDepth - 0.1);
            pendingPackages.add(new Package("PKG-" + (i + 1), w, h, d));
        }
    }

    public void printStatistics() {
        System.out.println("\n=== Packing Simulation Statistics ===");
        System.out.printf("Standard Container Size: %.2fm x %.2fm x %.2fm%n",
                containerWidth, containerHeight, containerDepth);
        int totalPacked = containers.stream().mapToInt(c -> c.getPackages().size()).sum();
        int total = totalPacked + pendingPackages.size();

        System.out.println("Total Packages: " + total);
        System.out.println("Containers Used: " + containers.size());
        System.out.println("Unpacked Packages: " + pendingPackages.size());

        if (!containers.isEmpty()) {
            System.out.println("\nContainer Utilization:");
            for (Container c : containers) {
                System.out.printf("Container %s: %.2f%% used, %d packages%n",
                    c.getId(), c.getUtilization(), c.getPackages().size());
            }

            double avgUtilization = containers.stream()
                .mapToDouble(Container::getUtilization)
                .average().orElse(0.0);
            System.out.printf("\nAverage Utilization: %.2f%%%n", avgUtilization);
        }

        if (!pendingPackages.isEmpty()) {
            System.out.println("\nWarning: Following packages couldn't be packed:");
            for (Package pkg : pendingPackages) {
                System.out.printf("  %s (%.2fm x %.2fm x %.2fm)%n",
                    pkg.getId(), pkg.getWidth(), pkg.getHeight(), pkg.getDepth());
            }
        }
    }

    // Allows manual addition of packages before simulation
    public void addPackage(Package pkg) {
        pendingPackages.add(pkg);
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<Package> getPendingPackages() {
        return pendingPackages;
    }

    public void reset() {
        containers.clear();
        pendingPackages.clear();
    }
}
