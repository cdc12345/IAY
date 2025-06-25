<#include "mcelements.ftl">
<@addTemplate file="utils/energy/drain_tank_simulate.java.ftl"/>
<#-- @formatter:off -->
/*@int*/(drainTankSimulate(world, ${toBlockPos(input$x,input$y,input$z)},${opt.toInt(input$amount)},${input$direction}))
<#-- @formatter:on -->