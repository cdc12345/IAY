package org.cdc.liberate;

import java.net.URLClassLoader;

public class InjectClassLoader extends ClassLoader {

    private final ClassLoader parent;
    private final ClassLoader inject;

    public InjectClassLoader(URLClassLoader inject, ClassLoader mcreator) {
        this.inject = inject;
        this.parent = mcreator;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> class1 = null;
        try {
            class1 = inject.loadClass(name);
            return class1;
        } catch (Exception ignored){
        }
        return parent.loadClass(name);
    }
}
