if (world instanceof ServerLevel serverLevel) {
	var ad = serverLevel.getServer().getServerResources().managers().getAdvancements();
	try {
		var field = ad.getClass().getDeclaredField("advancements");
		field.setAccessible(true);
		var list=((AdvancementList)field.get(ad));
		var removed=new HashSet<ResourceLocation>();
		list.getAllAdvancements().forEach(a->{
			if (${input$name}.equals(a.getId().toString())){
				removed.add(a.getId());
			}
		});
		list.remove(removed);
	} catch (NoSuchFieldException | IllegalAccessException e) {
		throw new RuntimeException(e);
	}
}