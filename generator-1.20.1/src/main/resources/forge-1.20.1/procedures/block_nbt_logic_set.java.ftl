<#include "mcelements.ftl">
<@definePart type="head">
if(!world.isClientSide()) {
	BlockPos _bp = ${toBlockPos(input$x,input$y,input$z)};
	BlockEntity _blockEntity = world.getBlockEntity(_bp);
	BlockState _bs = world.getBlockState(_bp);
	if(_blockEntity != null) {
</@definePart>
		_blockEntity.getPersistentData().putBoolean(${input$tagName}, ${input$tagValue});
<@definePart type="tail">
	}
	if(world instanceof Level _level)
		_level.sendBlockUpdated(_bp, _bs, _bs, 3);
}
</@definePart>