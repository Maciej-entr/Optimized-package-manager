package org.example;

import java.util.ArrayList;
import java.util.List;

public class Container {
    // Pola klasy: id, wymiary, lista paczek oraz użyta objętość
    private final String id;
    private final double width;
    private final double height;
    private final double depth;
    private final List<Package> packages;
    private double usedVolume;

    // Konstruktor: inicjalizuje kontener z podanymi wymiarami i pustą listą paczek
    public Container(String id, double width, double height, double depth) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.packages = new ArrayList<>();
        this.usedVolume = 0;
    }

    // Sprawdza, czy paczka zmieści się w kontenerze na podstawie jej wymiarów
    public boolean canFit(Package pkg) {
        return pkg.getWidth() <= width &&
               pkg.getHeight() <= height &&
               pkg.getDepth() <= depth;
    }

    // Próbuje dodać paczkę. Jeśli się mieści, dodaje ją i aktualizuje wykorzystaną objętość
    public boolean addPackage(Package pkg) {
        if (canFit(pkg)) {
            packages.add(pkg);
            usedVolume += pkg.getVolume();
            return true;
        }
        return false;
    }

    // Oblicza procentowe wykorzystanie kontenera bazując na objętości użytej i całkowitej
    public double getUtilization() {
        return usedVolume / (width * height * depth) * 100;
    }

    // Zwraca listę paczek znajdujących się w kontenerze
    public List<Package> getPackages() {
        return packages;
    }
}
