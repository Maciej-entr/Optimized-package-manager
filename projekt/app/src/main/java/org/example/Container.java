package org.example;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a container for packages with volume tracking.
 * Thread-safe for concurrent access.
 */
public final class Container {
    private final String id;
    private final double width;
    private final double height;
    private final double depth;
    private final List<Package> packages = new ArrayList<>();
    private double usedVolume = 0;

    public Container(String id, double width, double height, double depth) {
        validateDimensions(width, height, depth);
        this.id = Objects.requireNonNull(id, "Container ID cannot be null.");
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    private void validateDimensions(double width, double height, double depth) {
        if (width <= 0 || height <= 0 || depth <= 0) {
            throw new IllegalArgumentException("Container dimensions must be positive.");
        }
    }

    public synchronized boolean canAccommodate(Package pkg) {
        Objects.requireNonNull(pkg, "Package cannot be null.");
        return pkg.getWidth() <= width &&
               pkg.getHeight() <= height &&
               pkg.getDepth() <= depth &&
               (usedVolume + pkg.getVolume()) <= getVolume();
    }

    public synchronized boolean addPackage(Package pkg) {
        if (canAccommodate(pkg)) {
            packages.add(pkg);
            usedVolume += pkg.getVolume();
            return true;
        }
        return false;
    }

    // Getters (thread-safe)
    public String getId() { return id; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }
    public double getVolume() { return width * height * depth; }
    public double getRemainingVolume() { return getVolume() - usedVolume; }
    public double getUtilization() { return (usedVolume / getVolume()) * 100.0; }
    public List<Package> getPackages() { return Collections.unmodifiableList(packages); }

    public double getCurrentHeight() {
        return packages.stream()
            .mapToDouble(Package::getHeight)
            .max()
            .orElse(0.0);
    }
}