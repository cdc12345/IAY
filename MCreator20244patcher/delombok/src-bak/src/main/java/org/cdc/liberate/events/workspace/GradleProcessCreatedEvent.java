package org.cdc.liberate.events.workspace;

import lombok.Getter;
import net.mcreator.workspace.Workspace;
import org.cdc.liberate.events.LiberateEvent;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.ProjectConnection;

@Getter
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
}
