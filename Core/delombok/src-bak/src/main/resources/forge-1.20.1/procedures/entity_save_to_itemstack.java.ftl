{
  CompoundTag nbt = new CompoundTag();
  ${input$entity}.save(nbt);
  ${input$item}.getOrCreateTag().put(${input$name}, nbt);
}