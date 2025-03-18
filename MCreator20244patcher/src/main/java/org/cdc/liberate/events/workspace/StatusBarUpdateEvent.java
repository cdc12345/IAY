package org.cdc.liberate.events.workspace;

import org.cdc.liberate.events.LiberateEvent;
import javax.swing.*;

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

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public String getOldMessage() {
        return this.oldMessage;
    }

    @SuppressWarnings("all")
    public String getNewMessage() {
        return this.newMessage;
    }

    @SuppressWarnings("all")
    public JLabel getMessageLabelComponent() {
        return this.messageLabelComponent;
    }
    //</editor-fold>
}
