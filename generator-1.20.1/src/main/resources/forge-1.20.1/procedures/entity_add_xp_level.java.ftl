<@definePart type="head">
if (${input$entity} instanceof Player _player) {
</@definePart>
	_player.giveExperienceLevels(${opt.toInt(input$xpamount)});
<@definePart type="tail">
}
</@definePart>