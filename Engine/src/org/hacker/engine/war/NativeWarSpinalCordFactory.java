package org.hacker.engine.war;

import org.hacker.engine.ClassLoaderFactory;

/**
 * builds NativeWarSpinalCord
 */
public class NativeWarSpinalCordFactory implements WarSpinalCordFactory {
    private String className;
        private ClassLoaderFactory clf;

    public NativeWarSpinalCordFactory(ClassLoaderFactory clf, String className) {
        this.clf = clf;
        this.className = className;
    }

    public WarSpinalCord createSpinalCord() throws Exception {
        ClassLoader cl = clf.createClassLoader();
        Class<?> c = cl.loadClass(className);
        Object obj = c.newInstance();
        NativeWarSpinalCord spinal = new NativeWarSpinalCord(obj, className);
        return spinal;
    }

        public String getName() {
                return className;
        }

        @Override
        public String toString() {
                return className;
        }
}
