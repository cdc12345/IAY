<#include "mcelements.ftl">
<@addTemplate file="utils/block/number_nbt_get.java.ftl"/>
<#-- @formatter:off -->
(getDoubleNBTValue(world, ${toBlockPos(input$x,input$y,input$z)}, ${input$tagName}))
<#-- @formatter:on -->