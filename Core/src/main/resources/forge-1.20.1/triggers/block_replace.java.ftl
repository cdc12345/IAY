<#include "procedures.java.ftl">
@Mod.EventBusSubscriber public class ${name}Procedure {
	@SubscribeEvent public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		if (!event.getBlockSnapshot().getReplacedBlock().is(Blocks.AIR)){
			<#assign dependenciesCode><#compress>
				<@procedureDependenciesCode dependencies, {
				"x": "event.getPos().getX()",
				"y": "event.getPos().getY()",
				"z": "event.getPos().getZ()",
				"px": "event.getEntity().getX()",
				"py": "event.getEntity().getY()",
				"pz": "event.getEntity().getZ()",
				"world": "event.getLevel()",
				"entity": "event.getEntity()",
				"blockstate": "event.getState()",
				"placedagainst": "event.getPlacedAgainst()",
				"oldblockstate":"event.getBlockSnapshot().getReplacedBlock()",
				"event": "event"
				}/>
			</#compress></#assign>
			execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
		}
	}