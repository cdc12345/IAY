<#include "mcelements.ftl">
<@addTemplate file="utils/energy/can_extract_energy.java.ftl"/>
<#-- @formatter:off -->
(canExtractEnergy(world, ${toBlockPos(input$x,input$y,input$z)}, ${input$direction}))
<#-- @formatter:on -->