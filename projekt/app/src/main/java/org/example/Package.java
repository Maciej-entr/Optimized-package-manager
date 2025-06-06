package main.java.org.example;
import java.util.ArrayList;

/**
 * Represents a single package with dimensions and ID.
 * Immutable after construction.
 */
public class Package {
    private final String id;
    private final double width;
    private final double height;
    private final double depth;

    /**
     * Constructs a package with the specified ID and dimensions.
     * All dimensions must be positive and finite.
     */
    public Package(String id, double width, double height, double depth) {
        if (width <= 0 || height <= 0 || depth <= 0 ||
            Double.isNaN(width) || Double.isNaN(height) || Double.isNaN(depth) ||
            Double.isInfinite(width) || Double.isInfinite(height) || Double.isInfinite(depth)) {
            throw new IllegalArgumentException("All dimensions must be positive and finite.");
        }
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public String getId() { return id; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }

    /**
     * Returns the volume of the package in cubic meters.
     */
    public double getVolume() {
        return width * height * depth;
    }

    @Override
    public String toString() {
        return String.format("Package[id=%s, %.2fx%.2fx%.2f m]", id, width, height, depth);
    }

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Package)) return false;
    Package p = (Package) o;
    return id.equals(p.id);
}

@Override
public int hashCode() {
    return id.hashCode();
}
}
