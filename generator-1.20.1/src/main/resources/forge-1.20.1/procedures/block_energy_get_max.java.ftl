<#include "mcelements.ftl">
<@addTemplate file="utils/energy/energy_get_max.java.ftl"/>
<#-- @formatter:off -->
/*@int*/(getMaxEnergyStored(world, ${toBlockPos(input$x,input$y,input$z)},${input$direction}))
<#-- @formatter:on -->