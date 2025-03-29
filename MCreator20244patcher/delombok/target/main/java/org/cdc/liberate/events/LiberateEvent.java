package org.cdc.liberate.events;

import net.mcreator.plugin.MCREvent;

public abstract class LiberateEvent<T> extends MCREvent {
    /**
     * 同步执行
     */
    public static <T extends MCREvent> T eventSync(T event) {
        MCREvent.event(event);
        return event;
    }

    private final Object parent;
    private boolean canceled;
    private T value;

    public LiberateEvent(Object parent) {
        this.parent = parent;
    }

    public boolean canCancelable() {
        return false;
    }

    public boolean canSetValue() {
        return false;
    }

    public boolean isCanceled() {
        if (!canCancelable()) {
            return false;
        } else {
            return canceled;
        }
    }

    public void setValue(T value) {
        if (canSetValue()) {
            this.value = value;
        }
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LiberateEvent)) return false;
        final LiberateEvent<?> other = (LiberateEvent<?>) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        if (this.isCanceled() != other.isCanceled()) return false;
        final Object this$parent = this.getParent();
        final Object other$parent = other.getParent();
        if (this$parent == null ? other$parent != null : !this$parent.equals(other$parent)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof LiberateEvent;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        result = result * PRIME + (this.isCanceled() ? 79 : 97);
        final Object $parent = this.getParent();
        result = result * PRIME + ($parent == null ? 43 : $parent.hashCode());
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }

    @SuppressWarnings("all")
    public Object getParent() {
        return this.parent;
    }

    @SuppressWarnings("all")
    public T getValue() {
        return this.value;
    }

    @SuppressWarnings("all")
    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "LiberateEvent(parent=" + this.getParent() + ", canceled=" + this.isCanceled() + ", value=" + this.getValue() + ")";
    }
}
