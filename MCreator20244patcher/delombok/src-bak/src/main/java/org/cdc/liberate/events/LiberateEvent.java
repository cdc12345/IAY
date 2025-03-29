package org.cdc.liberate.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.mcreator.plugin.MCREvent;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class LiberateEvent<T> extends MCREvent {

    /**
     * 同步执行
     */
    public static<T extends MCREvent> T eventSync(T event){
        MCREvent.event(event);
        return event;
    }

    private final Object parent;
    private boolean canceled;
    private T value;

    public LiberateEvent(Object parent){
        this.parent = parent;
    }

    public boolean canCancelable() {
        return false;
    }

    public boolean canSetValue(){
        return false;
    }

    public boolean isCanceled() {
        if (!canCancelable()){
            return false;
        } else {
            return canceled;
        }
    }

    public void setValue(T value) {
        if (canSetValue()){
            this.value = value;
        }
    }
}
