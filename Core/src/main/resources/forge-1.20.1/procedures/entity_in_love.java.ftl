if (${input$entity} instanceof Animal _animal && ${input$player} instanceof Player _player){
        _animal.setAge(0);
		_animal.setInLove(_player);
		_animal.setInLoveTime(${input$time});
}