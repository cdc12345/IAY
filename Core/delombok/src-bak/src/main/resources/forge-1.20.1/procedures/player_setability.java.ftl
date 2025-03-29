if (${input$player} instanceof Player pPlayer){
  pPlayer.getAbilities().${generator.map(field$abilityName, "abilities")}((float)${input$value});
}