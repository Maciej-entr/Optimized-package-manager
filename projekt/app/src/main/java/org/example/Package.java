package org.example;

/**
 * Represents an immutable package with dimensions and ID.
 * Validates all inputs during construction.
 */
public final class Package {
    private final String id;
    private final double width;
    private final double height;
    private final double depth;

    public Package(String id, double width, double height, double depth) {
        validateDimensions(width, height, depth);
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    private void validateDimensions(double width, double height, double depth) {
        if (width <= 0 || height <= 0 || depth <= 0 || 
            Double.isInfinite(width) || Double.isInfinite(height) || Double.isInfinite(depth)) {
            throw new IllegalArgumentException("Dimensions must be positive and finite.");
        }
    }

    // Getters (no setters for immutability)
    public String getId() { return id; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }
    public double getVolume() { return width * height * depth; }

    @Override
    public String toString() {
        return String.format("Package[id=%s, %.2fx%.2fx%.2f m]", id, width, height, depth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Package)) return false;
        Package pkg = (Package) o;
        return id.equals(pkg.id) &&
               Double.compare(pkg.width, width) == 0 &&
               Double.compare(pkg.height, height) == 0 &&
               Double.compare(pkg.depth, depth) == 0;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}