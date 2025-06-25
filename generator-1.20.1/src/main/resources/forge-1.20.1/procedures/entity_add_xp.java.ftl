<@definePart type="head">
if (${input$entity} instanceof Player _player) {
</@definePart>
	_player.giveExperiencePoints(${opt.toInt(input$xpamount)});
<@definePart type="tail">
}
</@definePart>
