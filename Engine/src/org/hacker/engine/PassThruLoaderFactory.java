package org.hacker.engine;

public class PassThruLoaderFactory implements ClassLoaderFactory {

	private ClassLoader cl;

	public PassThruLoaderFactory(ClassLoader cl) {
		this.cl = cl;
	}

	public ClassLoader createClassLoader() {
		return cl;
	}

}
