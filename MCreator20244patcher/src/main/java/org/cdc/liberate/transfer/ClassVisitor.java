package org.cdc.liberate.transfer;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class ClassVisitor {

    protected List<MethodVisitor> visitors;
    protected int remainTask;

    /**
     *
     * @param taskNum 任务数量
     */
    public ClassVisitor(int taskNum){
        visitors = new ArrayList<>();
        this.remainTask = taskNum;
    }

    protected ClassLoader classLoader;
    protected Class<?> classBeingRedefined;
    protected ProtectionDomain protectionDomain;

    public void visitClass(CtClass ctClass , Module module, String className, byte[] bytes) throws NotFoundException {
        for (CtMethod method:ctClass.getDeclaredMethods()){
            for(MethodVisitor visitor:visitors){
                visitor.visitMethod(method,method.getName(),method.getReturnType().getName());
            }
        }
    }

    protected void finishOneTask(){
        remainTask--;
    }

    /**
     * @return 当任务全部完成时,这个方法会返回true
     */
    public boolean isFinished(){
        return remainTask == 0;
    }
}
