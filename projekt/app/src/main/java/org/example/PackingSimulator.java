package org.example;

import java.util.*;

/**
 * Klasa PackingSimulator służy do symulacji procesu pakowania paczek do kontenerów.
 * Umożliwia generowanie paczek o losowych wymiarach, próbuje je zapakować do istniejących kontenerów
 * lub tworzy nowe kontenery w razie potrzeby. Na koniec wyświetla statystyki symulacji.
 */
public class PackingSimulator {
    // Pola klasy: lista kontenerów, lista paczek oczekujących, generator liczb losowych oraz domyślny rozmiar kontenera.
    private final List<Container> containers;
    private final List<Package> pendingPackages;
    private final Random random;
    private static final double DEFAULT_CONTAINER_SIZE = 100.0;

    // Konstruktor: inicjalizuje listy oraz obiekt Random.
    public PackingSimulator() {
        this.containers = new ArrayList<>();
        this.pendingPackages = new ArrayList<>();
        this.random = new Random();
    }

    // Generuje paczki o losowych wymiarach (do 50 jednostek) i dodaje je do listy oczekujących.
    public void generatePackages(int count) {
        for (int i = 0; i < count; i++) {
            pendingPackages.add(new Package(
                "PKG-" + (i + 1),
                random.nextDouble() * 50,
                random.nextDouble() * 50,
                random.nextDouble() * 50
            ));
        }
    }

    // Przeprowadza symulację pakowania paczek do dostępnych kontenerów.
    public void runSimulation() {
        // Iterujemy po kopii listy, aby uniknąć modyfikacji oryginalnej listy podczas iteracji.
        for (Package pkg : new ArrayList<>(pendingPackages)) {
            boolean packed = false;
            
            // Próba zapakowania paczki do istniejących kontenerów.
            for (Container container : containers) {
                if (container.addPackage(pkg)) {
                    packed = true;
                    pendingPackages.remove(pkg); // Usuwamy zapakowaną paczkę z listy
                    break;
                }
            }

            // Jeśli paczka nie zmieściła się w żadnym kontenerze, tworzymy nowy kontener.
            if (!packed) {
                Container newContainer = new Container(
                    "CNT-" + (containers.size() + 1),
                    DEFAULT_CONTAINER_SIZE,
                    DEFAULT_CONTAINER_SIZE,
                    DEFAULT_CONTAINER_SIZE
                );
                if (newContainer.addPackage(pkg)) {
                    containers.add(newContainer); // Dodajemy nowy kontener do listy
                    pendingPackages.remove(pkg); // Usuwamy paczkę po zapakowaniu
                }
            }
        }
    }

    // Wyświetla statystyki symulacji: liczbę kontenerów użytych, liczbę paczek nie zapakowanych
    // oraz średnie wykorzystanie kontenerów.
    public void printStatistics() {
        System.out.println("\n=== Packing Simulation Statistics ===");
        System.out.println("Total containers used: " + containers.size());
        System.out.println("Packages not packed: " + pendingPackages.size());
        
        double avgUtilization = containers.stream()
            .mapToDouble(Container::getUtilization)
            .average()
            .orElse(0.0);
        
        System.out.printf("Average container utilization: %.2f%%\n", avgUtilization);
    }

    // Zwraca listę kontenerów użytych podczas symulacji.
    public List<Container> getContainers() {
        return containers;
    }

    // Zwraca listę paczek, które nie zostały zapakowane.
    public List<Package> getPendingPackages() {
        return pendingPackages;
    }
}
