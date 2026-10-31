package org.example.generator;

import org.example.classes.Color;
import org.example.classes.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayTypesTest {

    private static final Generator generator = new Generator();

    @Test
    void generatesNonEmptyIntArray() throws ReflectiveOperationException {
        int[] values = assertInstanceOf(int[].class, generator.generateValueOfType(int[].class));
        assertNotNull(values);
        assertTrue(values.length > 0);
    }

    @Test
    void generatesNonEmptyStringArray() throws ReflectiveOperationException {
        String[] values = assertInstanceOf(String[].class, generator.generateValueOfType(String[].class));
        assertNotNull(values);
        assertTrue(values.length > 0);
    }

    @Test
    void generatesNonEmptyColorArray() throws ReflectiveOperationException {
        Color[] values = assertInstanceOf(Color[].class, generator.generateValueOfType(Color[].class));
        assertNotNull(values);
        assertTrue(values.length > 0);
    }

    @Test
    void generatesNonEmptyRectangleArray() throws ReflectiveOperationException {
        Rectangle[] values = assertInstanceOf(Rectangle[].class, generator.generateValueOfType(Rectangle[].class));
        assertNotNull(values);
        assertTrue(values.length > 0);
    }

    @Test
    void generatesNonEmptyList() throws ReflectiveOperationException {
        List<?> values = assertInstanceOf(List.class, generator.generateValueOfType(List.class));
        assertFalse(values.isEmpty());
        values.forEach(value -> assertInstanceOf(Integer.class, value));
    }
}
