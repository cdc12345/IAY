package org.cdc.liberate.visitors;

import javassist.CtClass;
import javassist.NotFoundException;
import org.cdc.liberate.Liberate;
import org.cdc.liberate.transfer.ClassVisitor;

import java.util.List;

/**
 *
 */
public class SomeClassVersionFixer extends ClassVisitor {

    @SuppressWarnings("all")
    private final int major = 61;
    private final List<String> classList = List.of("net.mcreator.gradle.IsolatedGradleClassLoader","net.mcreator.gradle.GradleSyncBuildAction");

    public SomeClassVersionFixer() {
        super(2);
    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if (classList.contains(className)) {
            ctClass.getClassFile().setMajorVersion(major);
            finishOneTask();
        }
    }
}
