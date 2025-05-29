package org.example;

public class Package {
    private final String id;
    private final double width;
    private final double height;
    private final double depth;

    public Package(String id, double width, double height, double depth) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public String getId() { return id; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getDepth() { return depth; }
    public double getVolume() { return width * height * depth; }

    @Override
    public String toString() {
        return String.format("Package[id=%s, %.2fx%.2fx%.2f]", id, width, height, depth);
    }
}
