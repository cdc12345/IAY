<#include "mcelements.ftl">
<@addTemplate file="utils/block/text_nbt_get.java.ftl"/>
<#-- @formatter:off -->
(getTextNBTValue(world, ${toBlockPos(input$x,input$y,input$z)}, ${input$tagName}))
<#-- @formatter:on -->