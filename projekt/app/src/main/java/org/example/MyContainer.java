package main.java.org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a 3D container to hold packages.
 * Uses simplified volume-based approximation to determine fit.
 */
public class MyContainer {
    private final String id;
    private final double width;
    private final double height;
    private final double depth;

    private final List<Package> packages = new ArrayList<>();
    private double usedVolume = 0;

    public MyContainer(String id, double width, double height, double depth) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * Checks if the package dimensions and volume allow it to be placed in this container.
     * Note: This is a naive volume-based approximation.
     */
    public boolean canAccommodate(Package pkg) {
        return pkg.getWidth() <= width &&
               pkg.getHeight() <= height &&
               pkg.getDepth() <= depth &&
               (usedVolume + pkg.getVolume()) <= getVolume();
    }

    /**
     * Attempts to add a package to the container.
     * @return true if added successfully, false otherwise.
     */
    public boolean addPackage(Package pkg) {
        if (canAccommodate(pkg)) {
            packages.add(pkg);
            usedVolume += pkg.getVolume();
            return true;
        }
        return false;
    }

    public String getId() { return id; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }

    public double getVolume() {
        return width * height * depth;
    }

    public double getUtilization() {
        return getVolume() == 0 ? 0 : (usedVolume / getVolume()) * 100.0;
    }

    public double getRemainingVolume() {
        return getVolume() - usedVolume;
    }

    /**
     * Returns an unmodifiable list of packages to ensure immutability.
     */
    public List<Package> getPackages() {
        return Collections.unmodifiableList(packages);
    }

    @Override
    public String toString() {
        return String.format("Container[%s] %.2fx%.2fx%.2f, %.2f%% utilized",
                id, width, height, depth, getUtilization());
    }
}
