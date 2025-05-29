package org.example;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private final String id;
    private final double width;
    private final double height;
    private final double depth;
    private final List<Package> packages;
    private double usedVolume;

    public Container(String id, double width, double height, double depth) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.packages = new ArrayList<>();
        this.usedVolume = 0;
    }

    public boolean canFit(Package pkg) {
        return pkg.getWidth() <= width &&
               pkg.getHeight() <= height &&
               pkg.getDepth() <= depth;
    }

    public boolean addPackage(Package pkg) {
        if (canFit(pkg)) {
            packages.add(pkg);
            usedVolume += pkg.getVolume();
            return true;
        }
        return false;
    }

    public double getUtilization() {
        return usedVolume / (width * height * depth) * 100;
    }

    public List<Package> getPackages() {
        return packages;
    }
}
