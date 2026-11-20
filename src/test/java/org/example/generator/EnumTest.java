package org.example.generator;

import org.example.classes.Color;
import org.example.classes.Empty;
import org.example.classes.UserType;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumTest {

    private static final Generator generator = new Generator();

    @Test
    void generatesEnumTest() throws ReflectiveOperationException {
        assertInstanceOf(UserType.class, generator.generateValueOfType(UserType.class));
        assertInstanceOf(Color.class, generator.generateValueOfType(Color.class));

        assertThrows(IllegalArgumentException.class, () -> generator.generateValueOfType(Empty.class));
    }
}
