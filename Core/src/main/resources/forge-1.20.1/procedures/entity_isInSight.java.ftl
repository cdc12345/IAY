(new Object(){
    public boolean isInSight(Entity entity1,Entity entity2){
        if (entity1 instanceof Mob mob){
            return mob.getSensing().hasLineOfSight(entity2);
        } else if (entity1 instanceof Player player){
            return player.hasLineOfSight(entity2);
        }
        return false;
    }
}.isInSight(${input$entity},${input$check}))
