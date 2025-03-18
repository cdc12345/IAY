package org.cdc.liberate.events.workspace;

import org.cdc.liberate.events.LiberateEvent;

import java.io.File;

public class GradleJavaHomeSetEvent extends LiberateEvent<File> {

    public GradleJavaHomeSetEvent(Object parent) {
        super(parent);
    }

    @Override
    public boolean canSetValue() {
        return true;
    }
}
