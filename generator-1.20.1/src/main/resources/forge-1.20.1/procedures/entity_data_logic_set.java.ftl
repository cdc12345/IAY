<@definePart type="head">
if (${input$entity} instanceof ${generator.map(field$customEntity, "entities")} _datEntSetL)
</@definePart>
	_datEntSetL.getEntityData().set(${generator.map(field$customEntity, "entities")}.DATA_${field$accessor}, ${input$value});
<@definePart type="tail">
}
</@definePart>