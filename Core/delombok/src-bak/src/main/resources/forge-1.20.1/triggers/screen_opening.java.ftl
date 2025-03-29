<#include "procedures.java.ftl">
@Mod.EventBusSubscriber(value = {Dist.CLIENT}) public class ${name}Procedure {
	@SubscribeEvent public static void onScreenOpening(ScreenEvent.Opening event) {
			if (Minecraft.getInstance().getConnection()!=null && Minecraft.getInstance().getConnection().getConnection() != null)
				${JavaModName}.PACKET_HANDLER.sendToServer(new ${name}Message(Objects.requireNonNullElse(event.getNewScreen(),new Object()).getClass().getSimpleName(), Objects.requireNonNullElse(event.getCurrentScreen(),new Object()).getClass().getSimpleName()));
			<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
			"event": "event",
			"newscreen": "Objects.requireNonNullElse(event.getNewScreen(),new Object()).getClass().getSimpleName()",
			"oldscreen": "Objects.requireNonNullElse(event.getCurrentScreen(),new Object()).getClass().getSimpleName()",
			"x": "0",
			"y": "0",
			"z": "0",
			"world": "null",
			"entity": "null"
			}/>
			</#compress></#assign>
			execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
	}
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ${name}Message {
		private final String newScreen,oldScreen;
		public ${name}Message(String newScreen,String oldScreen) {
			this.newScreen = newScreen;
			this.oldScreen = oldScreen;
		}

		public ${name}Message(FriendlyByteBuf buffer) {
			this.newScreen = buffer.readUtf();
			this.oldScreen = buffer.readUtf();
		}

		public static void buffer(${name}Message message, FriendlyByteBuf buffer) {
			buffer.writeUtf(message.newScreen);
			buffer.writeUtf(message.oldScreen);
		}

		public static void handler(${name}Message message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
			if (!context.getSender().level().hasChunkAt(context.getSender().blockPosition()))
			return;
			<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
				"newscreen": "message.newScreen",
				"oldscreen": "message.oldScreen",
				"x": "context.getSender().getX()",
				"y": "context.getSender().getY()",
				"z": "context.getSender().getZ()",
				"world": "context.getSender().level()",
				"entity": "context.getSender()"
			}/>
			</#compress></#assign>
			execute(${dependenciesCode});
			});
			context.setPacketHandled(true);
		}

		@SubscribeEvent public static void registerMessage(FMLCommonSetupEvent event) {
			${JavaModName}.addNetworkMessage(${name}Message.class, ${name}Message::buffer, ${name}Message::new, ${name}Message::handler);
		}
	}