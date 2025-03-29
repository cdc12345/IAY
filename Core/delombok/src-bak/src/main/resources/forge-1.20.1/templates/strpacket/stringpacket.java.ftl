<#-- @formatter:off -->
<#include "../procedures.java.ftl">
<#include "../triggers.java.ftl">

package ${package}.network;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${name} {
	private final String msg;

	public ${name}(FriendlyByteBuf buffer) {
		this.msg = buffer.readUtf();
	}

	public ${name}(String msg) {
		this.msg = msg;
	}

	public static void buffer(${name} message, FriendlyByteBuf buffer) {
		buffer.writeUtf(message.msg);
	}

	public static void handler(StringPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			if (entity == null) entity = Minecraft.getInstance().player;
			<#if hasProcedure(data.onHandler)>
				<@procedureCode data.onHandler, {
					"x": "entity.getX()",
					"y": "entity.getY()",
					"z": "entity.getZ()",
					"world": "entity.level()",
					"entity": "entity",
					"msg": "message.msg"
				}/>
			<#else>
				MinecraftForge.EVENT_BUS.post(new StringPacketEvent(message.msg,entity));
			</#if>
		});
		context.setPacketHandled(true);
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		${JavaModName}.addNetworkMessage(${name}.class, ${name}::buffer,${name}::new,${name}::handler);
	}
}