package org.example.generator;

import org.example.classes.Rectangle;
import org.example.classes.Triangle;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectClassScannerTest {

    @Test
    void findsCompiledClassesInSubdirectories() throws Exception {
        Path classesRoot = Path.of(ProjectClassScannerTest.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        Class<?>[] classes = ProjectClassScanner.getAllClasses(classesRoot);

        assertTrue(Arrays.asList(classes).contains(ProjectClassScannerTest.class));
    }

    @Test
    void findsShapeImplementationsInMainClasses() throws Exception {
        Path classesRoot = Path.of(Rectangle.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        Class<?>[] classes = ProjectClassScanner.getAllClasses(classesRoot);

        assertTrue(Arrays.asList(classes).contains(Rectangle.class));
        assertTrue(Arrays.asList(classes).contains(Triangle.class));
    }
}
