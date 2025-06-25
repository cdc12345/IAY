<#include "mcelements.ftl">
<@addTemplate file="utils/energy/can_receive_energy.java.ftl"/>
<#-- @formatter:off -->
(canReceiveEnergy(world, ${toBlockPos(input$x,input$y,input$z)},${input$direction}))
<#-- @formatter:on -->