<#include "procedures.java.ftl">
@Mod.EventBusSubscriber public class ${name}Procedure {
	@SubscribeEvent public static void onEntityGetProjectile(LivingGetProjectileEvent event) {
		if (event != null && event.getEntity() != null) {
			AtomicReference<ItemStack> result = new AtomicReference<>(event.getProjectileItemStack());
			<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
				"x": "event.getEntity().getX()",
				"y": "event.getEntity().getY()",
				"z": "event.getEntity().getZ()",
				"world": "event.getEntity().level()",
				"result": "result",
				"weapon": "event.getProjectileWeaponItemStack()",
				"projectile": "event.getProjectileItemStack()",
				"entity": "event.getEntity()",
				"event": "event"
				}/>
			</#compress></#assign>
			execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
			event.setProjectileItemStack(result.get());
		}
	}