package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PackingSimulatorTest {
    private PackingSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new PackingSimulator();
    }

    @Test
    void testPackageGeneration() {
        simulator.generatePackages(5);
        assertEquals(5, simulator.getPendingPackages().size());
    }

    @Test
    void testSimulationRun() {
        simulator.generatePackages(10);
        simulator.runSimulation();
        assertTrue(simulator.getContainers().size() > 0);
    }

    @Test
    void testZeroPackages() {
        simulator.generatePackages(0);
        simulator.runSimulation();
        assertEquals(0, simulator.getContainers().size());
    }
}
