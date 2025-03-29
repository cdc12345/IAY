package org.cdc.liberate.transfer;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import lombok.Getter;
import org.cdc.liberate.Liberate;
import org.cdc.liberate.visitors.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.InvocationTargetException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;

public class AgentClassTransfer implements ClassFileTransformer {

    private final Class<?>[] classVisitors = new Class[]{
            D8WebAPIClassVisitor.class,
            UserFolderManagerClassVisitor.class,
            WorkspaceGeneratorSetupClassVisitor.class,
            GradleUtilsClassVisitor.class,
            StatusBarClassVisitor.class,
            WorkspaceGeneratorSetupDialogClassVisitor.class,
            SomeClassVersionFixer.class
    };

    /**
     * 已经完成的访问者数量.
     */
    private int taskOrder;

    @Getter
    private final List<ClassVisitor> visitors;
    private final ClassPool pool;

    public AgentClassTransfer() {
        visitors = new ArrayList<>();
        pool = ClassPool.getDefault();

        loadAllBuiltinClassVisitors();
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String classPath, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (taskOrder != classVisitors.length) {
            var className = toReadableClassName(classPath);
            if (className.startsWith("net.mcreator")) {
                try {
                    if (Liberate.getInstance().getMappings().containsKey(className)) {
                        System.out.println(className);
                        String name = Liberate.getInstance().getMappings().getProperty(className);
                        if (!name.endsWith(".class")) {
                            name = name + ".class";
                        }
                        var patchJar = Liberate.getInstance().getAgentJar();
                        JarEntry entry = patchJar.getJarEntry(name);
                        System.out.println(entry.getRealName());
                        return Objects.requireNonNull(patchJar.getInputStream(entry)).readAllBytes();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    var ctClass = pool.get(className);
                    for (ClassVisitor visitor : visitors) {
                        if (visitor.isFinished()) {
                            continue;
                        }
                        visitor.classBeingRedefined = classBeingRedefined;
                        visitor.classLoader = loader;
                        visitor.protectionDomain = protectionDomain;
                        visitor.visitClass(ctClass, module, className, classfileBuffer);
                        if (visitor.isFinished()) {
                            taskOrder++;
                        }
                    }
                    if (ctClass.isModified()) {
                        ctClass.writeFile("classes-cache");
                        return ctClass.toBytecode();
                    }
                } catch (NotFoundException | IOException | CannotCompileException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new byte[0];
    }

    private void loadAllBuiltinClassVisitors() {
        for (Class<?> clas : classVisitors) {
            try {
                visitors.add((ClassVisitor) clas.getConstructor().newInstance());
            } catch (NoSuchMethodException | InstantiationException |
                     InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String toReadableClassName(String classPath) {
        return classPath.replace('/', '.');
    }
}
