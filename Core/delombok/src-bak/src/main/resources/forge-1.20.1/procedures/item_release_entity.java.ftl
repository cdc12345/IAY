new Object(){
    public Entity getEntity(LevelAccessor world,String entityId,ItemStack itemStack,double x,double y,double z){
        var entity1 = world.registryAccess().registry(Registries.ENTITY_TYPE).get().get(new ResourceLocation(entityId)).create((Level) world);
        entity1.load((CompoundTag) itemStack.getOrCreateTag().get(${input$name}));
        entity1.absMoveTo(x,y,z);
        world.addFreshEntity(entity1);
        itemStack.getOrCreateTag().remove(${input$name});
        return entity1;
    }
}.getEntity(world,${input$entityId},${input$item},${input$x},${input$y},${input$z})