package org.cdc.liberate.events.workspace;

import lombok.Getter;
import org.cdc.liberate.events.LiberateEvent;

import javax.swing.*;

@Getter
public class StatusBarUpdateEvent extends LiberateEvent<Object> {
    private final String oldMessage;
    private final String newMessage;
    private final JLabel messageLabelComponent;

    public StatusBarUpdateEvent(Object parent, String oldMessage, String newMessage, JLabel jLabel) {
        super(parent);
        this.oldMessage = oldMessage;
        this.newMessage = newMessage;
        this.messageLabelComponent = jLabel;
    }
}
