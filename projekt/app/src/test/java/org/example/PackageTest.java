package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PackageTest {
    private Package pkg;

    @BeforeEach
    void setUp() {
        pkg = new Package("TEST-1", 10.0, 20.0, 30.0);
    }

    @Test
    void testPackageCreation() {
        assertEquals("TEST-1", pkg.getId());
        assertEquals(10.0, pkg.getWidth());
        assertEquals(20.0, pkg.getHeight());
        assertEquals(30.0, pkg.getDepth());
    }

    @Test
    void testVolumeCalculation() {
        assertEquals(6000.0, pkg.getVolume());
    }

    @Test
    void testToString() {
        assertTrue(pkg.toString().contains("TEST-1"));
        assertTrue(pkg.toString().contains("10.00"));
    }
}
