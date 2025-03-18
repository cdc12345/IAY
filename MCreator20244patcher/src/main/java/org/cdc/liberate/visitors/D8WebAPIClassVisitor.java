package org.cdc.liberate.visitors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.mcreator.io.net.api.D8WebAPI;
import org.cdc.liberate.events.LiberateEvent;
import org.cdc.liberate.events.net.D8WebAPIInitEvent;
import org.cdc.liberate.transfer.ClassVisitor;
import org.cdc.liberate.transfer.MethodVisitor;

public class D8WebAPIClassVisitor extends ClassVisitor {

    public D8WebAPIClassVisitor(){
        super(1);
        visitors.add(new initAPIPrivateMethodVisitor());
    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if ("net.mcreator.io.net.api.D8WebAPI".equals(className)){
            super.visitClass(ctClass, module, className, bytes);
        }
    }

    private class initAPIPrivateMethodVisitor extends MethodVisitor {
        @Override
        public void visitMethod(CtMethod method, String methodName, String returnType) {
            if ("initAPI".equals(methodName)){
                try {
                    method.insertBefore("{if (org.cdc.liberate.visitors.D8WebAPIClassVisitor.fireEvent1($0,LOG)) return false;}");
                    finishOneTask();
                } catch (CannotCompileException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean fireEvent1(D8WebAPI instance, org.apache.logging.log4j.Logger LOG){
        var event = new D8WebAPIInitEvent(instance,LOG);
        LiberateEvent.eventSync(event);
        return event.isCanceled();
    }
}
