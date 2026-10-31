package org.example.generator;

import org.example.classes.AbstractMessage;
import org.example.classes.BinaryTreeNode;
import org.example.classes.Cart;
import org.example.classes.Parcel;
import org.example.classes.Product;
import org.example.classes.Shape;
import org.example.classes.TextMessage;
import org.example.classes.UnimplementedService;
import org.example.exception.NoImplementationFoundException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerationClassTest {

    private final Generator generator = new Generator();

    @Test
    void generatesShapeImplementation() throws ReflectiveOperationException {
        Shape value = assertInstanceOf(Shape.class, generator.generateValueOfType(Shape.class));
        assertNotNull(value);

        Generatable annotation = value.getClass().getAnnotation(Generatable.class);
        assertNotNull(annotation);
        assertEquals(Shape.class, annotation.implement());
    }

    @Test
    void generatesCartWithProducts() throws ReflectiveOperationException {
        Cart cart = assertInstanceOf(Cart.class, generator.generateValueOfType(Cart.class));
        List<?> items = cart.getItems();

        assertNotNull(items);
        assertFalse(items.isEmpty());
        items.forEach(item -> assertInstanceOf(Product.class, item));
    }

    @Test
    void generatesLeafBinaryTreeNode() {
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            BinaryTreeNode root = assertInstanceOf(BinaryTreeNode.class, generator.generateValueOfType(BinaryTreeNode.class));
            assertNotNull(root.getData());
            assertNull(root.getLeft());
            assertNull(root.getRight());
        });
    }

    @Test
    void generatesConcreteImplementationOfAbstractClass() throws ReflectiveOperationException {
        AbstractMessage message = assertInstanceOf(AbstractMessage.class, generator.generateValueOfType(AbstractMessage.class));

        assertInstanceOf(TextMessage.class, message);
        assertFalse(message.getText().isBlank());
    }

    @Test
    void explainsWhenInterfaceHasNoImplementation() {
        NoImplementationFoundException error = assertThrows(NoImplementationFoundException.class,
                () -> generator.generateValueOfType(UnimplementedService.class));

        assertNotNull(error.getMessage());
        assertTrue(error.getMessage().contains(UnimplementedService.class.getSimpleName()));
    }

    @Test
    void generatesNewUserDefinedClassWithoutSpecialCase() throws ReflectiveOperationException {
        Parcel parcel = assertInstanceOf(Parcel.class, generator.generateValueOfType(Parcel.class));

        assertFalse(parcel.code().isBlank());
        assertNotNull(parcel.color());
    }
}
