<@definePart type="head">
if(${input$entity} instanceof Player _player) {
</@definePart>
    _player.getAbilities().invulnerable = ${input$condition};
<@definePart type="tail">
    _player.onUpdateAbilities();
}
</@definePart>