package org.example.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BaseTypesTest {

    private static final Generator generator = new Generator();

    @Test
    void generatesIntForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Integer.class, generator.generateValueOfType(int.class));
        assertInstanceOf(Integer.class, generator.generateValueOfType(Integer.class));
    }

    @Test
    void generatesByteForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Byte.class, generator.generateValueOfType(byte.class));
        assertInstanceOf(Byte.class, generator.generateValueOfType(Byte.class));
    }

    @Test
    void generatesShortForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Short.class, generator.generateValueOfType(short.class));
        assertInstanceOf(Short.class, generator.generateValueOfType(Short.class));
    }

    @Test
    void generatesLongForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Long.class, generator.generateValueOfType(long.class));
        assertInstanceOf(Long.class, generator.generateValueOfType(Long.class));
    }

    @Test
    void generatesFloatForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Float.class, generator.generateValueOfType(float.class));
        assertInstanceOf(Float.class, generator.generateValueOfType(Float.class));
    }

    @Test
    void generatesDoubleForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Double.class, generator.generateValueOfType(double.class));
        assertInstanceOf(Double.class, generator.generateValueOfType(Double.class));
    }

    @Test
    void generatesCharForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Character.class, generator.generateValueOfType(char.class));
        assertInstanceOf(Character.class, generator.generateValueOfType(Character.class));
    }

    @Test
    void generatesBooleanForPrimitiveAndWrapper() throws ReflectiveOperationException {
        assertInstanceOf(Boolean.class, generator.generateValueOfType(boolean.class));
        assertInstanceOf(Boolean.class, generator.generateValueOfType(Boolean.class));
    }
}
