package org.cdc.liberate.events.workspace;

import org.cdc.liberate.events.LiberateEvent;

import java.io.File;

public class PreGradleHomeSetEvent extends LiberateEvent<File> {
    public PreGradleHomeSetEvent(Object parent) {
        super(parent);
    }

    @Override
    public boolean canSetValue() {
        return true;
    }
}
