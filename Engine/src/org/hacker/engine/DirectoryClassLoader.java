package org.hacker.engine;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * load classes from a given directory
 */
public class DirectoryClassLoader extends ClassLoader {
    private String root;

    public DirectoryClassLoader(String rootDir) {
    if (rootDir == null)
        throw new IllegalArgumentException("Null root directory");
    root = rootDir;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    // Since all support classes of loaded class use same class loader
    // must check subclass cache of classes for things like Object
    Class c = findLoadedClass(name);

    if (c == null) {
        // Convert class name argument to filename
        String[] split = name.split("\\.");
        String filename = split[split.length - 1] + ".class";

        try {
        byte data[] = loadClassData(filename);
        c = defineClass(name, data, 0, data.length);
        if (c == null)
            throw new ClassNotFoundException(name);
        }
        catch (IOException e) {
//                throw new ClassNotFoundException("Error reading file: " + filename);
        }
    }

    if (c == null) {
        try {
        c = findSystemClass(name);
//                System.out.println("Returning System Class: " + name);
        }
        catch (Exception e) {
        }
    }

    if (resolve)
        resolveClass(c);
    return c;
    }

    private byte[] loadClassData(String filename) throws IOException {
    // Create a file object relative to directory provided
    File f = new File(root, filename);

    // Get size of class file
    int size = (int) f.length();

    // Reserve space to read
    byte buff[] = new byte[size];

    // Get stream to read from
    FileInputStream fis = new FileInputStream(f);
    DataInputStream dis = new DataInputStream(fis);

    // Read in data
    dis.readFully(buff);

    // close stream
    dis.close();

    // return data
    return buff;
    }
}
