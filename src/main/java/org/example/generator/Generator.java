package org.example.generator;

import org.example.exception.NoImplementationFoundException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Generator {

    private final Random random = new Random();
    private final List<Class<?>> WRAPPER_TYPES = List.of(Byte.class, Short.class, Integer.class,
            Long.class, Float.class, Double.class, Boolean.class, String.class, Character.class);

    public Object generateValueOfType(Class<?> clazz) throws ReflectiveOperationException {
        return generateClass(clazz, new HashSet<>());
    }

    public Object generateValueOfType(Class<?> clazzList, Class<?> elementType) throws ReflectiveOperationException {
        return generateClass(clazzList, new HashSet<>(), elementType);
    }

    private Object generateClass(Class<?> clazz, Set<Class<?>> constructing, Class<?> elementType) throws ReflectiveOperationException {
        if (List.class.isAssignableFrom(clazz) && clazz.isAssignableFrom(ArrayList.class)) {
            return generateList(elementType, constructing);
        }
        throw new IllegalArgumentException("elementType mast be type List<Type>");
    }

    private Object generateClass(Class<?> clazz, Set<Class<?>> constructing) throws ReflectiveOperationException {

        if (isNumber(clazz)) {
            return generateNumber(clazz, random);
        }

        if (clazz == char.class) {
            return (char) random.nextInt('a', 'z' + 1);
        } else if (clazz == Character.class) {
            return (char) random.nextInt('A', 'Z' + 1);
        }

        if (clazz == boolean.class || clazz == Boolean.class) {
            return random.nextBoolean();
        }
        if (clazz == String.class) {
            return getRandomString(random);
        }

        if (clazz.isEnum()) {
            Object[] constants = clazz.getEnumConstants();
            if (constants.length == 0) {
                throw new IllegalArgumentException("Cannot generate value for empty enum: " + clazz.getName());
            }
            return constants[random.nextInt(constants.length)];
        }

        int length = random.nextInt(1, 6);

        if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            Object array = Array.newInstance(componentType, length);

            for (int i = 0; i < length; i++) {
                Array.set(array, i, generateClass(componentType, constructing));
            }
            return array;
        }

        if (List.class.isAssignableFrom(clazz) && clazz.isAssignableFrom(ArrayList.class)) {
            return generateValueOfType(clazz, WRAPPER_TYPES.get(random.nextInt(WRAPPER_TYPES.size())));
        }

        if (Map.class.isAssignableFrom(clazz) && clazz.isAssignableFrom(HashMap.class)) {
            Map<String, Integer> values = new HashMap<>();

            for (int i = 0; i < length; i++) {
                String key = (String) generateClass(String.class, constructing);
                Integer value = (Integer) generateClass(Integer.class, constructing);
                values.put(key, value);
            }
            return values;
        }

        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            Class<?> implementation = chooseImplementation(clazz, random);
            return generateClass(implementation, constructing);
        }

        if (!constructing.add(clazz)) {
            return null;
        }
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors.length == 0) {
                throw new IllegalArgumentException("No constructors for " + clazz.getName());
            }
            Constructor<?> constructor = constructors[random.nextInt(constructors.length)];
            Type[] parameterTypes = constructor.getGenericParameterTypes();
            Object[] args = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                args[i] = generateType(parameterTypes[i], constructing);
            }
            return constructor.newInstance(args);
        } finally {
            constructing.remove(clazz);
        }
    }

    private Object generateType(Type type, Set<Class<?>> constructing) throws ReflectiveOperationException {

        if (type instanceof ParameterizedType parameterized
                && parameterized.getRawType() instanceof Class<?> rawClass
                && List.class.isAssignableFrom(rawClass)
                && rawClass.isAssignableFrom(ArrayList.class)) {

            Type elementType = parameterized.getActualTypeArguments()[0];
            int length = new Random().nextInt(1, 6);
            List<Object> values = new ArrayList<>(length);

            for (int i = 0; i < length; i++) {
                values.add(generateType(elementType, constructing));
            }
            return values;
        }

        if (type instanceof Class<?> clazz) {
            return generateClass(clazz, constructing);
        }

        throw new IllegalArgumentException("Unsupported parameter type: " + type);
    }

    private Class<?> chooseImplementation(Class<?> requestedType, Random random) {
        List<Class<?>> canImplementations = new ArrayList<>();

        for (Class<?> candidate : getCandidates()) {
            Generatable annotation = candidate.getAnnotation(Generatable.class);

            if (annotation != null
                    && (requestedType.isAssignableFrom(candidate) || !Modifier.isAbstract(candidate.getModifiers()))) {
                Class<?>[] impls = annotation.impls();
                for (Class<?> impl : impls) {
                    if (requestedType.isAssignableFrom(impl)) {
                        canImplementations.add(impl);
                    }
                }
            }
        }

        if (canImplementations.isEmpty()) {
            throw new NoImplementationFoundException("No concrete @Generatable implementation for " + requestedType.getName());
        }

        return canImplementations.get(random.nextInt(canImplementations.size()));
    }

    private <T> List<T> generateList(Class<T> elementType, Set<Class<?>> constructing) throws ReflectiveOperationException {
        if (elementType == null) {
            throw new IllegalArgumentException("elementType must not be null");
        }

        List<T> values = new ArrayList<>();
        int length = random.nextInt(1, 6);

        for (int i = 0; i < length; i++) {
            T value = elementType.cast(generateClass(elementType, constructing));
            values.add(value);
        }
        return values;
    }

    private boolean isNumber(Class<?> clazz) {
        return isInt(clazz) || isByte(clazz) || isShort(clazz) || isLong(clazz) || isFloat(clazz) || isDouble(clazz);
    }

    private Object generateNumber(Class<?> clazz, Random random) {
        if (isInt(clazz)) {
            return random.nextInt();
        } else if (isByte(clazz)) {
            return (byte) random.nextInt();
        } else if (isShort(clazz)) {
            return (short) random.nextInt();
        } else if (isLong(clazz)) {
            return random.nextLong();
        } else if (isFloat(clazz)) {
            return random.nextFloat();
        } else if (isDouble(clazz)) {
            return random.nextDouble();
        }
        throw new IllegalArgumentException();
    }

    private boolean isInt(Class<?> clazz) {
        return clazz == int.class || clazz == Integer.class;
    }

    private boolean isByte(Class<?> clazz) {
        return clazz == byte.class || clazz == Byte.class;
    }

    private boolean isShort(Class<?> clazz) {
        return clazz == short.class || clazz == Short.class;
    }

    private boolean isLong(Class<?> clazz) {
        return clazz == long.class || clazz == Long.class;
    }

    private boolean isFloat(Class<?> clazz) {
        return clazz == float.class || clazz == Float.class;
    }

    private boolean isDouble(Class<?> clazz) {
        return clazz == double.class || clazz == Double.class;
    }

    private String getRandomString(Random random) {
        int length = random.nextInt(1, 11);
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append((char) random.nextInt('a', 'z' + 1));
        }
        return result.toString();
    }

    private Class<?>[] getCandidates() {
        try {
            return ProjectClassScanner.getAllClasses(Path.of("build/classes/java/main"));
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
