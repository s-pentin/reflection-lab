package org.example.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public final class ProjectClassScanner {

    private ProjectClassScanner() {
    }

    public static Class<?>[] getAllClasses(Path classesRoot) throws IOException {
        Path root = Objects.requireNonNull(classesRoot, "classesRoot").toAbsolutePath().normalize();
        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("Not a classes directory: " + root);
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ProjectClassScanner.class.getClassLoader();
        }
        ClassLoader loader = classLoader;

        try (Stream<Path> files = Files.walk(root)) {
            return files.filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().endsWith(".class"))
                    .filter(file -> !file.getFileName().toString().equals("module-info.class"))
                    .filter(file -> !file.getFileName().toString().equals("package-info.class"))
                    .sorted()
                    .map(file -> loadClass(root, file, loader))
                    .toArray(Class<?>[]::new);
        }
    }

    private static Class<?> loadClass(Path root, Path classFile, ClassLoader loader) {
        String relativePath = root.relativize(classFile).toString();
        String className = relativePath.substring(0, relativePath.length() - ".class".length()).replace(File.separatorChar, '.');
        try {
            return Class.forName(className, false, loader);
        } catch (ClassNotFoundException | LinkageError e) {
            throw new IllegalStateException("Cannot load class " + className + " from " + root, e);
        }
    }
}
