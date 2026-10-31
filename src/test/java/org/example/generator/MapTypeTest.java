package org.example.generator;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MapTypeTest {

    private static final Generator generator = new Generator();

    @Test
    void generatesNonEmptyMap() throws ReflectiveOperationException {
        Map<?, ?> values = assertInstanceOf(Map.class, generator.generateValueOfType(Map.class));
        assertFalse(values.isEmpty());
        values.forEach((key, value) -> {
            assertInstanceOf(String.class, key);
            assertInstanceOf(Integer.class, value);
        });
    }
}
