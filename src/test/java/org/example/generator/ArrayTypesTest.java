package org.example.generator;

import org.example.classes.Color;
import org.example.classes.Empty;
import org.example.classes.Rectangle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayTypesTest {

    private final Generator generator = new Generator();
    private final List<Class<?>> WRAPPER_TYPES = List.of(Byte.class, Short.class, Integer.class,
            Long.class, Float.class, Double.class, Boolean.class, String.class, Character.class);

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
        assertTrue(values.stream().allMatch(value ->
                WRAPPER_TYPES.stream().anyMatch(clazz -> clazz.isInstance(value))));
    }

    @Test
    void generatesNonEmptyStringList() throws ReflectiveOperationException {
        List<?> values = assertInstanceOf(List.class, generator.generateValueOfType(List.class, String.class));
        assertFalse(values.isEmpty());
        values.forEach(value -> assertInstanceOf(String.class, value));
    }

    @Test
    void generatesNonEmptyIntegerList() throws ReflectiveOperationException {
        List<?> values = assertInstanceOf(List.class, generator.generateValueOfType(List.class, Integer.class));
        assertFalse(values.isEmpty());
        values.forEach(value -> assertInstanceOf(Integer.class, value));
    }

    @Test
    void generatesNonEmptyByteList() throws ReflectiveOperationException {
        List<?> values = assertInstanceOf(List.class, generator.generateValueOfType(List.class, Byte.class));
        assertFalse(values.isEmpty());
        values.forEach(value -> assertInstanceOf(Byte.class, value));
    }

    @Test
    void generatesNonEmptyBooleanList() throws ReflectiveOperationException {
        List<?> values = assertInstanceOf(List.class, generator.generateValueOfType(List.class, Boolean.class));
        assertFalse(values.isEmpty());
        values.forEach(value -> assertInstanceOf(Boolean.class, value));
    }

    @Test
    void generatesErrorList() {
        assertThrows(IllegalArgumentException.class, () -> generator.generateValueOfType(List.class, null));
    }
}
