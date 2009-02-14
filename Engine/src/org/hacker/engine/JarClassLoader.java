package org.hacker.engine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * load from a JAR file
 */
public class JarClassLoader  extends ClassLoader {
    private Map<String, byte[]> jarClasses = new HashMap<String, byte[]>();

    public JarClassLoader(JarInputStream stream) {
        if (stream == null)
            throw new IllegalArgumentException("Null stream");
        try {
            JarEntry entry;
            while ((entry = stream.getNextJarEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    byte[] buf = new byte[1024];
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    int nBytes; 
                    while((nBytes = stream.read(buf)) != -1) { 
                        os.write(buf, 0, nBytes); 
                    } 
                    byte[] data = os.toByteArray();
                    jarClasses.put(entry.getName(), data);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // Since all support classes of loaded class use same class loader
        // must check subclass cache of classes for things like Object
        Class c = findLoadedClass(name);

        if (c == null) {
            // Convert class name argument to filename
            String filename = name.replace('.', '/') + ".class";

            try {
                byte data[] = loadClassData(filename);
                if (data != null) {
                    c = defineClass(name, data, 0, data.length);
                    if (c == null)
                        throw new ClassNotFoundException(name);
                }
            }
            catch (IOException e) {
//                throw new ClassNotFoundException("Error reading file: " + filename);
            }
        }

        if (c == null) {
            try {
                c = findSystemClass(name);
//                System.out.println("Returning System Class");
            }
            catch (Exception e) {
            }
        }

        if (resolve)
            resolveClass(c);
        return c;
    }

    private byte[] loadClassData(String filename) throws IOException {
        return jarClasses.get(filename);
    }
}
