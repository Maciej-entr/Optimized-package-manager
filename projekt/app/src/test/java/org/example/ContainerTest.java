package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    private Container container;
    private Package smallPackage;
    private Package largePackage;

    @BeforeEach
    void setUp() {
        container = new Container("CNT-1", 100.0, 100.0, 100.0);
        smallPackage = new Package("SMALL-1", 10.0, 10.0, 10.0);
        largePackage = new Package("LARGE-1", 150.0, 150.0, 150.0);
    }

    @Test
    void testPackageFitting() {
        assertTrue(container.canFit(smallPackage));
        assertFalse(container.canFit(largePackage));
    }

    @Test
    void testAddPackage() {
        assertTrue(container.addPackage(smallPackage));
        assertFalse(container.addPackage(largePackage));
    }

    @Test
    void testUtilization() {
        container.addPackage(smallPackage);
        double expectedUtilization = (10.0 * 10.0 * 10.0) / (100.0 * 100.0 * 100.0) * 100;
        assertEquals(expectedUtilization, container.getUtilization(), 0.001);
    }
}
