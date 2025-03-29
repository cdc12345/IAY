package org.cdc.liberate.visitors;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.dialogs.ProgressDialog;
import org.cdc.liberate.events.LiberateEvent;
import org.cdc.liberate.events.workspace.WorkspaceGeneratorSetupDialogSetupErrorDialogEvent;
import org.cdc.liberate.transfer.ClassVisitor;
import org.cdc.liberate.transfer.MethodVisitor;

public class WorkspaceGeneratorSetupDialogClassVisitor extends ClassVisitor {
    public WorkspaceGeneratorSetupDialogClassVisitor() {
        super(1);
        visitors.add(new MethodsVisitor());
    }

    @Override
    public void visitClass(CtClass ctClass, Module module, String className, byte[] bytes) throws NotFoundException {
        if ("net.mcreator.ui.dialogs.workspace.WorkspaceGeneratorSetupDialog".equals(className)){
            super.visitClass(ctClass, module, className, bytes);
        }
    }

    private class MethodsVisitor extends MethodVisitor{
        @Override
        public void visitMethod(CtMethod method, String methodName, String returnType) {
            if ("showSetupFailedMessage".equals(methodName)){
                try {
                    method.insertBefore("{if (org.cdc.liberate.visitors.WorkspaceGeneratorSetupDialogClassVisitor.fireEvent1($1,$2,$3)) return;}");
                    finishOneTask();
                } catch (CannotCompileException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean fireEvent1(ProgressDialog dial, MCreator m,String s){
        WorkspaceGeneratorSetupDialogSetupErrorDialogEvent event = new WorkspaceGeneratorSetupDialogSetupErrorDialogEvent(dial, m, s);
        LiberateEvent.eventSync(event);
        if (event.isCanceled()){
            dial.hideDialog();
        }
        return event.isCanceled();
    }
}
