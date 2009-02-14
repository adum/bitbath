package org.hacker.engine;

/**
 * uses given bytes to load a class
 */
public class SingularClassLoader extends ClassLoader {
    private byte[] buff;
    private String className;

    public SingularClassLoader(byte[] buff, String className) {
        if (buff == null)
            throw new IllegalArgumentException("Null buff");
        this.buff = buff;
        this.className = className;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // Since all support classes of loaded class use same class loader
        // must check subclass cache of classes for things like Object
        Class c = findLoadedClass(name);

        if (c == null) {
            if (name.equals(className)) {
                try {
                    c = defineClass(name, buff, 0, buff.length);
                    if (c == null)
                        throw new ClassNotFoundException(name);
                }
                catch (Exception e) {
                    throw new ClassNotFoundException("Error reading file: " + name);
                }
            }
            else {
                try {
                    c = findSystemClass(name);
//                    System.out.println("Returning System Class: " + name);
                }
                catch (Exception e) {
                }
            }
        }

        if (resolve)
            resolveClass(c);
        return c;
    }
}