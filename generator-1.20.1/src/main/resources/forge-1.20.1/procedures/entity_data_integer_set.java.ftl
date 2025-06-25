<@definePart type="head">
if (${input$entity} instanceof ${generator.map(field$customEntity, "entities")} _datEntSetI)
</@definePart>
	_datEntSetI.getEntityData().set(${generator.map(field$customEntity, "entities")}.DATA_${field$accessor}, ${opt.toInt(input$value)});
<@definePart type="tail">
}
</@definePart>