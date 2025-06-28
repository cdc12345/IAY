<#include "mcelements.ftl">
<@addTemplate file="utils/block/logic_nbt_get.java.ftl"/>
<#-- @formatter:off -->
(getLogicNBTValue(world, ${toBlockPos(input$x,input$y,input$z)}, ${input$tagName}))
<#-- @formatter:on -->