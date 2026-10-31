package org.example.generator;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class StringTest {

    private static final Generator generator = new Generator();

    @Test
    void generatesStringTest() throws ReflectiveOperationException {
        assertInstanceOf(String.class, generator.generateValueOfType(String.class));
    }
}
