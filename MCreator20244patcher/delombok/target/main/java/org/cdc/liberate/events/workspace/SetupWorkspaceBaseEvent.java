package org.cdc.liberate.events.workspace;

import net.mcreator.workspace.Workspace;
import org.apache.logging.log4j.Logger;
import org.cdc.liberate.events.LiberateEvent;

public class SetupWorkspaceBaseEvent extends LiberateEvent<Object> {
    private final Logger LOG;
    private final Workspace workspace;

    public SetupWorkspaceBaseEvent(Object parent, Workspace workspace, Logger LOG) {
        super(parent);
        this.LOG = LOG;
        this.workspace = workspace;
    }

    @SuppressWarnings("all")
    public Logger getLOG() {
        return this.LOG;
    }

    @SuppressWarnings("all")
    public Workspace getWorkspace() {
        return this.workspace;
    }
}
