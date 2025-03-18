package org.cdc.liberate.events.workspace;

import net.mcreator.workspace.Workspace;
import org.cdc.liberate.events.LiberateEvent;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.ProjectConnection;

public class GradleProcessCreatedEvent extends LiberateEvent<Object> {
    private final BuildLauncher buildLauncher;
    private final ProjectConnection connection;
    private final String[] task;

    public GradleProcessCreatedEvent(Object parent, BuildLauncher buildLauncher, ProjectConnection connection, String[] task) {
        super(parent);
        this.buildLauncher = buildLauncher;
        this.connection = connection;
        this.task = task;
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public BuildLauncher getBuildLauncher() {
        return this.buildLauncher;
    }

    @SuppressWarnings("all")
    public ProjectConnection getConnection() {
        return this.connection;
    }

    @SuppressWarnings("all")
    public String[] getTask() {
        return this.task;
    }
    //</editor-fold>
}
