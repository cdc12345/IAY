<#include "procedures.java.ftl">
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD) public class ${name}Procedure {
	@SubscribeEvent public static void onSaltCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
			String tabName = event.getTabKey().location().getPath();
			<#assign dependenciesCode><#compress>
			<@procedureDependenciesCode dependencies, {
				"tabname": "tabName",
                "itemstack": "entry.getKey()",
				"world": "Minecraft.getInstance().level",
				"event": "event"
				}/>
			</#compress></#assign>
            for (Map.Entry<ItemStack, CreativeModeTab.TabVisibility> entry : event.getEntries()) {
				if (event.getTabKey() == CreativeModeTabs.SEARCH) continue;
				execute(event<#if dependenciesCode?has_content>,</#if>${dependenciesCode});
            }
		}
