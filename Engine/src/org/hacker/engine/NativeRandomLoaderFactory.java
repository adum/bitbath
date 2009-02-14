package org.hacker.engine;

import java.util.Random;

/**
 * use Random overriding
 */
public class NativeRandomLoaderFactory implements ClassLoaderFactory {
        private String runtimePath;
        private String dirBot;
        private Random r;

        public NativeRandomLoaderFactory(String dirBot, String runtimePath, long seed) {
                this.dirBot = dirBot;
                this.runtimePath = runtimePath;
                r = new Random(seed);
        }
        
        public ClassLoader createClassLoader() {
        ClassLoader clRuntime = null;
        if (runtimePath != null) {
                clRuntime = new DirectoryClassLoaderRandom(runtimePath, null, 0);
        }
        ClassLoader cl;
        cl = new DirectoryClassLoaderRandom(dirBot, clRuntime, r.nextLong());
                return cl;
        }
}
