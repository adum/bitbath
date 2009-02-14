package org.hacker.engine;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * overrides random to use our custom version
 */
public class DirectoryClassLoaderRandom extends ClassLoader {
    private String root;
        private ClassLoader nextChoice;
        private long seed;

    public DirectoryClassLoaderRandom(String rootDir, ClassLoader nextChoice, long seed) {
        if (rootDir == null)
            throw new IllegalArgumentException("Null root directory");
        root = rootDir;
        this.nextChoice = nextChoice;
        this.seed = seed;
    }

    @SuppressWarnings("unchecked")
        @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // Since all support classes of loaded class use same class loader
        // must check subclass cache of classes for things like Object
        Class c = findLoadedClass(name);
//        ClassLoader parent2 = this.getParent();
//        if (name.equals("javh.util.Random")) {
//              c = parent2.loadClass(name);
//        }
        
        if (c == null) {
            // Convert class name argument to filename
            String[] split = name.split("\\.");
            String filename = split[split.length - 1] + ".class";
            
            if (name.equals("javh.util.Random")) {
                filename = name.replace('.', '/') + ".class";
            }

            try {
                byte data[] = loadClassData(filename);
//                if (name.equals("java.util.Random"))
//                      name = "h" + name;
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
                if (nextChoice != null && name.equals("javh.util.Random")) {
                        c = nextChoice.loadClass(name);
                        if (c != null) {
                                Field field = c.getDeclaredField("hackedStartingSeed");
                                field.setAccessible(true);
                                field.setLong(c, seed);
//                              System.out.println("setttt");
                        }
                }
                else
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
        
        // change "java/util/Random" to "hava/util/Random"
        char[] cs = new String("java/util/Random").toCharArray();
        loop: for (int start = 0; start < size - cs.length; start++) {
                for (int x = 0; x < cs.length; x++)
                        if (buff[start + x] != cs[x])
                                continue loop;
                buff[start + 3] = 'h';
        }

        // close stream
        dis.close();

        // return data
        return buff;
    }
}
