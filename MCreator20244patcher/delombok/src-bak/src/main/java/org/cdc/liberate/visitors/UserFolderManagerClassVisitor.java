package org.cdc.liberate.visitors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.cdc.liberate.events.LiberateEvent;
import org.cdc.liberate.events.workspace.PreGradleHomeSetEvent;
import org.cdc.liberate.transfer.ClassVisitor;
import org.cdc.liberate.transfer.MethodVisitor;

import java.io.File;

public class UserFolderManagerClassVisitor extends ClassVisitor {

    public UserFolderManagerClassVisitor() {
        super(1);
        visitors.add(new getGradleHomeMethodVisitor());
    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if ("net.mcreator.io.UserFolderManager".equals(className)) {
            super.visitClass(ctClass, module, className, bytes);
        }
    }

    private class getGradleHomeMethodVisitor extends MethodVisitor{
        @Override
        public void visitMethod(CtMethod method, String methodName, String returnType) {
            if ("getGradleHome".equals(methodName)){
                try {
                    method.insertBefore("{java.io.File f=org.cdc.liberate.visitors.UserFolderManagerClassVisitor.fireEvent1();if (f!= null) return f;}");
                    finishOneTask();
                } catch (CannotCompileException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static File fireEvent1(){
        try {
            return LiberateEvent.eventSync(new PreGradleHomeSetEvent(null)).getValue();
        } catch (NullPointerException ignored){
            return null;
        }
    }
}
