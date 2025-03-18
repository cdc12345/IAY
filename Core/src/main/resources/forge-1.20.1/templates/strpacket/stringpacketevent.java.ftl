package ${package}.event;
public class StringPacketEvent extends Event {

    private final String msg;

    private final Entity entity;

    public StringPacketEvent(String msg, Entity entity){
        this.msg = msg;
        this.entity = entity;
    }

    public String getMsg() {
        return msg;
    }

    public Entity getEntity() {
        return entity;
    }
}