package ${package}.event;
public class TargetFilterEvent extends Event {

	public static TargetFilterEvent postEvent(double id, Entity entity, Entity sourceEntity){
		var event = new TargetFilterEvent(id, entity, sourceEntity);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}


	private final double eventSourceId;
	private final Entity entity,sourceEntity;

	public TargetFilterEvent(double id, Entity entity,Entity sourceEntity) {
		this.eventSourceId = id;
		this.entity = entity;
		this.sourceEntity = sourceEntity;
		this.setResult(Result.${data.defaultFilterResult});
	}

	public Entity getEntity() {
		return entity;
	}

	public double getEventSourceId() {
		return eventSourceId;
	}

	public Entity getSourceEntity() {
		return sourceEntity;
	}

	@Override
	public boolean hasResult() {
		return true;
	}
}