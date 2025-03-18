package org.cdc.liberate.visitors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.mcreator.ui.StatusBar;
import org.cdc.liberate.events.LiberateEvent;
import org.cdc.liberate.events.workspace.StatusBarUpdateEvent;
import org.cdc.liberate.transfer.ClassVisitor;
import org.cdc.liberate.transfer.MethodVisitor;

import javax.swing.*;

public class StatusBarClassVisitor extends ClassVisitor {
    public StatusBarClassVisitor() {
        super(1);
        visitors.add(new setMessageMethodVisitor());
    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if ("net.mcreator.ui.StatusBar".equals(className)) {
            super.visitClass(ctClass, module, className, bytes);
        }
    }

    private class setMessageMethodVisitor extends MethodVisitor{
        @Override
        public void visitMethod(CtMethod method, String methodName, String returnType) {
            if ("setMessage".equals(methodName)){
                try {
                    method.insertBefore("{org.cdc.liberate.visitors.StatusBarClassVisitor.fireEvent1($0,$1,messages);}");
                    finishOneTask();
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fireEvent1(StatusBar parent, String newMessage, JLabel messages){
        LiberateEvent.event(new StatusBarUpdateEvent(parent,messages.getText(),newMessage,messages));
    }
}
