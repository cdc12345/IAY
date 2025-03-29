package org.cdc.liberate.events.net;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.cdc.liberate.events.LiberateEvent;
@Getter
public class D8WebAPIInitEvent extends LiberateEvent<Object> {
    private final Logger logger;

    public D8WebAPIInitEvent(Object parent, Logger logger){
        super(parent);
        this.logger = logger;
        setCanceled(true);
    }

    @Override
    public boolean canCancelable() {
        return true;
    }
}
