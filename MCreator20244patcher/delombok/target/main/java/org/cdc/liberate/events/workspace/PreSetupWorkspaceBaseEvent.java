package org.cdc.liberate.events.workspace;

import net.mcreator.workspace.Workspace;
import org.apache.logging.log4j.Logger;
import org.cdc.liberate.events.LiberateEvent;

public class PreSetupWorkspaceBaseEvent extends LiberateEvent<Object> {
    private final Workspace workspace;
    private final Logger LOG;

    public PreSetupWorkspaceBaseEvent(Object parent, Workspace workspace, Logger LOG) {
        super(parent);
        this.LOG = LOG;
        this.workspace = workspace;
    }

    @Override
    public boolean canCancelable() {
        return true;
    }

    @SuppressWarnings("all")
    public Workspace getWorkspace() {
        return this.workspace;
    }

    @SuppressWarnings("all")
    public Logger getLOG() {
        return this.LOG;
    }
}
