<#include "mcelements.ftl">
<@definePart type="head">
if(${input$entity} instanceof ServerPlayer _serverPlayer) {
</@definePart>
	_serverPlayer.awardRecipesByKey(new ResourceLocation[]{${toResourceLocation(input$recipe)}});
<@definePart type="tail">
}
</@definePart>