<#include "procedures.java.ftl">
@Mod.EventBusSubscriber public class ${name}Procedure {
	@SubscribeEvent public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			AtomicDouble amount = new AtomicDouble(event.getAmount());
			<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
				"x": "event.getEntity().getX()",
				"y": "event.getEntity().getY()",
				"z": "event.getEntity().getZ()",
				"world": "event.getEntity().level()",
				"amount": "amount",
				"entity": "event.getEntity()",
				"damagesource": "event.getSource()",
				"sourceentity": "event.getSource().getEntity()",
				"event": "event"
				}/>
			</#compress></#assign>
			execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
			event.setAmount(amount.floatValue());
		}
	}