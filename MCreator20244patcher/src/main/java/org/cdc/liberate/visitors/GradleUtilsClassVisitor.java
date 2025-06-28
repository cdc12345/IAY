package org.cdc.liberate.visitors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.cdc.liberate.events.LiberateEvent;
import org.cdc.liberate.events.workspace.GradleJavaHomeSetEvent;
import org.cdc.liberate.events.workspace.GradleProcessCreatedEvent;
import org.cdc.liberate.transfer.ClassVisitor;
import org.cdc.liberate.transfer.MethodVisitor;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.ProjectConnection;

public class GradleUtilsClassVisitor extends ClassVisitor {
    public GradleUtilsClassVisitor() {
        super(2);
        visitors.add(new MethodsVisitor());

    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if ("net.mcreator.gradle.GradleUtils".equals(className)) {
            super.visitClass(ctClass, module, className, bytes);
        }
    }

    private class MethodsVisitor extends MethodVisitor{
        @Override
        public void visitMethod(CtMethod method, String methodName, String returnType) {
            if ("getGradleTaskLauncher".equals(methodName)){
                try {
                    method.insertAfter("{org.cdc.liberate.visitors.GradleUtilsClassVisitor.fireEvent1($_,$1,$2);}");
                    finishOneTask();
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                }
            } else if ("getJavaHome".equals(methodName)){
                try{
                    method.insertBefore("{java.lang.String f=org.cdc.liberate.visitors.GradleUtilsClassVisitor.fireEvent2();if (f !=null) return f;}");
                    finishOneTask();
                } catch (CannotCompileException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fireEvent1(BuildLauncher buildLauncher, ProjectConnection projectConnection, String[] task){
        LiberateEvent.eventSync(new GradleProcessCreatedEvent(null,buildLauncher,projectConnection,task));
    }

    public static String fireEvent2(){
        var event = new GradleJavaHomeSetEvent(null);
        LiberateEvent.eventSync(event);
        if (event.getValue() == null){
            return null;
        }
        return event.getValue().getAbsolutePath().replace('\\','/');
    }

}
