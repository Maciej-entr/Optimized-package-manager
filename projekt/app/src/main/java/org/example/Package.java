package org.example;

public class Package {
    // Pola klasy: id i wymiary paczki
    private final String id;
    private final double width;
    private final double height;
    private final double depth;

    // Konstruktor: tworzy paczkę z podanym id i wymiarami
    public Package(String id, double width, double height, double depth) {
        this.id = id;         // Ustawienie identyfikatora
        this.width = width;   // Ustawienie szerokości
        this.height = height; // Ustawienie wysokości
        this.depth = depth;   // Ustawienie głębokości
    }

    // Zwraca identyfikator paczki
    public String getId() { 
        return id; 
    }

    // Zwraca szerokość paczki
    public double getWidth() { 
        return width; 
    }

    // Zwraca wysokość paczki
    public double getHeight() { 
        return height; 
    }

    // Zwraca głębokość paczki
    public double getDepth() { 
        return depth; 
    }

    // Oblicza i zwraca objętość paczki
    public double getVolume() { 
        return width * height * depth; 
    }

    // Przesłonięta metoda toString, która ładnie reprezentuje paczkę
    @Override
    public String toString() {
        return String.format("Package[id=%s, %.2fx%.2fx%.2f]", id, width, height, depth); 
    }
}
