<#include "mcelements.ftl">
<@addTemplate file="utils/energy/get_fluid_tank_level.java.ftl"/>
<#-- @formatter:off -->
/*@int*/(getFluidTankLevel(world, ${toBlockPos(input$x,input$y,input$z)},${opt.toInt(input$tank)}, ${input$direction}))
<#-- @formatter:on -->