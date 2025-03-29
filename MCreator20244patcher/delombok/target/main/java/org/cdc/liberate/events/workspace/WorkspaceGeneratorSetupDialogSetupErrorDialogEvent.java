package org.cdc.liberate.events.workspace;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.dialogs.ProgressDialog;
import org.cdc.liberate.events.LiberateEvent;

public class WorkspaceGeneratorSetupDialogSetupErrorDialogEvent extends LiberateEvent {
    private ProgressDialog progressDialog;
    private MCreator mCreator;
    private String content;

    public WorkspaceGeneratorSetupDialogSetupErrorDialogEvent(ProgressDialog dial, MCreator m, String s) {
        super(null);
        this.content = s;
        this.progressDialog = dial;
        this.mCreator = m;
    }

    @Override
    public boolean canCancelable() {
        return true;
    }

    @SuppressWarnings("all")
    public ProgressDialog getProgressDialog() {
        return this.progressDialog;
    }

    @SuppressWarnings("all")
    public MCreator getMCreator() {
        return this.mCreator;
    }

    @SuppressWarnings("all")
    public String getContent() {
        return this.content;
    }
}
