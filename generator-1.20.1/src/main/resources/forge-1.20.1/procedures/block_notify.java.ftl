<#include "mcelements.ftl">
<@definePart type="head">
if(world instanceof Level _level) {
</@definePart>
	_level.updateNeighborsAt(${toBlockPos(input$x,input$y,input$z)},
		_level.getBlockState(${toBlockPos(input$x,input$y,input$z)}).getBlock());
<@definePart type="tail">
}
</@definePart>