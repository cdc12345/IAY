<#include "mcelements.ftl">
<@addTemplate file="utils/energy/fill_tank_simulate.java.ftl"/>
<#-- @formatter:off -->
/*@int*/(fillTankSimulate(world, ${toBlockPos(input$x,input$y,input$z)},${opt.toInt(input$amount)},${input$direction}))
<#-- @formatter:on -->