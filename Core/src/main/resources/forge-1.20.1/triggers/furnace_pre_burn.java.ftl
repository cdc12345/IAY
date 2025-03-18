<#include "procedures.java.ftl">
@Mod.EventBusSubscriber public class ${name}Procedure {
	@SubscribeEvent public static void onFurnaceCanBurn(FurnaceCanBurnEvent event) {
			<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
			    "item": "event.getItem()",
			    "fuel": "event.getFuel()",
			    "littime": "event.getLitTime()",
				"event": "event"
				}/>
			</#compress></#assign>
			execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
	}