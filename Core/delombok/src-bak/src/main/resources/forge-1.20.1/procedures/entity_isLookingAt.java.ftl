(new Object() {
    boolean isLookingAtMe(Entity pPlayer1,Entity entity1) {
        if (pPlayer1 instanceof Player pPlayer){
            Vec3 vec3 = pPlayer.getViewVector(1.0F).normalize();
            Vec3 vec31 = new Vec3(entity1.getX() - pPlayer.getX(), entity1.getEyeY() - pPlayer.getEyeY(), entity1.getZ() - pPlayer.getZ());
            double d0 = vec31.length();
            vec31 = vec31.normalize();
            double d1 = vec3.dot(vec31);
            return d1 > 1.0D - 0.025D / d0 && pPlayer.hasLineOfSight(entity1);
        } else {
            return false;
        }
    }
}.isLookingAtMe(${input$entity},${input$check}))
