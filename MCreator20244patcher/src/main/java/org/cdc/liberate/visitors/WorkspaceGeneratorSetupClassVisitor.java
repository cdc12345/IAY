package org.cdc.liberate.visitors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.mcreator.generator.setup.WorkspaceGeneratorSetup;
import net.mcreator.workspace.Workspace;
import org.apache.logging.log4j.Logger;
import org.cdc.liberate.events.LiberateEvent;
import org.cdc.liberate.events.workspace.PreSetupWorkspaceBaseEvent;
import org.cdc.liberate.events.workspace.SetupWorkspaceBaseEvent;
import org.cdc.liberate.transfer.ClassVisitor;
import org.cdc.liberate.transfer.MethodVisitor;

public class WorkspaceGeneratorSetupClassVisitor extends ClassVisitor {
    public WorkspaceGeneratorSetupClassVisitor() {
        super(1);
        visitors.add(new setupWorkspaceBaseMethodVisitor());
    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if ("net.mcreator.generator.setup.WorkspaceGeneratorSetup".equals(className)) {
            super.visitClass(ctClass, module, className, bytes);
        }
    }

    private class setupWorkspaceBaseMethodVisitor extends MethodVisitor {
        @Override
        public void visitMethod(CtMethod method, String methodName, String returnType) {
            if ("setupWorkspaceBase".equals(methodName)){
                try {
                    method.insertBefore("{if(org.cdc.liberate.visitors.WorkspaceGeneratorSetupClassVisitor.fireEvent2(null,$1,LOG))return;}");
                    method.insertAfter("{org.cdc.liberate.visitors.WorkspaceGeneratorSetupClassVisitor.fireEvent1(null,$1,LOG);}");
                    finishOneTask();
                } catch (CannotCompileException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void fireEvent1(Object parent, Workspace workspace, Logger LOG){
        LiberateEvent.eventSync(new SetupWorkspaceBaseEvent(parent, workspace, LOG));
    }

    public static boolean fireEvent2(Object parent, Workspace workspace, Logger LOG){
        return LiberateEvent.eventSync(new PreSetupWorkspaceBaseEvent(parent,workspace,LOG)).isCanceled();
    }
}
