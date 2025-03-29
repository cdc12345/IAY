if (world instanceof ServerLevel serverLevel){
    var ad = serverLevel.getServer().getServerResources().managers().getAdvancements();
    try {
        var field=ad.getClass().getDeclaredField("advancements");
        field.setAccessible(true);
        field.set(ad,new AdvancementList());
    } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
    }
}