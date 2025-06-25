<#include "mcelements.ftl">
<@addTemplate file="utils/energy/get_energy_stored.java.ftl"/>
<#-- @formatter:off -->
/*@int*/(getEnergyStored(world, ${toBlockPos(input$x,input$y,input$z)},${input$direction}))
<#-- @formatter:on -->