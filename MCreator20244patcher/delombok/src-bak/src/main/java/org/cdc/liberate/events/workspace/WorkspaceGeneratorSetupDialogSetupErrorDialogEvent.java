package org.cdc.liberate.events.workspace;

import lombok.Getter;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.dialogs.ProgressDialog;
import org.cdc.liberate.events.LiberateEvent;

@Getter
public class WorkspaceGeneratorSetupDialogSetupErrorDialogEvent extends LiberateEvent {
    private ProgressDialog progressDialog;
    private MCreator mCreator;
    private String content;

    public WorkspaceGeneratorSetupDialogSetupErrorDialogEvent(ProgressDialog dial, MCreator m,String s){
        super(null);
        this.content = s;
        this.progressDialog = dial;
        this.mCreator = m;
    }

    @Override
    public boolean canCancelable() {
        return true;
    }
}
