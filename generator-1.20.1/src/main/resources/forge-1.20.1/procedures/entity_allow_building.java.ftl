<@definePart type="head">
if (${input$entity} instanceof Player _player) {
</@definePart>
<@definePart type="tail">
    _player.onUpdateAbilities();
}
</@definePart>
    _player.getAbilities().mayBuild = ${input$condition};